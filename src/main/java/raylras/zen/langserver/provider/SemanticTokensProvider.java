package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensParams;
import raylras.zen.code.Range;
import raylras.zen.code.SourceUnit;
import raylras.zen.code.tree.*;
import raylras.zen.langserver.Semantics;

import java.util.ArrayList;
import java.util.List;

public class SemanticTokensProvider extends TreeVisitor {

    private final SourceUnit sourceUnit;
    private final List<Integer> data;
    private int prevLine;
    private int prevColumn;

    public SemanticTokensProvider(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit;
        this.data = new ArrayList<>();
        this.prevLine = Range.FIRST_LINE;
        this.prevColumn = Range.FIRST_COLUMN;
    }

    public static SemanticTokens semanticTokensFull(SourceUnit sourceUnit, SemanticTokensParams params) {
        if (sourceUnit == null)
            return new SemanticTokens();
        if (sourceUnit.ast == null)
            sourceUnit.updateAll(null);
//        return new SemanticTokens(new SemanticTokensProvider(sourceUnit).visitCompilationUnit(sourceUnit.ast));
        return new SemanticTokens();
    }

    private void push(TreeNode node, int tokenType, int tokenModifiers) {
        if (node == null) return;
        push(node.range, tokenType, tokenModifiers);
    }

    private void push(Range range, int tokenType, int tokenModifiers) {
        if (range == null) return;
        int line = range.startLine - prevLine;
        int column = range.startLine == prevLine ? range.startColumn - prevColumn : range.startColumn;
        int length = range.endColumn - range.startColumn;
        prevLine = range.startLine;
        prevColumn = range.startColumn;
        data.add(line);
        data.add(column);
        data.add(length);
        data.add(tokenType);
        data.add(tokenModifiers);
    }

    public static int getTokenModifiers(Declarator declarator) {
        int tokenModifiers = 0;
        switch (declarator) {
            case GLOBAL:
            case STATIC:
                tokenModifiers |= Semantics.TokenModifier.STATIC;
            case VAL:
                tokenModifiers |= Semantics.TokenModifier.READONLY;
            case VAR:
                tokenModifiers |= Semantics.TokenModifier.DECLARATION;
        }
        return tokenModifiers;
    }

//    @Override
//    public List<Integer> visitCompilationUnit(CompilationUnit node) {
//        super.visitCompilationUnit(node);
//        return data;
//    }
//
//    @Override
//    public Object visitImportDecl(ImportDecl node) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Object visitClassDecl(ClassDecl node) {
//        push(node, Semantics.TokenType.CLASS, Semantics.TokenModifier.DECLARATION);
//        super.visitClassDecl(node);
//        return null;
//    }
//
//    @Override
//    public Object visitConstructorDecl(ConstructorDecl node) {
//        push(node, Semantics.TokenType.FUNCTION, Semantics.TokenModifier.DECLARATION);
//        super.visitConstructorDecl(node);
//        return null;
//    }
//
//    @Override
//    public Object visitFunctionDecl(FunctionDecl node) {
//        push(node, Semantics.TokenType.FUNCTION, Semantics.TokenModifier.DECLARATION);
//        super.visitFunctionDecl(node);
//        return null;
//    }
//
//    @Override
//    public Object visitParameterDecl(ParameterDecl node) {
//        push(node, Semantics.TokenType.FUNCTION, Semantics.TokenModifier.DECLARATION);
//        super.visitParameterDecl(node);
//        return null;
//    }
//
//    @Override
//    public Object visitMapEntry(MapEntry node) {
//        return null;
//    }
//
//    @Override
//    public Object visitVariableDecl(VariableDecl node) {
//        push(node, Semantics.TokenType.VARIABLE, Semantics.TokenModifier.DECLARATION);
//        super.visitVariableDecl(node);
//        return null;
//    }

}
