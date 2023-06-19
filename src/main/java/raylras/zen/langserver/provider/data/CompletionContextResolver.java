package raylras.zen.langserver.provider.data;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.langserver.provider.data.CompletionContext.Kind;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

public class CompletionContextResolver extends Visitor<Object> {

    private final CompilationUnit unit;
    private final Range cursor;
    private final ParseTree cursorNode;
    private final String cursorString;
    private final TerminalNode prevCursorNode;
    private final Range pervCursorNodeRange;

    private Kind kind = Kind.NONE;
    private String completingString;
    private Object payload;

    public CompletionContextResolver(CompilationUnit unit, Range cursor) {
        this.unit = unit;
        this.cursor = cursor;
        this.cursorNode = Nodes.getNodeAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
        this.cursorString = getTextBeforeCursor(cursorNode);
        this.prevCursorNode = Nodes.getPrevTerminal(unit.getTokenStream(), cursorNode);
        this.pervCursorNodeRange = Ranges.of(prevCursorNode);
    }

    public CompletionContext resolve() {
        unit.accept(this);
        return new CompletionContext(kind, completingString, payload);
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

    private boolean isCompleting(ParseTree node) {
        return Ranges.of(node).contains(pervCursorNodeRange);
    }

    @Override
    public Object visitChildren(RuleNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            if (isCompleting(child)) {
                child.accept(this);
                break;
            }
        }
        return null;
    }

    @Override
    public Object visitImportDeclaration(ImportDeclarationContext ctx) {
        if (isCompleting(ctx.qualifiedName())) {
            if (Ranges.contains(ctx.qualifiedName(), cursorNode)) {
                kind = Kind.IMPORT;
                return null;
            } else {
                kind = Kind.KEYWORD;
                completingString = cursorString;
                return null;
            }
        }
        if (isCompleting(ctx.AS())) {
            kind = Kind.NONE;
        }
        if (isCompleting(ctx.alias())) {
            kind = Kind.NONE;
        }
        return null;
    }

    @Override
    public Object visitParameter(ParameterContext ctx) {
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatementContext ctx) {
        return null;
    }

    @Override
    public Object visitLocalAccessExpr(LocalAccessExprContext ctx) {
        return null;
    }

    @Override
    public Object visitMemberAccessExpr(MemberAccessExprContext ctx) {
        return null;
    }

    @Override
    public Object visitArgument(ArgumentContext ctx) {
        return null;
    }

}
