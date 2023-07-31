package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.langserver.provider.data.Keywords;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Logger;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;
import raylras.zen.util.l10n.L10N;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompletionProvider extends Visitor<Void> {

    private static final Logger logger = Logger.getLogger("completion");

    private final CompilationUnit unit;
    private final Range cursor;
    private final ParseTree cursorNode;
    private final Range cursorNodeRange;
    private final Scope cursorNodeScope;
    private final String cursorString;
    private final TerminalNode prevNode;
    private final Range prevNodeRange;

    private final List<CompletionItem> completionData = new ArrayList<>();

    public CompletionProvider(CompilationUnit unit, Range cursor) {
        this.unit = unit;
        this.cursor = cursor;
        this.cursorNode = CSTNodes.getCstAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
        this.cursorNodeRange = Ranges.of(cursorNode);
        this.cursorNodeScope = unit.lookupScope(cursorNode);
        this.cursorString = getTextBeforeCursor(cursorNode);
        this.prevNode = CSTNodes.getPrevTerminal(unit.getTokenStream(), cursorNode);
        this.prevNodeRange = Ranges.of(prevNode);
    }

    public static CompletionList completion(CompilationUnit unit, CompletionParams params) {
        Range cursor = Ranges.of(params.getPosition());
        CompletionProvider provider = new CompletionProvider(unit, cursor);
        return new CompletionList(provider.complete());
    }

    /* Visitor Overrides */

    private List<CompletionItem> complete() {
        unit.accept(this);
        return completionData;
    }

    @Override
    public Void visitImportDeclaration(ImportDeclarationContext ctx) {
        if (isPrevNodeOfCursor(ctx.qualifiedName())) {
            if (Ranges.contains(ctx.qualifiedName(), cursorNodeRange)) {
                // import foo.bar|
                // continue to complete the package name
                String completingString = getTextBeforeCursor(ctx.qualifiedName());
                completeImports(completingString);
            } else {
                // import foo.bar |
                // cannot complete the package name, only the keyword 'as'
                completeKeywords(cursorString, Keywords.AS);
            }
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitFormalParameter(FormalParameterContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitFunctionBody(FunctionBodyContext ctx) {
        if (isPrevNodeOfCursor(ctx.BRACE_OPEN())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitVariableDeclaration(VariableDeclarationContext ctx) {
        if (isPrevNodeOfCursor(ctx.simpleName())) {
            completeKeywords(cursorString, Keywords.AS);
            return null;
        }
        if (isPrevNodeOfCursor(ctx.SEMICOLON())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitBlockStatement(BlockStatementContext ctx) {
        if (isPrevNodeOfCursor(ctx.BRACE_OPEN())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
            return null;
        }
        if (isPrevNodeOfCursor(ctx.BRACE_CLOSE())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitReturnStatement(ReturnStatementContext ctx) {
        if (isPrevNodeOfCursor(ctx.RETURN())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            return null;
        }
        if (isPrevNodeOfCursor(ctx.SEMICOLON())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitIfStatement(IfStatementContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitWhileStatement(WhileStatementContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatementContext ctx) {
        if (isPrevNodeOfCursor(ctx.expression())
                && isNextTokenOfNode(ZenScriptLexer.DOT, ctx.expression())) {
            Type type = TypeResolver.getType(ctx.expression(), unit);
            if (type != null) {
                completeInstanceMembers("", type);
            }
            return null;
        }
        if (isPrevNodeOfCursor(ctx.SEMICOLON())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitSimpleNameExpr(SimpleNameExprContext ctx) {
        completeLocalSymbols(cursorString);
        completeGlobalSymbols(cursorString);
        completeKeywords(cursorString, Keywords.TRUE, Keywords.FALSE);
        return null;
    }

    @Override
    public Void visitCallExpr(CallExprContext ctx) {
        if (isPrevNodeOfCursor(ctx.PAREN_OPEN())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
        }
        // FIXME
//        for (TerminalNode comma : ctx.COMMA()) {
//            if (isPrevNodeOfCursor(comma)) {
//                completeLocalSymbols(cursorString);
//                completeGlobalSymbols(cursorString);
//            }
//        }
        return null;
    }

    @Override
    public Void visitBinaryExpr(BinaryExprContext ctx) {
        if (isPrevTokenOfCursor(ctx.op)) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
        }
        return null;
    }

    @Override
    public Void visitAssignmentExpr(AssignmentExprContext ctx) {
        if (isPrevTokenOfCursor(ctx.op)) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitUnaryExpr(UnaryExprContext ctx) {
        if (isPrevTokenOfCursor(ctx.op)) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
        }
        return null;
    }

    @Override
    public Void visitParensExpr(ParensExprContext ctx) {
        if (isPrevNodeOfCursor(ctx.PAREN_OPEN())) {
            completeLocalSymbols(cursorString);
            completeGlobalSymbols(cursorString);
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitMemberAccessExpr(MemberAccessExprContext ctx) {
        if (isPrevNodeOfCursor(ctx.expression())) {
            Symbol symbol = cursorNodeScope.lookupSymbol(ctx.expression().getText());
            if (symbol != null) {
                completeStaticMembers("", symbol.getType());
            } else {
                Type leftType = TypeResolver.getType(ctx.expression(), unit);
                completeInstanceMembers("", leftType);
            }
            return null;
        }
        if (isPrevTokenOfCursor(ctx.op)) {
            Symbol symbol = cursorNodeScope.lookupSymbol(ctx.expression().getText());
            if (symbol != null) {
                completeStaticMembers(cursorString, symbol.getType());
            } else {
                Type leftType = TypeResolver.getType(ctx.expression(), unit);
                completeInstanceMembers(cursorString, leftType);
            }
            return null;
        }
        visitChildren(ctx);
        return null;
    }

    /* End Visitor Overrides */

    @Override
    public Void visitChildren(RuleNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            if (isPrevNodeOfCursor(child)) {
                child.accept(this);
                break;
            }
        }
        return null;
    }

    private void completeImports(String completingString) {
        // TODO
    }

    private void completeLocalSymbols(String completingString) {
        Scope scope = cursorNodeScope;
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (symbol.getSimpleName().startsWith(completingString)
                        && isPrevSymbolOfCursor(symbol)) {
                    addToCompletionData(symbol, toTypeName(symbol.getType()));
                }
            }
            scope = scope.getParent();
        }
    }

    private void completeGlobalSymbols(String completingString) {
        for (Symbol symbol : unit.getEnv().getGlobalSymbols()) {
            if (symbol.getSimpleName().startsWith(completingString)) {
                addToCompletionData(symbol, "global " + toTypeName(symbol.getType()));
            }
        }
    }

    private void completeStaticMembers(String completingString, Type type) {
        for (Symbol symbol : type.getMembers()) {
            if (symbol.getSimpleName().startsWith(completingString)
                    && symbol.isModifiedBy(Symbol.Modifier.STATIC)) {
                addToCompletionData(symbol, "static " + toTypeName(symbol.getType()));
            }
        }
    }

    private void completeInstanceMembers(String completingString, Type type) {
        for (Symbol symbol : type.getMembers()) {
            if (symbol.getSimpleName().startsWith(completingString)
                    && !symbol.isModifiedBy(Symbol.Modifier.STATIC)) {
                addToCompletionData(symbol, toTypeName(symbol.getType()));
            }
        }
    }

    private void completeKeywords(String completingString, String... keywords) {
        for (String keyword : keywords) {
            if (keyword.startsWith(completingString)) {
                addToCompletionData(keyword);
            }
        }
    }

    private void addToCompletionData(Symbol symbol, String detail) {
        CompletionItem item = new CompletionItem(symbol.getSimpleName());
        item.setKind(toCompletionKind(symbol));
        item.setDetail(detail);
        completionData.add(item);
    }

    private void addToCompletionData(String keyword) {
        CompletionItem item = new CompletionItem(keyword);
        item.setDetail(L10N.getString("completion.keyword"));
        item.setKind(CompletionItemKind.Keyword);
        completionData.add(item);
    }

    private CompletionItemKind toCompletionKind(Symbol symbol) {
        switch (symbol.getKind()) {
            case IMPORT:
            case CLASS:
                return CompletionItemKind.Class;
            case FUNCTION:
                return CompletionItemKind.Function;
            case VARIABLE:
                return CompletionItemKind.Variable;
            case BUILT_IN:
                return toCompletionKind(symbol.getType());
            case NONE:
            default:
                return null;
        }
    }

    private CompletionItemKind toCompletionKind(Type type) {
        if (type instanceof ClassType) {
            return CompletionItemKind.Class;
        } else if (type instanceof FunctionType) {
            return CompletionItemKind.Function;
        } else {
            return CompletionItemKind.Variable;
        }
    }

    private String toTypeName(Type type) {
        String typeName = Objects.toString(type);
        return replacePackageNameWithEmpty(typeName);
    }

    private String replacePackageNameWithEmpty(String typeName) {
        Pattern packageNamePattern = Pattern.compile("(\\w+\\.)+(?=\\w+)");
        Matcher matcher = packageNamePattern.matcher(typeName);
        return matcher.replaceAll("");
    }

    private String getTextBeforeCursor(ParseTree node) {
        Range nodeRange = Ranges.of(node);
        if (nodeRange.startLine != cursor.startLine || nodeRange.startLine != nodeRange.endLine) {
            return "";
        }
        int length = cursor.startColumn - nodeRange.startColumn;
        String text = node.getText();
        if (length > 0) {
            return text.substring(0, length);
        }
        return "";
    }

    private boolean isPrevSymbolOfCursor(Symbol symbol) {
        return Ranges.of(symbol.getCst()).startLine < cursor.startLine;
    }

    /**
     * Check the previous node of the cursor.
     *
     * @param node the node used to check.
     * @return true if the previous node of the cursor is {@code node}, false otherwise.
     */
    private boolean isPrevNodeOfCursor(ParseTree node) {
        return Ranges.contains(node, prevNodeRange);
    }

    /**
     * Check the previous token of the cursor.
     *
     * @param token the token used to check.
     * @return true if the previous token of the cursor is {@code token}, false otherwise.
     */
    private boolean isPrevTokenOfCursor(Token token) {
        return Ranges.contains(token, prevNodeRange);
    }

    /**
     * Check the next token of the node.
     *
     * @param nextTokenType the type used to check.
     * @param node          the node used to check.
     * @return true if the type of the next token equals {@code nextTokenType},
     * false if the type is not equal or the next token is not found.
     */
    private boolean isNextTokenOfNode(int nextTokenType, ParseTree node) {
        Token nextToken = CSTNodes.getNextToken(unit.getTokenStream(), node);
        return nextTokenType == CSTNodes.getTokenType(nextToken);
    }

}
