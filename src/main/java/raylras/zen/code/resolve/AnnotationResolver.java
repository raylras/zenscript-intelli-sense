package raylras.zen.code.resolve;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
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
        if (node != null) {
            return node.accept(this);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Annotation> visitCompilationUnit(CompilationUnitContext ctx) {
        return getAnnotationsToRight(0);
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
        return getAnnotationsToLeft(ctx.ZEN_CLASS());
    }

    @Override
    public List<Annotation> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.ZEN_CONSTRUCTOR());
    }

    @Override
    public List<Annotation> visitVariableDeclaration(VariableDeclarationContext ctx) {
        return getAnnotationsToLeft(ctx.Declarator);
    }

    private List<Annotation> getAnnotationsToLeft(TerminalNode node) {
        if (node != null) {
            return getAnnotationToToLeft(node.getSymbol().getTokenIndex());
        } else {
            return Collections.emptyList();
        }
    }

    private List<Annotation> getAnnotationsToLeft(Token token) {
        if (token != null) {
            return getAnnotationToToLeft(token.getTokenIndex());
        } else {
            return Collections.emptyList();
        }
    }

    private List<Annotation> getAnnotationToToLeft(int tokenIndex) {
        List<Token> annotationTokens = unit.getTokenStream().getHiddenTokensToLeft(tokenIndex, ZenScriptLexer.PREPROCESSOR_CHANNEL);
        return toAnnotations(annotationTokens);
    }

    private List<Annotation> getAnnotationsToRight(int tokenIndex) {
        List<Token> annotationTokens = unit.getTokenStream().getHiddenTokensToRight(tokenIndex, ZenScriptLexer.PREPROCESSOR_CHANNEL);
        return toAnnotations(annotationTokens);
    }

    private List<Annotation> toAnnotations(List<Token> annotationTokens) {
        return annotationTokens.stream()
                .map(Token::getText)
                .map(Annotation::create)
                .collect(Collectors.toList());
    }

}
