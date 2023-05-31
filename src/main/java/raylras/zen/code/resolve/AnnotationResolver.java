package raylras.zen.code.resolve;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationResolver extends Visitor<List<Annotation>> {

    private final CompilationUnit unit;

    public AnnotationResolver(CompilationUnit unit) {
        this.unit = unit;
    }

    public List<Annotation> resolve(ParseTree node) {
        if (node == null)
            return Collections.emptyList();
        return node.accept(this);
    }

    @Override
    public List<Annotation> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.Declarator);
    }

    @Override
    public List<Annotation> visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.Declarator);
    }

    @Override
    public List<Annotation> visitClassDeclaration(ClassDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.ZEN_CLASS().getSymbol());
    }

    @Override
    public List<Annotation> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.ZEN_CONSTRUCTOR().getSymbol());
    }

    @Override
    public List<Annotation> visitVariableDeclaration(VariableDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.Declarator);
    }

    private List<Annotation> getAnnotationsToLeft(Token token) {
        if (token == null)
            return Collections.emptyList();
        List<Token> annoTokens = unit.getTokenStream().getHiddenTokensToLeft(token.getTokenIndex(), ZenScriptLexer.PREPROCESSOR_CHANNEL);
        return annoTokens.stream()
                .map(Token::getText)
                .map(Annotation::create)
                .collect(Collectors.toList());
    }

}
