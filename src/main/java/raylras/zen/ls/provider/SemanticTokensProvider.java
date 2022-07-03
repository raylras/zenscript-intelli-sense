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
            SemanticToken token = builder.push(importDecl.getReference().getRange(), TokenType.CLASS);
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
            SemanticToken token = varAccess.getSymbol()
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
            builder.push(stringExpr.getRange(), TokenType.STRING);
            return super.visit(stringExpr);
        }

        @Override
        public SemanticToken visit(TypeCastExpression castExpr) {
            return super.visit(castExpr);
        }
    }

    private static final class SemanticDataBuilder {
        private final List<Integer> semanticData = new ArrayList<>();
        private final Set<SemanticToken> semanticTokenSet = new TreeSet<>();
        private SemanticToken prevToken;

        public List<Integer> getSemanticData() {
            return semanticData;
        }

        public SemanticToken push(Range range, TokenType tokenType, TokenModifier... tokenModifiers) {
            org.eclipse.lsp4j.Range lspRange = PosUtils.toLSPRange(range);
            int line = lspRange.getStart().getLine();
            int column = lspRange.getStart().getCharacter();
            int length = range.lastColumn() - range.column();
            int modifiers = TokenModifier.toBitFlag(tokenModifiers);
            SemanticToken token = new SemanticToken(line, column, length, tokenType, modifiers);
            semanticTokenSet.add(token);
            return token;
        }

        public SemanticToken push(Range range, TokenType tokenType, int modifiers) {
            org.eclipse.lsp4j.Range lspRange = PosUtils.toLSPRange(range);
            int line = lspRange.getStart().getLine();
            int column = lspRange.getStart().getCharacter();
            int length = range.lastColumn() - range.column();
            SemanticToken token = new SemanticToken(line, column, length, tokenType, modifiers);
            semanticTokenSet.add(token);
            return token;
        }

        private void build() {
            prevToken = new SemanticToken(0, 0, 0, null, 0); // first position of LSP4J
            semanticTokenSet.forEach(this::collectData);
        }

        private void collectData(SemanticToken token) {
            semanticData.addAll(convertToDataUnit(token));
            prevToken = token;
        }

        private List<Integer> convertToDataUnit(SemanticToken token) {
            // index:     0     1       2       3           4
            // tokenData: line  column  length  tokenType   modifiers
            int[] dataUnit = new int[5];

            // the token's line is always relative to the previous token's line
            dataUnit[0] = token.line - prevToken.line;

            if (token.line == prevToken.line) {
                // use relative colum when in the same line
                dataUnit[1] = token.column - prevToken.column;
            } else {
                // otherwise use absolute column
                dataUnit[1] = token.column;
            }

            dataUnit[2] = token.length;
            dataUnit[3] = token.tokenType.ordinal();
            dataUnit[4] = token.modifiers;

            return Arrays.stream(dataUnit).boxed().toList();
        }

        private void clear() {
            semanticData.clear();
            semanticTokenSet.clear();
        }

    }

    private record SemanticToken(int line, int column, int length, TokenType tokenType, int modifiers)
            implements Comparable<SemanticToken> {

        @Override
        public int compareTo(SemanticToken other) {
            return this.line == other.line ? this.column - other.column : this.line - other.line;
        }

        @Override
        public String toString() {
            return String.format("%s<%d:%d>", tokenType.getName(), line, column);
        }
    }

    private final SemanticDataBuilder builder;
    private final SemanticVisitor visitor;

    public SemanticTokensProvider() {
        this.builder = new SemanticDataBuilder();
        this.visitor = new SemanticVisitor(builder);
    }

    public SemanticTokens provideSemanticTokens(@NotNull SemanticTokensParams params, @NotNull CompileUnit compileUnit) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        builder.clear();
        visitor.visit(scriptNode);
        builder.build();
        return new SemanticTokens(builder.getSemanticData());
    }

}
