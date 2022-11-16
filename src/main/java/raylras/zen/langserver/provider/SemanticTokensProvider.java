package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.*;
import raylras.zen.ast.ASTNodeBaseVisitor;
import raylras.zen.ast.AliasNode;
import raylras.zen.ast.TextRange;
import raylras.zen.ast.TypeNameNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.type.Expression;
import raylras.zen.ast.type.Identifier;
import raylras.zen.ast.type.Node;
import raylras.zen.langserver.LanguageServerContext;
import raylras.zen.project.ZenDocument;
import raylras.zen.project.ZenProjectManager;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SemanticTokensProvider extends ASTNodeBaseVisitor<Void> {

    public enum TokenType {
        CLASS(0, SemanticTokenTypes.Class),
        PARAMETER(1, SemanticTokenTypes.Parameter),
        VARIABLE(2, SemanticTokenTypes.Variable),
        PROPERTY(3, SemanticTokenTypes.Property),
        FUNCTION(4, SemanticTokenTypes.Function),
        METHOD(5, SemanticTokenTypes.Method),
        STRING(6, SemanticTokenTypes.String),
        NUMBER(7, SemanticTokenTypes.Number);

        public final int ID;
        public final String NAME;
        TokenType(int ID, String NAME) {
            this.ID = ID;
            this.NAME = NAME;
        }
    }

    public enum TokenModifiers {
        Declaration(1<<0, SemanticTokenModifiers.Declaration),
        Definition(1<<1, SemanticTokenModifiers.Definition),
        Readonly(1<<2, SemanticTokenModifiers.Readonly),
        Static(1<<3, SemanticTokenModifiers.Static);

        public final int ID;
        public final String NAME;
        TokenModifiers(int ID, String NAME) {
            this.ID = ID;
            this.NAME = NAME;
        }
    }
    public static final SemanticTokensLegend Semantic_Tokens_Legend;

    static {
        List<String> tokenTypes = Arrays.stream(TokenType.values()).map(tokenType -> tokenType.NAME).collect(Collectors.toList());
        List<String> tokenModifiers = Arrays.stream(TokenModifiers.values()).map(modifier -> modifier.NAME).collect(Collectors.toList());
        Semantic_Tokens_Legend =  new SemanticTokensLegend(tokenTypes, tokenModifiers);
    }

    private final List<Integer> data;
    private TextRange prevRange;

    public SemanticTokensProvider() {
        this.data = new ArrayList<>();
        this.prevRange = new TextRange(1, 0, 0, 0);
    }

    public static SemanticTokens semanticTokensFull(LanguageServerContext context, SemanticTokensParams params) {
        ZenProjectManager projectManager = ZenProjectManager.getInstance(context);
        ZenDocument document = projectManager.getDocument(CommonUtils.toPath(params.getTextDocument().getUri()));
        SemanticTokensProvider provider = new SemanticTokensProvider();
        document.getAst().accept(provider);
        return new SemanticTokens(provider.data);
    }

    private void push(Node node, TokenType tokenType, int modifiers) {
        TextRange textRange = node.getTextRange();
        int line = textRange.startLine - prevRange.startLine;
        int column = textRange.startLine == prevRange.startLine ? textRange.startColumn - prevRange.startColumn : textRange.startColumn;
        int length = textRange.endColumn - textRange.startColumn;
        prevRange = textRange;

        data.add(line);
        data.add(column);
        data.add(length);
        data.add(tokenType.ID);
        data.add(modifiers);
    }

    @Override
    public Void visit(FunctionDeclarationNode node) {
        push(node.getIdentifier(), TokenType.FUNCTION, TokenModifiers.Declaration.ID);
        return super.visit(node);
    }

    @Override
    public Void visit(ImportDeclarationNode node) {
        for (Identifier pkg : node.getPackages()) {
            push(pkg, TokenType.CLASS, 0);
        }
        return super.visit(node);
    }

    @Override
    public Void visit(AliasNode node) {
        push(node, TokenType.CLASS, 0);
        return super.visit(node);
    }

    @Override
    public Void visit(ParameterDeclarationNode node) {
        push(node.getIdentifier(), TokenType.PARAMETER, TokenModifiers.Declaration.ID);
        return super.visit(node);
    }

    @Override
    public Void visit(VariableDeclarationNode node) {
        int modifier = TokenModifiers.Declaration.ID;
        switch (node.getDeclarator()) {
            case GLOBAL:
            case STATIC:
                modifier |= TokenModifiers.Static.ID;
            case VAL:
                modifier |= TokenModifiers.Readonly.ID;
        }
        push(node.getIdentifier(), TokenType.VARIABLE, modifier);
        return super.visit(node);
    }

    @Override
    public Void visit(ZenClassDeclarationNode node) {
        push(node.getIdentifier(), TokenType.CLASS, TokenModifiers.Declaration.ID);
        return super.visit(node);
    }

    @Override
    public Void visit(CallExpressionNode node) {
        Expression left = node.getLeft();
        if (left instanceof Identifier) {
            push(node.getLeft(), TokenType.FUNCTION, 0);
        } else if (left.getClass() == MemberAccessExpressionNode.class) {
            Expression right = ((MemberAccessExpressionNode) left).getRight();
            push(right, TokenType.FUNCTION, 0);
        }
        return super.visit(node);
    }

    @Override
    public Void visit(NumericLiteralExpressionNode node) {
        push(node, TokenType.NUMBER, 0);
        return super.visit(node);
    }

    @Override
    public Void visit(StringLiteralExpressionNode node) {
        push(node, TokenType.STRING, 0);
        return super.visit(node);
    }

    @Override
    public Void visit(BracketHandlerExpressionNode node) {
        return super.visit(node);
    }

    @Override
    public Void visit(TypeNameNode node) {
        push(node, TokenType.CLASS, 0);
        return super.visit(node);
    }

}
