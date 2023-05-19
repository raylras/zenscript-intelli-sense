package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.data.CompletionData;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.resolve.CompletionDataResolver;
import raylras.zen.code.resolve.ExpressionSymbolResolver;
import raylras.zen.code.resolve.ExpressionTypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.*;
import raylras.zen.l10n.L10N;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

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

        boolean isStaticAccess = qualifierSymbol != null && qualifierSymbol.getKind() == Symbol.Kind.CLASS;
        Type type = null;
        if (qualifierSymbol != null) {
            type = qualifierSymbol.getType();
        } else {
            type = new ExpressionTypeResolver(unit).resolve(qualifierExpr);
        }

        if (type == null) {
            return;
        }


        if (isStaticAccess) {
            completeStaticMembers(qualifierSymbol);
        } else {
            completeInstanceMembers(type.lookupSymbol(unit));
        }
    }

    private void completeDefault() {
        completeKeywords();
    }


    private void completeLocalSymbols() {
        Scope scope = unit.lookupScope(completionData.node);
        if (scope == null)
            return;
        for (Symbol symbol : scope.symbols) {
            if (symbol.getName().startsWith(completionData.completingString)) {
                CompletionItem item = new CompletionItem(symbol.getName());
                item.setDetail(symbol.getType().toString());
                item.setKind(getCompletionItemKind(symbol.getKind()));
                data.add(item);
            }
        }
    }

    private void completeGlobalSymbols() {
        for (Symbol member : unit.context.getGlobals()) {
            if (member.getName().startsWith(completionData.completingString)) {
                CompletionItem item = new CompletionItem(member.getName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }


    private void completeStaticMembers(Symbol target) {
        if (target == null)
            return;
        for (Symbol member : target.getMembers()) {
            if (!member.isDeclaredBy(Declarator.STATIC))
                continue;
            if (member.getName().startsWith(completionData.completingString)) {
                CompletionItem item = new CompletionItem(member.getName());
                item.setDetail("static " + member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeInstanceMembers(Symbol target) {
        if (target == null)
            return;
        for (Symbol member : target.getMembers()) {
            if (member.isDeclaredBy(Declarator.STATIC))
                continue;
            if (member.getName().startsWith(completionData.completingString)) {
                CompletionItem item = new CompletionItem(member.getName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeKeywords() {
        for (String keyword : KEYWORDS) {
            if (keyword.startsWith(completionData.completingString)) {
                CompletionItem item = new CompletionItem(keyword);
                item.setKind(CompletionItemKind.Keyword);
                item.setDetail(L10N.getString("l10n.keyword"));
                data.add(item);
            }
        }
    }

    private Type getTypeOfNode(ParseTree node) {
        ExpressionContext exprCtx = getCompletingExpression(node);
        if (exprCtx == null)
            return null;
        return new ExpressionTypeResolver(unit).resolve(exprCtx);
    }

    private Symbol getSymbolOfNode(ParseTree node) {
        ExpressionContext exprCtx = getCompletingExpression(node);
        if (exprCtx == null)
            return null;
        return new ExpressionSymbolResolver(unit).resolve(exprCtx);
    }

    private static ExpressionContext getCompletingExpression(ParseTree node) {
        ParseTree current = node;
        while (current != null) {
            if (current instanceof ExpressionStatementContext)
                return ((ExpressionStatementContext) current).expression();
            if (current instanceof ArgumentContext)
                return ((ArgumentContext) current).expression();
            current = current.getParent();
        }
        return null;
    }

    private static String getCompletingString(ParseTree node) {
        String result = node.getText();
        if (result.equals("."))
            result = "";
        return result;
    }

    private static ParseTree getNodeAtPosition(ParseTree parseTree, Position position) {
        Range range = Ranges.from(position);
        return Nodes.getNodeAtPosition(parseTree, range.startLine, range.startColumn);
    }

    private static CompletionItemKind getCompletionItemKind(Symbol.Kind kind) {
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

}
