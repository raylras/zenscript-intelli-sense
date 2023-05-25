package raylras.zen.langserver.provider;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import org.antlr.v4.runtime.Token;
import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.Declarator;
import raylras.zen.code.symbol.*;
import raylras.zen.langserver.data.CompletionNode;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.langserver.search.CompletionNodeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.*;
import raylras.zen.l10n.L10N;
import raylras.zen.service.LibraryService;
import raylras.zen.util.*;
import raylras.zen.util.Range;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CompletionProvider {

    private static final Logger logger = Logger.getLogger("completion");
    public static final int MAX_ITEMS = 50;
    public static final String[] KEYWORDS = makeKeywords();

    private final CompilationUnit unit;
    private final CompletionNode completionNode;

    private final List<CompletionItem> items = new ArrayList<>();
    private boolean isInComplete = false;

    public CompletionProvider(CompilationUnit unit, CompletionNode completionNode) {
        this.unit = unit;
        this.completionNode = completionNode;
    }

    public static CompletionList completion(CompilationUnit unit, CompletionParams params) {
        Range cursor = Ranges.from(params.getPosition());
        logger.info("Completing at %s(%d, %d)...", unit.getFilePath().getFileName(), cursor.startLine, cursor.endLine);
        Instant started = Instant.now();
        CompletionNode node = new CompletionNodeResolver(unit, cursor).resolve();
        CompletionProvider provider = new CompletionProvider(unit, node);
        provider.complete();

        long elapsedMs = Duration.between(started, Instant.now()).toMillis();
        if (provider.isInComplete)
            logger.info(String.format("Found %d items (incomplete) in %,d ms", provider.items.size(), elapsedMs));
        else
            logger.info(String.format("...found %d items in %,d ms", provider.items.size(), elapsedMs));

        return new CompletionList(provider.isInComplete, provider.items);
    }

    private void complete() {
        switch (completionNode.kind) {
            case IDENTIFIER:
                completeIdentifier();
                break;
            case IMPORT:
                completeImport();
                break;
            case MEMBER_ACCESS:
                completeMemberAccess();
                break;
            case BRACKET_HANDLER:
                completeBracketHandler();
                break;
            case DEFAULT:
                completeDefault();
                break;
            case NONE:
                break;
        }
    }


    // basic completion methods
    private void completeIdentifier() {
        if (completionNode.node instanceof LocalAccessExprContext) {
            completeLocalSymbols(s -> true);
            completeGlobalSymbols(s -> true, true);
            completeKeywords();
        } else {
            completeLocalSymbols(s -> s.getKind().isClass());
            completeGlobalSymbols(s -> s.getKind().isClass(), true);
        }

        completeAutoImportedClass();
    }

    private void completeImport() {

        QualifiedNameContext qualifierExpr = ((ImportDeclarationContext) completionNode.node).qualifiedName();
        String qualifiedName = qualifierExpr.getText();

        if (Strings.isNullOrEmpty(qualifiedName)) {
            completeGlobalSymbols(s -> !s.isDeclaredBy(Declarator.GLOBAL), true);
        } else {

            Map<String, CompletionItem> itemMap = new HashMap<>();

            Tuple<String, Collection<String>> possiblePackage = MemberUtils.findPackages(unit, qualifiedName);

            for (String child : possiblePackage.second) {
                itemMap.put(child, makePackage(child, false));
            }
            if (possiblePackage.first != null) {
                for (Symbol member : unit.environment().getSymbolsOfPackage(possiblePackage.first)) {
                    itemMap.put(member.getName(), makeItem(member));
                }
            }

            ClassSymbol availableClass = unit.environment().findSymbol(ClassSymbol.class, qualifiedName);
            if (availableClass != null) {
                MemberUtils.iterateMembers(unit.environment(), availableClass.getType(), true, member -> {
                    if (!isNameMatchesCompleting(member.getName())) {
                        return;
                    }
                    itemMap.put(member.getName(), makeItem(member));
                });
            }

            items.addAll(itemMap.values());
        }

    }

    private void completeBracketHandler() {

    }

    private void completeMemberAccess() {
        ExpressionContext qualifierExpr = completionNode.getQualifierExpression();


        Tuple<Boolean, Type> qualifierType = MemberUtils.resolveQualifierTarget(unit, qualifierExpr);
        boolean endWithParen = completionNode.isEndsWithParen();

        if (!TypeUtils.isValidType(qualifierType.second)) {
            String text = qualifierExpr.getText();
            Tuple<String, Collection<String>> possiblePackage = MemberUtils.findPackages(unit, text);
            boolean success = addPackageAndChildren(possiblePackage.first, possiblePackage.second, endWithParen);

            if (!success) {
                logger.warn("Could not find members of expression, no such type or package: " + qualifierExpr.getText());
            }
            return;
        }


        addMemberAccess(qualifierType.second, qualifierType.first, endWithParen);
    }

    private boolean addPackageAndChildren(@Nullable String packageName, Collection<String> childPackages, boolean endsWithParen) {
        boolean isEmpty = true;
        if (packageName != null) {

            for (Symbol member : unit.environment().getSymbolsOfPackage(packageName)) {
                isEmpty = false;
                if (member.getKind().isFunction()) {
                    items.add(makeFunction((FunctionSymbol) member, !endsWithParen));
                } else {
                    items.add(makeItem(member));
                }
            }
        }
        for (String child : childPackages) {
            isEmpty = false;
            items.add(makePackage(child, false));
        }
        return !isEmpty;
    }

    private void completeDefault() {
        completeKeywords();
    }


    private void completeLocalSymbols(Predicate<Symbol> condition) {
        Scope scope = unit.lookupScope(completionNode.node);
        if (scope == null)
            return;
        boolean endWithParen = completionNode.isEndsWithParen();

        List<Symbol> symbols = unit.lookupLocalSymbols(Symbol.class, scope,
            it -> isNameMatchesCompleting(it.getName())
        );
        for (Symbol symbol : symbols) {
            if (!condition.test(symbol)) {
                continue;
            }
            if (symbol.getKind() == ZenSymbolKind.IMPORT) {
                ImportSymbol importSymbol = (ImportSymbol) symbol;
                if (importSymbol.isFunctionImport()) {
                    for (FunctionSymbol functionTarget : importSymbol.getFunctionTargets()) {
                        if (!condition.test(functionTarget)) {
                            continue;
                        }
                        items.add(makeFunction(functionTarget, !endWithParen));
                    }
                } else {
                    Symbol target = importSymbol.getSimpleTarget();
                    if (!condition.test(target)) {
                        continue;
                    }
                    if (target != null) {
                        items.add(makeItem(target));
                    } else {
                        items.add(makeItem(symbol));
                    }
                }
            } else if (symbol.getKind().isFunction()) {
                items.add(makeFunction((FunctionSymbol) symbol, !endWithParen));
            } else {
                items.add(makeItem(symbol));
            }
        }
    }

    private void completeGlobalSymbols(Predicate<Symbol> condition, boolean addPackages) {
        for (Symbol member : unit.environment().getGlobals()) {
            if (!condition.test(member)) {
                continue;
            }
            if (isNameMatchesCompleting(member.getName())) {
                items.add(makeItem(member));
            }
        }

        if (addPackages) {
            if (isNameMatchesCompleting("scripts")) {
                items.add(makePackage("scripts", true));
            }
            for (String rootPackageName : unit.environment().libraryService().allRootPackageNames()) {
                items.add(makePackage(rootPackageName, true));
            }
        }
    }

    private void completeAutoImportedClass() {
        LibraryService libraryService = unit.environment().libraryService();
        String completingString = completionNode.completingString;
        for (String clazzName : libraryService.allGlobalClasses()) {
            if (SymbolUtils.isNativeClass(clazzName)) {
                continue;
            }
            if (items.size() > MAX_ITEMS) {
                isInComplete = true;
                break;
            }
            String simpleClassName = StringUtils.getSimpleName(clazzName);
            if (StringUtils.matchesPartialName(simpleClassName, completingString)) {
                ClassSymbol classSymbol = libraryService.getClassSymbol(clazzName);
                CompletionItem item = makeItem(classSymbol);
                item.setAdditionalTextEdits(makeAutoImports(classSymbol));
                items.add(item);
            }
        }
    }


    private boolean isNameMatchesCompleting(String candidate) {
        return StringUtils.matchesPartialName(candidate, completionNode.completingString);
    }

    private void addMemberAccess(Type type, boolean isStatic, boolean endsWithParen) {
//        HashMap<String, List<FunctionSymbol>> functions = new HashMap<>();
        MemberUtils.iterateMembers(unit.environment(), type, isStatic, member -> {

            if (!isNameMatchesCompleting(member.getName())) {
                return;
            }

            if (member.getKind().isFunction()) {
//                functions.computeIfAbsent(member.getName(), n -> new ArrayList<>())
//                    .add((FunctionSymbol) member);
                items.add(makeFunction((FunctionSymbol) member, !endsWithParen));
            } else {
                items.add(makeItem(member));
            }

        });

//        for (List<FunctionSymbol> overloads : functions.values()) {
//            data.add(makeFunctions(overloads, !endsWithParen));
//        }
    }

    private void completeKeywords() {
        for (String keyword : KEYWORDS) {
            if (isNameMatchesCompleting(keyword)) {
                CompletionItem item = new CompletionItem(keyword);
                item.setKind(CompletionItemKind.Keyword);
                item.setDetail(L10N.getString("l10n.keyword"));
                items.add(item);
            }
        }
    }

    private static CompletionItemKind getCompletionItemKind(Symbol symbol) {
        switch (symbol.getKind()) {
            case ZEN_CLASS:
            case NATIVE_CLASS:
            case LIBRARY_CLASS:
                return CompletionItemKind.Class;
            case INTERFACE:
            case FUNCTIONAL_INTERFACE:
                return CompletionItemKind.Interface;
            case OPERATOR:
                return CompletionItemKind.Operator;
            case LOCAL_VARIABLE:
                return CompletionItemKind.Variable;
            case GLOBAL_VARIABLE:
            case FIELD:
                return CompletionItemKind.Field;
            case FUNCTION_PARAMETER:
                return CompletionItemKind.Property;
            case FUNCTION:
            case FUNCTION_EXPRESSION:
            case EXPAND_FUNCTION:
                return CompletionItemKind.Function;
            case CONSTRUCTOR:
                return CompletionItemKind.Constructor;
            case NONE:
            default:
                return null;
        }
    }

    // tool methods for make completionItem
    private static String[] makeKeywords() {
        try {
            Pattern pattern = Pattern.compile("^[a-zA-Z].*");
            Method method = ZenScriptLexer.class.getDeclaredMethod("makeLiteralNames");
            method.setAccessible(true);
            String[] literalNames = (String[]) method.invoke(null);
            List<String> keywordList = Arrays.stream(literalNames)
                .filter(Objects::nonNull)
                .map(literal -> literal.replaceAll("'", ""))
                .filter(literal -> pattern.matcher(literal).matches())
                .collect(Collectors.toList());
            return keywordList.toArray(new String[]{});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    private Range getImportInsertPlace() {
        List<ImportDeclarationContext> imports = ((CompilationUnitContext) unit.getParseTree()).importDeclaration();

        if (!imports.isEmpty()) {
            ImportDeclarationContext last = imports.get(imports.size() - 1);

            int line = last.stop.getLine() - 1;
            int column = last.stop.getCharPositionInLine() + last.stop.getText().length();

            return new Range(line, column + 1, line, column + 1);
        }
        // no imports, add before first node
        Token start = ((CompilationUnitContext) unit.getParseTree()).start;

        List<Token> preprocessors = unit.getTokenStream().getHiddenTokensToLeft(start.getTokenIndex(), ZenScriptLexer.PREPROCESSOR_CHANNEL);
        if (!preprocessors.isEmpty()) {
            Token last = preprocessors.get(preprocessors.size() - 1);

            int line = last.getLine() - 1;
            int column = last.getCharPositionInLine() + last.getText().length();

            return new Range(line, column + 1, line, column + 1);
        }

        // import at first line
        return new Range(0, 0, 0, 0);

    }

    private List<TextEdit> makeAutoImports(ClassSymbol symbol) {
        TextEdit textEdit = new TextEdit();

        Range range = getImportInsertPlace();
        textEdit.setRange(Ranges.toLSPRange(range));
        if (range.startColumn == 0) {
            textEdit.setNewText("import " + symbol.getQualifiedName() + ";\n");
        } else {
            textEdit.setNewText("\nimport " + symbol.getQualifiedName() + ";");
        }
        return Collections.singletonList(textEdit);
    }

    private CompletionItem makeItem(Symbol symbol) {
        CompletionItem item = new CompletionItem();
        if (symbol instanceof ClassSymbol) {
            // class symbol name may contain package.
            String name = StringUtils.getSimpleName(((ClassSymbol) symbol).getQualifiedName());
            item.setLabel(name);
        } else {
            item.setLabel(symbol.getName());
        }
        item.setKind(getCompletionItemKind(symbol));
        item.setDetail(symbol.toString());
//        item.setData();
        return item;
    }

    private CompletionItem makePackage(String packageName, boolean isRoot) {
        CompletionItem item = new CompletionItem();
        item.setLabel(packageName);
        item.setKind(CompletionItemKind.Module);
        item.setDetail(packageName);
//        item.setData();
        return item;
    }

    private CompletionItem makeFunction(FunctionSymbol function, boolean addParens) {
        CompletionItem item = new CompletionItem();

        // build label
        StringBuilder labelBuilder = new StringBuilder();
        labelBuilder.append(function.getName()).append("(");

        List<Type> paramTypes = function.getType().paramTypes;
        List<String> paramNames = function.getParamNames();
        for (int i = 0; i < paramTypes.size(); i++) {
            Type paramType = paramTypes.get(i);
            String paramName = paramNames.get(i);
            labelBuilder.append(paramName).append(" as ").append(paramType.toString());
            if (i < paramTypes.size() - 1) {
                labelBuilder.append(", ");
            }
        }
        labelBuilder.append(")");
        item.setLabel(labelBuilder.toString());
        item.setKind(CompletionItemKind.Function);
        item.setInsertText(function.getName());
        item.setFilterText(function.getName());
        item.setDetail(function.getReturnType() + " " + function);
//        item.setData();
        if (addParens) {
            if (paramTypes.isEmpty()) {
                item.setInsertText(function.getName() + "()$0");
            } else {
                StringBuilder insertTextBuilder = new StringBuilder();
                insertTextBuilder.append(function.getName()).append("(");

                for (int i = 0; i < paramTypes.size(); i++) {
                    String paramName = paramNames.get(i);
                    insertTextBuilder.append("${").append(i + 1).append(":")
                        .append(paramName)
                        .append("}");
                    if (i < paramNames.size() - 1) {
                        insertTextBuilder.append(", ");
                    }
                }
                insertTextBuilder.append(")$0");
                item.setInsertText(insertTextBuilder.toString());
                // Activate signatureHelp
                // see https://github.com/microsoft/vscode/issues/78806
                Command command = new Command();
                item.setCommand(command);
                command.setCommand("editor.action.triggerParameterHints");
                command.setTitle("Trigger Parameter Hints");
            }
            item.setInsertTextFormat(InsertTextFormat.Snippet);
        }
        return item;
    }

    // grouping overloads
    private CompletionItem makeFunctions(List<FunctionSymbol> overloads, boolean addParens) {
        FunctionSymbol first = overloads.get(0);
        CompletionItem item = new CompletionItem();
        item.setLabel(first.getName());
        item.setKind(CompletionItemKind.Function);
        item.setDetail(first.getReturnType() + " " + first);
//        item.setData();
        if (addParens) {
            List<Type> paramTypes = first.getType().paramTypes;
            if (overloads.size() == 1 && paramTypes.isEmpty()) {
                item.setInsertText(first.getName() + "()$0");
            } else {
                item.setInsertText(first.getName() + "($0)");
                // Activate signatureHelp
                // see https://github.com/microsoft/vscode/issues/78806
                Command command = new Command();
                item.setCommand(command);
                command.setCommand("editor.action.triggerParameterHints");
                command.setTitle("Trigger Parameter Hints");
            }
            item.setInsertTextFormat(InsertTextFormat.Snippet);
        }
        return item;
    }


}
