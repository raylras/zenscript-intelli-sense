package raylras.zen.code.resolve;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.data.CompletionData;
import raylras.zen.code.data.CompletionKind;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

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
public class CompletionDataResolver extends Visitor<CompletionData> {

    private final CompilationUnit unit;
    private final Range cursorPos;


    public CompletionDataResolver(CompilationUnit unit, Range cursorPos) {
        this.unit = unit;
        this.cursorPos = cursorPos;
    }

    @Override
    protected CompletionData aggregateResult(CompletionData aggregate, CompletionData nextResult) {
        if (nextResult == null) {
            return aggregate;
        }
        return nextResult;
    }

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, CompletionData currentResult) {
        return currentResult == null;
    }

    public CompletionData resolve(ParseTree node) {
        CompletionData result = null;
        if (node != null) {
            result = node.accept(this);
        }

        if (result != null) {
            return result;
        }

        return CompletionData.NONE;
    }

    @Override
    public CompletionData visitChildren(RuleNode node) {
        // skip not target position
        if (!isNodeContainsCursor(node)) {
            return null;
        }
        return super.visitChildren(node);
    }

    private boolean isNodeContainsCursor(ParseTree node) {
        if (node == null) {
            return false;
        }
        Range nodeRange = Ranges.from(node);
        return Ranges.isRangeContainsPosition(nodeRange, cursorPos.startLine, cursorPos.startColumn);
    }

    private boolean isNodeContainsCursor(ParseTree node, TerminalNode possibleNextDOT) {
        if (possibleNextDOT != null) {
            return isNodeContainsCursor(possibleNextDOT);
        }
        return isNodeContainsCursor(node);

    }

    // if node is possibly unavailable, calculate range
    private boolean isNodeContainsCursor(ParserRuleContext parentNode, Token previousToken) {
        Range nodeRange = new Range(
            previousToken.getLine() - 1, previousToken.getCharPositionInLine() + 1,
            parentNode.stop.getLine() - 1, parentNode.stop.getCharPositionInLine() + parentNode.stop.getText().length());
        return Ranges.isRangeContainsPosition(nodeRange, cursorPos.startLine, cursorPos.startColumn);
    }

    @Override
    public CompletionData visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        ZenScriptParser.QualifiedNameContext qualifiedNameContext = ctx.qualifiedName();

        TerminalNode nextDOTNode = findNextDOT(qualifiedNameContext);
        if (isNodeContainsCursor(qualifiedNameContext, nextDOTNode)) {
            return null;
        }

        String completingString = getExprTextInQualifiedExpr(qualifiedNameContext, nextDOTNode != null);

        return new CompletionData(CompletionKind.IMPORT, ctx, completingString);
    }


    @Override
    public CompletionData visitQualifiedName(ZenScriptParser.QualifiedNameContext ctx) {

        if (ctx.parent instanceof ZenScriptParser.ClassDeclarationContext) {
            return CompletionData.NONE;
        }
        TerminalNode nextDOTNode = findNextDOT(ctx);
        if (isNodeContainsCursor(ctx, nextDOTNode)) {
            return null;
        }

        String completingString = getExprTextInQualifiedExpr(ctx, nextDOTNode != null);

        return new CompletionData(CompletionKind.IDENTIFIER, ctx, completingString);
    }


    // typeLiterals

    @Override
    public CompletionData visitClassType(ZenScriptParser.ClassTypeContext ctx) {
        if (!isNodeContainsCursor(ctx)) {
            return null;
        }

        String completingString = getExprTextInQualifiedExpr(ctx.qualifiedName());
        return new CompletionData(CompletionKind.IDENTIFIER, ctx, completingString);
    }

    /**
     * when parsing partial expression like foo.bar.
     * the last DOT would not recognize as a part of QualifiedName
     * handle it.
     */
    private TerminalNode findNextDOT(ZenScriptParser.QualifiedNameContext expr) {
        ParseTree possibleNext = Nodes.getNextNode(expr);
        if (possibleNext instanceof TerminalNode && ((TerminalNode) possibleNext).getSymbol().getType() == ZenScriptParser.DOT) {
            return (TerminalNode) possibleNext;
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
        return expr.getText();
    }

    @Override
    public CompletionData visitMemberAccessExpr(ZenScriptParser.MemberAccessExprContext ctx) {
        // if cursor in left expr, not resolve this.
        if (isNodeContainsCursor(ctx.Left)) {
            return ctx.Left.accept(this);
        }
        if (!isNodeContainsCursor(ctx, ctx.Op)) {
            return null;
        }
        String completingString = getExprTextOrEmpty(ctx.simpleName());

        return new CompletionData(CompletionKind.MEMBER_ACCESS, ctx, completingString);
    }

    @Override
    public CompletionData visitIntRangeExpr(ZenScriptParser.IntRangeExprContext ctx) {
        // Only when cursor is exactly between two DOTs of the expression:
        Token dotDot = ctx.Op;
        int tokenLine = dotDot.getLine() - 1;
        int tokenBegin = dotDot.getCharPositionInLine();

        if (dotDot.getType() == ZenScriptParser.DOT_DOT &&
            tokenLine == cursorPos.startLine &&
            tokenBegin + 1 == cursorPos.startColumn) {
            // consider this as MemberAccessExpression for completion
            String completingString = "";
            return new CompletionData(CompletionKind.MEMBER_ACCESS, ctx, completingString);
        }
        // default
        return super.visitIntRangeExpr(ctx);
    }

    @Override
    public CompletionData visitArrayIndexExpr(ZenScriptParser.ArrayIndexExprContext ctx) {
        // if cursor in left expr, not resolve this.
        if (isNodeContainsCursor(ctx.Left)) {
            return ctx.Left.accept(this);
        }

        if (!isNodeContainsCursor(ctx.Index)) {
            return null;
        }

        if (ctx.Index == null) {
            // TODO: Possibly here can still return something, like all members.
            return CompletionData.NONE;
        }

        if (ctx.Index instanceof ZenScriptParser.StringLiteralExprContext) {

            ZenScriptParser.StringLiteralExprContext literal = (ZenScriptParser.StringLiteralExprContext) ctx.Index;
            String completingString = getExprTextOrEmpty(literal);
            // remove ""/''
            completingString = completingString.substring(1, completingString.length() - 2);
            return new CompletionData(CompletionKind.MEMBER_ACCESS, ctx, completingString);
        } else {
            // handle inner exprs
            return ctx.Index.accept(this);
        }
    }

    @Override
    public CompletionData visitLocalAccessExpr(ZenScriptParser.LocalAccessExprContext ctx) {
        if (!isNodeContainsCursor(ctx)) {
            return null;
        }

        String completingString = getExprTextOrEmpty(ctx.simpleName());
        return new CompletionData(CompletionKind.IDENTIFIER, ctx, completingString);
    }

    @Override
    public CompletionData visitBracketHandlerExpr(ZenScriptParser.BracketHandlerExprContext ctx) {
        if (!isNodeContainsCursor(ctx)) {
            return null;
        }

        String completingString = getExprTextOrEmpty(ctx);
        // remove <>
        completingString = completingString.substring(1, completingString.length() - 2);
        return new CompletionData(CompletionKind.BRACKET_HANDLER, ctx, completingString);

    }

    @Override
    public CompletionData visitMapEntry(ZenScriptParser.MapEntryContext ctx) {
        // replace default call of visitChildren, only visit value
        if (!isNodeContainsCursor(ctx.Value)) {
            return null;
        }
        if (ctx.Value == null) {
            return CompletionData.NONE;
        }
        return ctx.Value.accept(this);
    }


}
