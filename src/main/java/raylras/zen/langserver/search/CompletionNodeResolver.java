package raylras.zen.langserver.search;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.data.CompletionNode;
import raylras.zen.langserver.data.CompletionKind;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;

/**
 * This class just resolve for possible node that needs to execute auto complete progress (those not only need keywords),
 * and differs for different strategy for completion.
 * currently do not consider symbol type first.
 * appendix: all possible nodes
 * - importDeclaration
 * - qualifiedName (without classDeclaration)
 * - ClassTypeLiteral
 * - expression:
 * - MemberAccessExpr
 * - ArrayIndexExpr
 * - LocalAccessExpr
 * - BracketHandlerExpr (special)
 * - Key of MapEntryExpr should be filtered out
 * <p>
 * <p>
 * Also handles incomplete expression for special case:
 * - foo.bar. (with DOT suffix) will not recognize DOT as part of QualifierName
 * - foo..bar (two DOTS) will be recognized as IntRangeExpr
 */
public class CompletionNodeResolver extends AbstractPositionSearchResolver<CompletionNode> {

    private final CompilationUnit unit;

    public CompletionNodeResolver(CompilationUnit unit, Range cursor) {
        super(cursor);
        this.unit = unit;
    }

    public CompletionNode resolve() {
        CompletionNode result = null;
        if (unit.getParseTree() != null) {
            result = unit.getParseTree().accept(this);
        }

        if (result != null) {
            return result;
        }

        return CompletionNode.NONE;
    }

    @Override
    public CompletionNode visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        ZenScriptParser.QualifiedNameContext qualifiedNameContext = ctx.qualifiedName();

        TerminalNode nextDOTNode = findNextDOT(qualifiedNameContext);
        if (!isNodeContainsCursor(qualifiedNameContext, nextDOTNode)) {
            return null;
        }

        String completingString = getExprTextInQualifiedExpr(qualifiedNameContext, nextDOTNode != null);

