package raylras.zen.ast.visit;

import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.*;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.type.ClassType;
import raylras.zen.ast.type.FunctionType;
import raylras.zen.ast.type.Type;

import java.util.List;

public final class DeclarationVisitor extends ZenScriptParserBaseVisitor<Declaration> {

    private final ASTBuilder builder;

    public DeclarationVisitor(ASTBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ImportDeclaration visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        if (ctx == null) return null;

        IDNode ref = IDNode.of(ctx.reference());
        Type type = new ClassType(ref.getName());
        String simpleName = ref.getName().substring(ref.getName().lastIndexOf('.') + 1);
        AliasDeclaration alias = this.visitAliasDeclaration(ctx.aliasDeclaration());
        if (alias != null) alias.setType(type);

        ImportDeclaration importDecl = new ImportDeclaration(ref, alias);
        importDecl.setType(type);
        importDecl.setRange(Range.of(ctx));
        ref.setType(importDecl.getType());

        builder.addSymbolToCurrentScope(simpleName, importDecl);

        return importDecl;
    }

    @Override
    public AliasDeclaration visitAliasDeclaration(ZenScriptParser.AliasDeclarationContext ctx) {
        if (ctx == null) return null;

        IDNode id = IDNode.of(ctx.identifier());

        AliasDeclaration alias = new AliasDeclaration(id);
        alias.setRange(Range.of(ctx));

        builder.addSymbolToCurrentScope(id.getName(), alias);

        return alias;
    }

    @Override
    public FunctionDeclaration visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        IDNode id = IDNode.of(ctx.identifier());
        List<ParameterDeclaration> params = ctx.formalParameter().stream().map(this::visitFormalParameter).toList();
        TypeDeclaration resultType = this.visitTypeDeclaration(ctx.type());
        BlockNode block = builder.getStmtVisitor().visitBlock(ctx.block());
        builder.popScope();

        FunctionDeclaration funcDecl = new FunctionDeclaration(id, params, resultType, block);
        funcDecl.setType(FunctionType.of(funcDecl));
        funcDecl.setRange(Range.of(ctx));
        id.setType(funcDecl.getType());

        builder.addSymbolToCurrentScope(id.getName(), funcDecl);

        return funcDecl;
    }

    @Override
    public ParameterDeclaration visitFormalParameter(ZenScriptParser.FormalParameterContext ctx) {
        if (ctx == null) return null;

        IDNode id = IDNode.of(ctx.identifier());
        Expression defaultValue = builder.getExprVisitor().visitDefaultValue(ctx.defaultValue());
        TypeDeclaration typeDecl = this.visitTypeDeclaration(ctx.type());

        ParameterDeclaration paramDecl = new ParameterDeclaration(id, typeDecl, defaultValue);
        paramDecl.setRange(Range.of(ctx));
        if (typeDecl != null) {
            paramDecl.setType(typeDecl.getType());
        } else {
            if (defaultValue != null) {
                paramDecl.setType(defaultValue.getType());
            }
        }
        id.setType(paramDecl.getType());

        builder.addSymbolToCurrentScope(id.getName(), paramDecl);

        return paramDecl;
    }

    @Override
    public ZenClassDeclaration visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        IDNode id = IDNode.of(ctx.identifier());
        List<VariableDeclStatement> propDecls = ctx.variableDeclStatement().stream().map(builder.getStmtVisitor()::visitVariableDeclStatement).toList();
        List<ConstructorDeclaration> ctorDecls = ctx.constructorDeclaration().stream().map(this::visitConstructorDeclaration).toList();
        List<FunctionDeclaration> funcDecls = ctx.functionDeclaration().stream().map(this::visitFunctionDeclaration).toList();
        builder.popScope();

        ZenClassDeclaration zenClass = new ZenClassDeclaration(id, propDecls, ctorDecls, funcDecls);
        zenClass.setRange(Range.of(ctx));
        id.setType(zenClass.getType());

        builder.addSymbolToCurrentScope(id.getName(), zenClass);

        return zenClass;
    }

    @Override
    public ConstructorDeclaration visitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        List<ParameterDeclaration> params = ctx.formalParameter().stream().map(this::visitFormalParameter).toList();
        BlockNode block = builder.getStmtVisitor().visitBlock(ctx.block());
        builder.popScope();

        ConstructorDeclaration ctor = new ConstructorDeclaration(params, block);
        ctor.setRange(Range.of(ctx));

        return ctor;
    }

    public TypeDeclaration visitTypeDeclaration(ZenScriptParser.TypeContext ctx) {
        if (ctx == null) return null;

        Type type = builder.getTypeVisitor().visitType(ctx);
        Symbol symbol = builder.findSymbolInCurrentScope(ctx.getText()).orElse(null);

        TypeDeclaration typeDecl = new TypeDeclaration(symbol);
        typeDecl.setType(type);
        typeDecl.setRange(Range.of(ctx));

        return typeDecl;
    }

    public VariableDeclaration visitVariableDeclaration(ZenScriptParser.IdentifierContext ctx) {
        if (ctx == null) return null;

        IDNode id = new IDNode(ctx.getText());
        id.setRange(Range.of(ctx));
        VariableDeclaration varDecl = new VariableDeclaration(id);
        varDecl.setRange(Range.of(ctx));

        builder.addSymbolToCurrentScope(id.getName(), varDecl);

        return varDecl;
    }

}
