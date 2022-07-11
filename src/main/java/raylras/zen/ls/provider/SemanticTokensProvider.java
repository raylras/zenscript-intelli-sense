package raylras.zen.ls.provider;

import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensParams;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.CompileUnit;
import raylras.zen.ast.Range;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.Symbol;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.DefaultVisitor;
import raylras.zen.ls.TokenModifier;
import raylras.zen.ls.TokenType;
import raylras.zen.util.PosUtils;

import java.util.*;

public class SemanticTokensProvider {

    private static final class SemanticVisitor extends DefaultVisitor<SemanticToken> {

        private final SemanticDataBuilder builder;

        public SemanticVisitor(SemanticDataBuilder builder) {
            this.builder = builder;
        }

        @Override
        public SemanticToken visit(AliasDeclaration aliasDecl) {
            return builder.push(aliasDecl.getId().getRange(), TokenType.CLASS);
        }

        @Override
        public SemanticToken visit(FunctionDeclaration funcDecl) {
            SemanticToken token = builder.push(funcDecl.getId().getRange(), TokenType.FUNCTION);
            super.visit(funcDecl);
            return token;
        }

        @Override
        public SemanticToken visit(ImportDeclaration importDecl) {
            SemanticToken token = builder.push(importDecl.getId().getRange(), TokenType.CLASS);
            super.visit(importDecl);
            return token;
        }

        @Override
        public SemanticToken visit(ParameterDeclaration paramDecl) {
            SemanticToken token = builder.push(paramDecl.getId().getRange(), TokenType.PARAMETER);
            super.visit(paramDecl);
            return token;
        }

        @Override
        public SemanticToken visit(VariableDeclaration varDecl) {
            SemanticToken token = builder.push(varDecl.getId().getRange(), TokenType.VARIABLE);
            super.visit(varDecl);
            return token;
        }

        @Override
        public SemanticToken visit(TypeDeclaration typeDecl) {
            return super.visit(typeDecl);
        }

        @Override
        public SemanticToken visit(ZenClassDeclaration classDecl) {
            SemanticToken token = builder.push(classDecl.getId().getRange(), TokenType.CLASS);
            super.visit(classDecl);
            return token;
        }

        @Override
        public SemanticToken visit(VariableDeclStatement varDeclStmt) {
            int modifiers = TokenModifier.Declaration.getId();
            if (varDeclStmt.isFinal()) {
                modifiers |= TokenModifier.Readonly.getId();
            }
            if (varDeclStmt.isStatic()) {
                modifiers |= TokenModifier.Static.getId();
            }
            if (varDeclStmt.isGlobal()) {
                modifiers |= TokenModifier.Static.getId();
            }
            SemanticToken token = builder.push(varDeclStmt.getId().getRange(), TokenType.VARIABLE, modifiers);
            super.visit(varDeclStmt);
            return token;
        }

        @Override
        public SemanticToken visit(ArgumentsExpression argsExpr) {
            return super.visit(argsExpr);
        }

        @Override
        public SemanticToken visit(BoolLiteral boolExpr) {
            return super.visit(boolExpr);
        }

        @Override
        public SemanticToken visit(BracketHandler bracketExpr) {
            return super.visit(bracketExpr);
        }

        @Override
        public SemanticToken visit(FloatLiteral floatExpr) {
            return builder.push(floatExpr.getRange(), TokenType.NUMBER);
        }

        @Override
        public SemanticToken visit(FunctionExpression funcExpr) {
            return super.visit(funcExpr);
        }

        @Override
        public SemanticToken visit(IntLiteral intExpr) {
            return builder.push(intExpr.getRange(), TokenType.NUMBER);
        }

        @Override
        public SemanticToken visit(VarAccessExpression varAccess) {
            SemanticToken token = Optional.ofNullable(varAccess.getSymbol())
                    .map(Symbol::node)
                    .map(node -> {
                        SemanticToken semanticToken = node.accept(this);
                        return builder.push(varAccess.getRange(), semanticToken.tokenType, semanticToken.modifiers);
                    }).orElse(null);
            super.visit(varAccess);
            return token;
        }

        @Override
        public SemanticToken visit(MapLiteral mapExpr) {
            return super.visit(mapExpr);
        }

        @Override
        public SemanticToken visit(MemberAccess memberAccess) {
            return super.visit(memberAccess);
        }

        @Override
        public SemanticToken visit(MemberIndexExpression memberIndex) {
            return super.visit(memberIndex);
        }

        @Override
        public SemanticToken visit(NullExpression nullExpr) {
            return super.visit(nullExpr);
        }

        @Override
        public SemanticToken visit(StringLiteral stringExpr) {
//            builder.push(stringExpr.getRange(), TokenType.STRING);
            return super.visit(stringExpr);
        }

        @Override
        public SemanticToken visit(TypeCastExpression castExpr) {
            return super.visit(castExpr);
        }
    }

    private static final class SemanticDataBuilder {
        private final Set<SemanticToken> semanticTokens = new TreeSet<>();
        private SemanticToken prevToken;

        public SemanticToken push(Range range, int tokenType, int modifiers) {
            org.eclipse.lsp4j.Range lspRange = PosUtils.toLSPRange(range);
            int line = lspRange.getStart().getLine();
            int column = lspRange.getStart().getCharacter();
            int length = range.lastColumn() - range.column();
            SemanticToken token = new SemanticToken(line, column, length, tokenType, modifiers);
            semanticTokens.add(token);
            return token;
        }

        public SemanticToken push(Range range, TokenType tokenType, TokenModifier... tokenModifiers) {
            return push(range, tokenType.ordinal(), TokenModifier.toInt(tokenModifiers));
        }

        public SemanticToken push(Range range, TokenType tokenType, int modifiers) {
            return push(range, tokenType.ordinal(), modifiers);
        }

        private List<Integer> build() {
            prevToken = new SemanticToken(0, 0, 0, 0, 0);
            return semanticTokens.stream()
                    .map(this::convert)
                    .flatMap(List::stream)
                    .toList();
        }

        private List<Integer> convert(SemanticToken token) {
            // see https://microsoft.github.io/language-server-protocol/specifications/lsp/3.17/specification/#textDocument_semanticTokens
            int line;
            int column;
            int length;
            int tokenType;
            int modifiers;

            // the token's line is always relative to the previous token's line
            line = token.line - prevToken.line;

            // use relative colum when in the same line
            if (token.line == prevToken.line) {
                column = token.column - prevToken.column;
            } else {
                // otherwise, use absolute column
                column = token.column;
            }

            length = token.length;
            tokenType = token.tokenType;
            modifiers = token.modifiers;

            prevToken = token;

            return List.of(line, column, length, tokenType, modifiers);
        }

    }

    private record SemanticToken(int line, int column, int length, int tokenType, int modifiers)
            implements Comparable<SemanticToken> {

        @Override
        public int compareTo(SemanticToken other) {
            return this.line == other.line ? this.column - other.column : this.line - other.line;
        }
    }

    public SemanticTokens provideSemanticTokens(@NotNull SemanticTokensParams params, @NotNull CompileUnit compileUnit) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        SemanticDataBuilder builder = new SemanticDataBuilder();
        new SemanticVisitor(builder).visit(scriptNode);
        return new SemanticTokens(builder.build());
    }

}
