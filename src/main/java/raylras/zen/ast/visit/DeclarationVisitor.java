package raylras.zen.ast.visit;

import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.ASTBuilder;
import raylras.zen.ast.BlockNode;
import raylras.zen.ast.Range;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.type.ClassType;
import raylras.zen.ast.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public final class DeclarationVisitor extends ZenScriptParserBaseVisitor<Declaration> {

    private final ASTBuilder builder;

    public DeclarationVisitor(ASTBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ImportDeclaration visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        if (ctx == null) return null;

        String ref = ctx.reference().getText();
        Type type = new ClassType(ref);
        String simpleName = ref.substring(ref.lastIndexOf('.') + 1);
        AliasDeclaration alias = this.visitAliasDeclaration(ctx.aliasDeclaration());
        if (alias != null) alias.setType(type);

        ImportDeclaration importDecl = new ImportDeclaration(ref, alias);
        importDecl.setType(type);
        importDecl.setRange(Range.of(ctx));
        importDecl.setIdRange(Range.of(ctx.reference()));

        builder.addSymbolToCurrentScope(simpleName, importDecl);

        return importDecl;
    }

    @Override
    public AliasDeclaration visitAliasDeclaration(ZenScriptParser.AliasDeclarationContext ctx) {
        if (ctx == null) return null;

        String name = ctx.identifier().getText();

        AliasDeclaration alias = new AliasDeclaration(name);
        alias.setRange(Range.of(ctx));
        alias.setIdRange(Range.of(ctx.identifier()));

        builder.addSymbolToCurrentScope(name, alias);

        return alias;
    }

    @Override
    public FunctionDeclaration visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        String name = ctx.identifier().getText();
        List<ParameterDeclaration> params = ctx.formalParameter().stream().map(this::visitFormalParameter).collect(Collectors.toList());
        TypeDeclaration resultType = this.visitTypeDeclaration(ctx.type());
        BlockNode block = builder.getStmtVisitor().visitBlock(ctx.block());
        builder.popScope();

        FunctionDeclaration funcDecl = new FunctionDeclaration(name, params, resultType, block);
        funcDecl.setRange(Range.of(ctx));
        funcDecl.setIdRange(Range.of(ctx.identifier()));

        builder.addSymbolToCurrentScope(name, funcDecl);

        return funcDecl;
    }

    @Override
    public ParameterDeclaration visitFormalParameter(ZenScriptParser.FormalParameterContext ctx) {
        if (ctx == null) return null;

        String name = ctx.identifier().getText();
        Expression defaultValue = builder.getExprVisitor().visitDefaultValue(ctx.defaultValue());
        TypeDeclaration typeDecl = this.visitTypeDeclaration(ctx.type());

        ParameterDeclaration param = new ParameterDeclaration(name, typeDecl, defaultValue);
        param.setRange(Range.of(ctx));
        param.setIDRange(Range.of(ctx.identifier()));

        builder.addSymbolToCurrentScope(name, param);

        return param;
    }

    @Override
    public ZenClassDeclaration visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        String name = ctx.identifier().getText();
        List<VariableDeclStatement> propDecls = ctx.variableDeclStatement().stream().map(builder.getStmtVisitor()::visitVariableDeclStatement).collect(Collectors.toList());
        List<ConstructorDeclaration> ctorDecls = ctx.constructorDeclaration().stream().map(this::visitConstructorDeclaration).collect(Collectors.toList());
        List<FunctionDeclaration> funcDecls = ctx.functionDeclaration().stream().map(this::visitFunctionDeclaration).collect(Collectors.toList());
        builder.popScope();

        ZenClassDeclaration zenClass = new ZenClassDeclaration(name, propDecls, ctorDecls, funcDecls);
        zenClass.setRange(Range.of(ctx));
        zenClass.setIDRange(Range.of(ctx.identifier()));

        builder.addSymbolToCurrentScope(name, zenClass);

        return zenClass;
    }

    @Override
    public ConstructorDeclaration visitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        if (ctx == null) return null;

        builder.pushScope();
        List<ParameterDeclaration> params = ctx.formalParameter().stream().map(this::visitFormalParameter).collect(Collectors.toList());
        BlockNode block = builder.getStmtVisitor().visitBlock(ctx.block());
        builder.popScope();

        ConstructorDeclaration ctor = new ConstructorDeclaration(params, block);
        ctor.setRange(Range.of(ctx));

        return ctor;
    }

    public TypeDeclaration visitTypeDeclaration(ZenScriptParser.TypeContext ctx) {
        if (ctx == null) return null;

        return builder.findSymbolInCurrentScope(ctx.getText())
                .map(symbol -> {
                    TypeDeclaration decl = new TypeDeclaration(symbol);
                    decl.setType(symbol.getNode().getType());
                    decl.setRange(Range.of(ctx));
                    return decl;
                })
                .orElse(null);
    }

    public VariableDeclaration visitVariableDeclaration(ZenScriptParser.IdentifierContext ctx) {
        if (ctx == null) return null;

        String name = ctx.getText();
        VariableDeclaration varDecl = new VariableDeclaration(name);
        varDecl.setRange(Range.of(ctx));

        builder.addSymbolToCurrentScope(name, varDecl);

        return varDecl;
    }

}
