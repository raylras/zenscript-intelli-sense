package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.util.l10n.L10N;
import raylras.zen.langserver.provider.data.Keywords;
import raylras.zen.util.Logger;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompletionProvider extends Visitor<Void> {

    private static final Logger logger = Logger.getLogger("completion");

    private final CompilationUnit unit;
    private final Range cursor;
    private final ParseTree cursorNode;
    private final Range cursorNodeRange;
    private final String cursorString;
    private final TerminalNode prevCursorNode;
    private final Range prevCursorNodeRange;

    private final List<CompletionItem> completionData = new ArrayList<>();

    public CompletionProvider(CompilationUnit unit, Range cursor) {
        this.unit = unit;
        this.cursor = cursor;
        this.cursorNode = Nodes.getNodeAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
        this.cursorNodeRange = Ranges.of(cursorNode);
        this.cursorString = getTextBeforeCursor(cursorNode);
        this.prevCursorNode = Nodes.getPrevTerminal(unit.getTokenStream(), cursorNode);
        this.prevCursorNodeRange = Ranges.of(prevCursorNode);
    }

    public static CompletionList completion(CompilationUnit unit, CompletionParams params) {
        Range cursor = Ranges.of(params.getPosition());
        CompletionProvider provider = new CompletionProvider(unit, cursor);
        return new CompletionList(provider.complete());
    }

    private List<CompletionItem> complete() {
        unit.accept(this);
        return completionData;
    }

    private void completeImports(String completingString) {
        // TODO
    }

    private void completeLocalSymbols(String completingString, Scope completingScope) {
        Scope scope = completingScope;
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (symbol.getDeclaredName().startsWith(completingString)
                        && isPrevCursor(symbol)) {
                    addToCompletionData(symbol);
                }
            }
            scope = scope.getParent();
        }
    }

    private void completeGlobalSymbols(String completingString) {
        for (Symbol symbol : unit.getEnv().getGlobalSymbols()) {
            if (symbol.getDeclaredName().startsWith(completingString)) {
                addToCompletionData(symbol);
            }
        }
    }

    private void completeStaticMembers(String completingString, Type type) {
        for (Symbol symbol : type.getMembers()) {
            if (symbol.getDeclaredName().startsWith(completingString)
                    && symbol.isDeclaredBy(Declarator.STATIC)) {
                addToCompletionData(symbol);
            }
        }
    }

    private void completeInstanceMembers(String completingString, Type type) {
        for (Symbol symbol : type.getMembers()) {
            if (symbol.getDeclaredName().startsWith(completingString)
                    && !symbol.isDeclaredBy(Declarator.STATIC)) {
                addToCompletionData(symbol);
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

    private void addToCompletionData(Symbol symbol) {
        CompletionItem item = new CompletionItem(symbol.getDeclaredName());
        item.setDetail(toTypeName(symbol));
        item.setKind(toCompletionKind(symbol));
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

    private String toTypeName(Symbol symbol) {
        return Objects.toString(symbol.getType());
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

    private boolean isPrevCursor(Symbol symbol) {
        Range symbolRange = Ranges.of(symbol.getOwner());
        return symbolRange.startLine < cursor.startLine;
    }

    private boolean isPrevCursor(ParseTree node) {
        return Ranges.contains(node, prevCursorNodeRange);
    }

    private boolean isPrevCursor(Token token) {
        return Ranges.contains(token, prevCursorNodeRange);
    }

    /* Visitor Overrides */

    @Override
    public Void visitChildren(RuleNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            if (isPrevCursor(child)) {
                child.accept(this);
                break;
            }
        }
        return null;
    }

    @Override
    public Void visitImportDeclaration(ImportDeclarationContext ctx) {
        if (isPrevCursor(ctx.qualifiedName())) {
            if (Ranges.contains(ctx.qualifiedName(), cursorNodeRange)) {
                String completingString = getTextBeforeCursor(ctx.qualifiedName());
                completeImports(completingString);
            } else {
                completeKeywords(cursorString, Keywords.AS);
            }
        }
        return null;
    }

    @Override
    public Void visitParameter(ParameterContext ctx) {
        return null;
    }

    @Override
    public Void visitVariableDeclaration(VariableDeclarationContext ctx) {
        if (isPrevCursor(ctx.identifier())) {
            completeKeywords(cursorString, Keywords.AS);
        }
        if (isPrevCursor(ctx.SEMICOLON())) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
        }
        return null;
    }

    @Override
    public Void visitIfStatement(IfStatementContext ctx) {
        if (isPrevCursor(ctx.IF())) {
            visit(ctx.expression());
        }
        return null;
    }

    @Override
    public Void visitWhileStatement(WhileStatementContext ctx) {
        if (isPrevCursor(ctx.PAREN_OPEN())) {
            visit(ctx.expression());
        }
        if (isPrevCursor(ctx.expression())) {
            visit(ctx.expression());
        }
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatementContext ctx) {
        if (isPrevCursor(ctx.expression())) {
            Type type = new TypeResolver(unit).resolve(ctx.expression());
            if (type != null) {
                completeInstanceMembers("", type);
            }
        }
        if (isPrevCursor(ctx.SEMICOLON())) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
        }
        return null;
    }

    @Override
    public Void visitLocalAccessExpr(LocalAccessExprContext ctx) {
        Scope scope = unit.lookupScope(cursorNode);
        completeLocalSymbols(cursorString, scope);
        completeGlobalSymbols(cursorString);
        completeKeywords(cursorString, Keywords.TRUE, Keywords.FALSE);
        return null;
    }

    @Override
    public Void visitCallExpr(CallExprContext ctx) {
        if (isPrevCursor(ctx.PAREN_OPEN())) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
        }
        for (TerminalNode comma : ctx.COMMA()) {
            if (isPrevCursor(comma)) {
                Scope scope = unit.lookupScope(cursorNode);
                completeLocalSymbols(cursorString, scope);
                completeGlobalSymbols(cursorString);
            }
        }
        return null;
    }

    @Override
    public Void visitBinaryExpr(BinaryExprContext ctx) {
        if (isPrevCursor(ctx.Op)) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
        }
        return null;
    }

    @Override
    public Void visitUnaryExpr(UnaryExprContext ctx) {
        if (isPrevCursor(ctx.Op)) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
        }
        return null;
    }

    @Override
    public Void visitParensExpr(ParensExprContext ctx) {
        if (isPrevCursor(ctx.PAREN_OPEN())) {
            visit(ctx.expression());
        }
        if (isPrevCursor(ctx.expression())) {
            visit(ctx.expression());
        }
        return null;
    }

    @Override
    public Void visitMemberAccessExpr(MemberAccessExprContext ctx) {
        if (isPrevCursor(ctx.expression())) {
            Type leftType = new TypeResolver(unit).resolve(ctx.Left);
            completeInstanceMembers("", leftType);
        }
        if (isPrevCursor(ctx.Op)) {
            Type leftType = new TypeResolver(unit).resolve(ctx.Left);
            completeInstanceMembers(cursorString, leftType);
        }
        return null;
    }

    @Override
    public Void visitArgument(ArgumentContext ctx) {
        return null;
    }

    /* End Visitor Overrides */

}