        return new CompletionNode(CompletionKind.IMPORT, ctx, completingString);
    }

    @Override
    public CompletionNode visitQualifiedName(ZenScriptParser.QualifiedNameContext ctx) {

        if (ctx.parent instanceof ZenScriptParser.ClassDeclarationContext) {
            return CompletionNode.NONE;
        }
        TerminalNode nextDOTNode = findNextDOT(ctx);
        if (!isNodeContainsCursor(ctx, nextDOTNode)) {
            return null;
        }

        String completingString = getExprTextInQualifiedExpr(ctx, nextDOTNode != null);

        return new CompletionNode(CompletionKind.IDENTIFIER, ctx, completingString);
    }


    // typeLiterals

    @Override
    public CompletionNode visitClassType(ZenScriptParser.ClassTypeContext ctx) {
        if (!isNodeContainsCursor(ctx)) {
            return null;
        }

        String completingString = getExprTextInQualifiedExpr(ctx.qualifiedName());
        return new CompletionNode(CompletionKind.IDENTIFIER, ctx, completingString);
    }

    /**
     * when parsing partial expression like foo.bar.
     * the last DOT would not recognize as a part of QualifiedName
     * handle it.
     */
    private TerminalNode findNextDOT(ZenScriptParser.QualifiedNameContext expr) {
        ParseTree possibleNext = Nodes.getNextNode(expr);
        if (possibleNext instanceof TerminalNode && ((TerminalNode) possibleNext).getSymbol().getType() == ZenScriptParser.DOT) {
            TerminalNode node = (TerminalNode) possibleNext;
            if (node.getSymbol().getLine() > cursor.startLine || (node.getSymbol().getCharPositionInLine() + 1 > cursor.startColumn)) {
                return null;
            }
            return node;
        }
        return null;
    }

    private String getExprTextInQualifiedExpr(ZenScriptParser.QualifiedNameContext expr) {
        return getExprTextInQualifiedExpr(expr, findNextDOT(expr) != null);
    }

    private String getExprTextInQualifiedExpr(ZenScriptParser.QualifiedNameContext expr, boolean isNextNodeDOT) {
        String text = getExprTextOrEmpty(expr);

        if (isNextNodeDOT) {
            return "";
        } else if (text.contains(".")) {
            return text.substring(text.lastIndexOf(".") + 1);
        }
        return text;
    }

    private String getExprTextOrEmpty(ParserRuleContext expr) {
        if (expr == null) {
            return "";
        }
        return Nodes.getTextBefore(expr, cursor.startLine, cursor.startColumn);
    }

    @Override
    public CompletionNode visitMemberAccessExpr(ZenScriptParser.MemberAccessExprContext ctx) {
        // if cursor in left expr, not resolve this.
        if (isNodeContainsCursor(ctx.Left)) {
            return ctx.Left.accept(this);
        }
        if (!isNodeContainsCursor(ctx, ctx.Op)) {
            return null;
        }
        String completingString = getExprTextOrEmpty(ctx.simpleName());

        return new CompletionNode(CompletionKind.MEMBER_ACCESS, ctx, completingString);
    }

    @Override
    public CompletionNode visitIntRangeExpr(ZenScriptParser.IntRangeExprContext ctx) {
        // Only when cursor is exactly between two DOTs of the expression:
        Token dotDot = ctx.Op;
        int tokenLine = dotDot.getLine() - 1;
        int tokenBegin = dotDot.getCharPositionInLine();

        if (dotDot.getType() == ZenScriptParser.DOT_DOT &&
            tokenLine == cursor.startLine &&
            tokenBegin + 1 == cursor.startColumn) {
            // consider this as MemberAccessExpression for completion
            String completingString = "";
            return new CompletionNode(CompletionKind.MEMBER_ACCESS, ctx, completingString);
        }
        // default
        return visitChildren(ctx);
    }

    @Override
    public CompletionNode visitArrayIndexExpr(ZenScriptParser.ArrayIndexExprContext ctx) {
        // if cursor in left expr, not resolve this.
        if (isNodeContainsCursor(ctx.Left)) {
            return ctx.Left.accept(this);
        }

        if (!isNodeContainsCursor(ctx.Index)) {
            return null;
        }

        if (ctx.Index == null) {
            // TODO: Possibly here can still return something, like all members.
            return CompletionNode.NONE;
        }

        if (ctx.Index instanceof ZenScriptParser.StringLiteralExprContext) {

            ZenScriptParser.StringLiteralExprContext literal = (ZenScriptParser.StringLiteralExprContext) ctx.Index;
            String completingString = getExprTextOrEmpty(literal);
            // remove ""/''
            completingString = completingString.substring(1, completingString.length() - 2);
            return new CompletionNode(CompletionKind.MEMBER_ACCESS, ctx, completingString);
        } else {
            // handle inner exprs
            return ctx.Index.accept(this);
        }
    }

    @Override
    public CompletionNode visitLocalAccessExpr(ZenScriptParser.LocalAccessExprContext ctx) {
        if (!isNodeContainsCursor(ctx)) {
            return null;
        }

        String completingString = getExprTextOrEmpty(ctx.simpleName());
        return new CompletionNode(CompletionKind.IDENTIFIER, ctx, completingString);
    }

    @Override
    public CompletionNode visitBracketHandlerExpr(ZenScriptParser.BracketHandlerExprContext ctx) {
        if (!isNodeContainsCursor(ctx)) {
            return null;
        }

        String completingString = getExprTextOrEmpty(ctx);
        // remove <>
        completingString = completingString.substring(1, completingString.length() - 2);
        return new CompletionNode(CompletionKind.BRACKET_HANDLER, ctx, completingString);

    }

    @Override
    public CompletionNode visitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        // replace default call of visitChildren, only visit value
        if (!isNodeContainsCursor(ctx.Value)) {
            return null;
        }
        if (ctx.Value == null) {
            return CompletionNode.NONE;
        }
        return ctx.Value.accept(this);
    }


}
