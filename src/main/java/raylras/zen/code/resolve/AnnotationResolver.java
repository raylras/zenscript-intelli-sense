package raylras.zen.code.resolve;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AnnotationResolver {

    private AnnotationResolver() {}

    public static List<Annotation> getAnnotations(ParseTree cst, CommonTokenStream tokens) {
        Objects.requireNonNull(cst);
        Objects.requireNonNull(tokens);
        List<Annotation> result = cst.accept(new AnnotationVisitor(tokens));
        if (result != null) {
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private static final class AnnotationVisitor extends Visitor<List<Annotation>> {
        private final CommonTokenStream tokens;

        public AnnotationVisitor(CommonTokenStream tokens) {
            this.tokens = tokens;
        }

        @Override
        public List<Annotation> visitCompilationUnit(CompilationUnitContext ctx) {
            return getRight(0);
        }

        @Override
        public List<Annotation> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return ctx.prefix == null ? getLeft(ctx.FUNCTION()) : getLeft(ctx.prefix);
        }

        @Override
        public List<Annotation> visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return getLeft(ctx.EXPAND());
        }

        @Override
        public List<Annotation> visitClassDeclaration(ClassDeclarationContext ctx) {
            return getLeft(ctx.ZEN_CLASS());
        }

        @Override
        public List<Annotation> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return getLeft(ctx.ZEN_CONSTRUCTOR());
        }

        @Override
        public List<Annotation> visitVariableDeclaration(VariableDeclarationContext ctx) {
            return getLeft(ctx.prefix);
        }

        private List<Annotation> getLeft(TerminalNode node) {
            if (node != null) {
                return getLeft(node.getSymbol().getTokenIndex());
            } else {
                return Collections.emptyList();
            }
        }

        private List<Annotation> getLeft(Token token) {
            if (token != null) {
                return getLeft(token.getTokenIndex());
            } else {
                return Collections.emptyList();
            }
        }

        private List<Annotation> getLeft(int tokenIndex) {
            List<Token> annotationTokens = tokens.getHiddenTokensToLeft(tokenIndex, ZenScriptLexer.PREPROCESSOR_CHANNEL);
            return toAnnotationList(annotationTokens);
        }

        private List<Annotation> getRight(int tokenIndex) {
            List<Token> annotationTokens = tokens.getHiddenTokensToRight(tokenIndex, ZenScriptLexer.PREPROCESSOR_CHANNEL);
            return toAnnotationList(annotationTokens);
        }

        private List<Annotation> toAnnotationList(List<Token> tokens) {
            if (tokens == null) {
                return Collections.emptyList();
            }
            return tokens.stream()
                    .map(Token::getText)
                    .map(Annotation::create)
                    .collect(Collectors.toList());
        }
    }

}
