package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.data.CompletionData;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.resolve.CompletionDataResolver;
import raylras.zen.code.resolve.ExpressionSymbolResolver;
import raylras.zen.code.resolve.ExpressionTypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.symbol.ZenSymbolKind;
import raylras.zen.code.type.*;
import raylras.zen.l10n.L10N;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;
import raylras.zen.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CompletionProvider {

    private final CompilationUnit unit;
    private final List<CompletionItem> data = new ArrayList<>();

    private final CompletionData completionData;

    private static final String[] KEYWORDS = makeKeywords();

    public CompletionProvider(CompilationUnit unit, CompletionData completionNode) {
        this.unit = unit;
        this.completionData = completionNode;
    }

    public static List<CompletionItem> completion(CompilationUnit unit, CompletionParams params) {
        Range cursorPos = Ranges.from(params.getPosition());
        CompletionData completionData = new CompletionDataResolver(unit, cursorPos).resolve(unit.parseTree);
        CompletionProvider provider = new CompletionProvider(unit, completionData);
        provider.complete();
        return provider.data;
    }

    private void complete() {


        switch (completionData.kind) {
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
            case NONE:
                completeDefault();
                break;
        }

    }


    // basic completion methods
    private void completeIdentifier() {
        completeLocalSymbols();
        completeGlobalSymbols();
        completeKeywords();
    }

    private void completeImport() {
        completeGlobalSymbols();
    }

    private void completeBracketHandler() {

    }

    private void completeMemberAccess() {
        ExpressionContext qualifierExpr = completionData.getQualifierExpression();
        if (qualifierExpr == null) {
            return;
        }
        Symbol qualifierSymbol = new ExpressionSymbolResolver(unit).resolve(qualifierExpr);

        boolean isStaticAccess = qualifierSymbol != null && qualifierSymbol.getKind() == ZenSymbolKind.CLASS;
        Type type;
        if (qualifierSymbol != null) {
            type = qualifierSymbol.getType();
        } else {
            type = new ExpressionTypeResolver(unit).resolve(qualifierExpr);
        }

        if (type == null) {
            return;
        }

        boolean endWithParen = completionData.isEndsWithParen();

        addMemberAccess(type, isStaticAccess, endWithParen);
    }

    private void completeDefault() {
        completeKeywords();
    }


    private void completeLocalSymbols() {
        Scope scope = unit.lookupScope(completionData.node);
        if (scope == null)
            return;
        for (Symbol symbol : scope.symbols) {
            if (isNameMatchesCompleting(symbol.getName())) {
                CompletionItem item = new CompletionItem(symbol.getName());
                item.setDetail(symbol.getType().toString());
                item.setKind(getCompletionItemKind(symbol.getKind()));
                data.add(item);
            }
        }
    }

    private void completeGlobalSymbols() {
        for (Symbol member : unit.context.getGlobals()) {
            if (isNameMatchesCompleting(member.getName())) {
                CompletionItem item = new CompletionItem(member.getName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private boolean isNameMatchesCompleting(String candidate) {
        return StringUtils.matchesPartialName(candidate, completionData.completingString);
    }

    private void addMemberAccess(Type type, boolean isStatic, boolean endsWithParen) {
        Symbol target = type.lookupSymbol(unit);

        if (target == null)
            return;

//        HashMap<String, List<FunctionSymbol>> functions = new HashMap<>();
        for (Symbol member : target.getMembers()) {
            if (isStatic != member.isDeclaredBy(Declarator.STATIC))
                continue;

            if (!isNameMatchesCompleting(member.getName())) {
                continue;
            }

            if (member.getKind() == ZenSymbolKind.FUNCTION) {
//                functions.computeIfAbsent(member.getName(), n -> new ArrayList<>())
//                    .add((FunctionSymbol) member);
                data.add(makeFunction((FunctionSymbol) member, !endsWithParen));
            } else {
                data.add(makeItem(member));
            }

        }

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
                data.add(item);
            }
        }
    }

    private static CompletionItemKind getCompletionItemKind(ZenSymbolKind kind) {
        switch (kind) {
            case FUNCTION:
                return CompletionItemKind.Function;
            case CLASS:
                return CompletionItemKind.Class;
            case VARIABLE:
            case NONE:
                return CompletionItemKind.Variable;
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


    private CompletionItem makeItem(Symbol symbol) {
        if (symbol.getKind() == ZenSymbolKind.FUNCTION)
            throw new RuntimeException("Method symbol should use makeMethod()");
        CompletionItem item = new CompletionItem();
        item.setLabel(symbol.getName());
        item.setKind(getCompletionItemKind(symbol.getKind()));
        item.setDetail(symbol.toString());
//        item.setData();
        return item;
    }


    private CompletionItem makeFunction(FunctionSymbol function, boolean addParens) {
        CompletionItem item = new CompletionItem();

        // build label
        StringBuilder labelBuilder = new StringBuilder();
        labelBuilder.append(function.getName()).append("(");

        List<VariableSymbol> params = function.getParams();
        for (int i = 0; i < params.size(); i++) {
            VariableSymbol param = params.get(i);
            labelBuilder.append(param.getName()).append(" as ").append(param.getType().toString());
            if (i < params.size() - 1) {
                labelBuilder.append(", ");
            }
        }
        labelBuilder.append(")");
        item.setLabel(labelBuilder.toString());
        item.setKind(CompletionItemKind.Function);
        item.setFilterText(function.getName());
        item.setDetail(function.getReturnType() + " " + function);
//        item.setData();
        if (addParens) {
            if (params.isEmpty()) {
                item.setInsertText(function.getName() + "()$0");
            } else {
                StringBuilder insertTextBuilder = new StringBuilder();
                insertTextBuilder.append(function.getName()).append("(");

                for (int i = 0; i < params.size(); i++) {
                    VariableSymbol param = params.get(i);
                    insertTextBuilder.append("${").append(i + 1).append(":")
                        .append(param.getName())
                        .append("}");
                    if (i < params.size() - 1) {
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
            if (overloads.size() == 1 && first.getParams().isEmpty()) {
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
