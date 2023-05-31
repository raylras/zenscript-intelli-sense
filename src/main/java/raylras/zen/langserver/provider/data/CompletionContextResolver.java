package raylras.zen.langserver.provider.data;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.langserver.provider.data.CompletionContext.Kind;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

public class CompletionContextResolver extends Visitor<Void> {

    private final CompilationUnit unit;
    private final Range cursor;

    private Kind kind;
    private ParseTree completingNode;
    private String completingString;

    public CompletionContextResolver(CompilationUnit unit, Range cursor) {
        this.unit = unit;
        this.cursor = cursor;
    }

    public CompletionContext resolve() {
        unit.accept(this);
        return new CompletionContext(kind, completingNode, completingString);
    }

    @Override
    public Void visitImportDeclaration(ImportDeclarationContext ctx) {
        completingNode = ctx;
        kind = Kind.IMPORT;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitQualifiedName(QualifiedNameContext ctx) {
        completingNode = ctx;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitAlias(AliasContext ctx) {
        completingNode = ctx;
        kind = Kind.NONE;
        return null;
    }

    @Override
    public Void visitIdentifier(IdentifierContext ctx) {
        completingString = ctx.getText();
        return null;
    }

    @Override
    public Void visitParameter(ParameterContext ctx) {
        completingNode = ctx;
        kind = Kind.NONE;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitDefaultValue(DefaultValueContext ctx) {
        completingNode = ctx;
        kind = Kind.LOCAL_ACCESS;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatementContext ctx) {
        completingNode = ctx;
        kind = Kind.LOCAL_ACCESS;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitArgument(ArgumentContext ctx) {
        completingNode = ctx;
        kind = Kind.LOCAL_ACCESS;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitMemberAccessExpr(MemberAccessExprContext ctx) {
        kind = Kind.MEMBER_ACCESS;
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitChildren(RuleNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            if (isCursorInsideNode(child))
                child.accept(this);
        }
        return null;
    }

    @Override
    public Void visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() != ZenScriptLexer.DOT) {
            completingString = node.getText();
        } else {
            completingString = "";
        }
        return null;
    }

    @Override
    public Void visitErrorNode(ErrorNode node) {
//        if (node.getSymbol().getType() != ZenScriptLexer.DOT) {
//            completingString = node.getText();
//        } else {
//            completingString = "";
//        }
        return null;
    }

    private boolean isCursorInsideNode(ParseTree node) {
        return Ranges.isRangeContainsPosition(Ranges.from(node), cursor.startLine, cursor.startColumn);
    }

}
