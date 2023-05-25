package raylras.zen.langserver.data;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.util.Nodes;

public class CompletionNode {

    public final CompletionKind kind;
    public final ParserRuleContext node;
    public final String completingString;

    public CompletionNode(CompletionKind kind, ParserRuleContext node, String completingString) {
        this.kind = kind;
        this.node = node;
        this.completingString = completingString;
    }

    public ZenScriptParser.ExpressionContext getQualifierExpression() {
        if (node instanceof ZenScriptParser.MemberAccessExprContext) {
            return ((ZenScriptParser.MemberAccessExprContext) node).Left;
        } else if (node instanceof ZenScriptParser.ArrayIndexExprContext) {
            return ((ZenScriptParser.ArrayIndexExprContext) node).Left;
        } else if (node instanceof ZenScriptParser.IntRangeExprContext) {
            return ((ZenScriptParser.IntRangeExprContext) node).From;
        }
        return null;
    }


    public ZenScriptParser.QualifiedNameContext getQualifiedName() {
        if (node instanceof ZenScriptParser.ImportDeclarationContext) {
            return ((ZenScriptParser.ImportDeclarationContext) node).qualifiedName();
        } else if (node instanceof ZenScriptParser.ExpandFunctionDeclarationContext) {
            return ((ZenScriptParser.ExpandFunctionDeclarationContext) node).qualifiedName();
        } else if (node instanceof ZenScriptParser.ClassTypeContext) {
            return ((ZenScriptParser.ClassTypeContext) node).qualifiedName();
        }
        return null;
    }

    public boolean isEndsWithParen() {
        ParseTree next = Nodes.getNextNode(node);
        return next instanceof TerminalNode && ((TerminalNode) next).getSymbol().getType() == ZenScriptParser.PAREN_OPEN;
    }

    public static final CompletionNode NONE = new CompletionNode(CompletionKind.NONE, null, "");

}
