package raylras.zen.lsp;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.lsp.antlr.ZenScriptParser;
import raylras.zen.lsp.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.scope.CommonScope;
import raylras.zen.scope.Scope;
import raylras.zen.symbol.*;
import raylras.zen.type.Type;
import raylras.zen.type.TypeFunction;
import raylras.zen.type.TypeZenClass;
import raylras.zen.type.ZenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ZenScriptDefinitionParser extends ZenScriptParserBaseVisitor<Symbol<? extends Type>> {

    public final Scope global;
    public final Scope scriptImports; // imports
    public final Scope scriptFunctions; // functions
    public final Scope scriptStatics; // static variables
    public final Scope scriptLocals; // writable variables, readonly variables
    private final Stack<Scope> scopeStack;

    public ZenScriptDefinitionParser(String fileName, Scope global) {
        this.global = global;
        this.scriptImports = new CommonScope(global, fileName + "/Imports");
        this.scriptFunctions = new CommonScope(scriptImports, fileName + "/Functions");
        this.scriptStatics = new CommonScope(scriptFunctions, fileName + "/Statics");
        this.scriptLocals = new CommonScope(scriptStatics, fileName + "/Locals");
        this.scopeStack = new Stack<>();
        scopeStack.push(global);
        scopeStack.push(scriptImports);
        scopeStack.push(scriptFunctions);
        scopeStack.push(scriptStatics);
        scopeStack.push(scriptLocals);
    }

    @Override
    public Symbol<? extends Type> visitImportStatement(ZenScriptParser.ImportStatementContext ctx) {
        TerminalNode nRename = ctx.IDENTIFIER();
        if (nRename != null) {
            scriptImports.define(new SymbolClass(global, nRename.getSymbol()));
        }

        return super.visitImportStatement(ctx);
    }

    @Override
    public Symbol<? extends Type> visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Symbol<TypeFunction> symbol = new SymbolFunction(scopeStack.peek(), tName);

        scriptStatics.define(symbol);

        scopeStack.push(symbol);
        List<SymbolVariable<? extends Type>> arguments = new ParameterVisitor(scopeStack).visitFormalParameters(ctx.formalParameters());
        Type returnType = ZenType.getZenType(ctx.asType());
        symbol.setType(new TypeFunction(tName.getText(), returnType, arguments.stream().map(SymbolBase::getType).collect(Collectors.toList())));
        scopeStack.pop();

        return super.visitFunctionDeclaration(ctx);
    }

    @Override
    public Symbol<? extends Type> visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Symbol<TypeZenClass> symbol = new SymbolClass(scopeStack.peek(), tName);

        scriptStatics.define(symbol);

        scopeStack.push(symbol);
        super.visitZenClassDeclaration(ctx);
        scopeStack.pop();

        return super.visitZenClassDeclaration(ctx);
    }

    @Override
    public Symbol<? extends Type> visitConstructor(ZenScriptParser.ConstructorContext ctx) {
        Token tName = ctx.ZEN_CONSTRUCTOR().getSymbol();
        Symbol<TypeFunction> symbol = new SymbolFunction(scopeStack.peek(), tName);

        scopeStack.push(symbol);
        List<SymbolVariable<? extends Type>> arguments = new ParameterVisitor(scopeStack).visitFormalParameters(ctx.formalParameters());
        symbol.setType(new TypeFunction(tName.getText(), null, arguments.stream().map(SymbolBase::getType).collect(Collectors.toList())));
        scopeStack.pop();

        return super.visitConstructor(ctx);
    }

    @Override
    public Symbol<? extends Type> visitField(ZenScriptParser.FieldContext ctx) {
        return super.visitField(ctx);
    }

    @Override
    public Symbol<? extends Type> visitMethod(ZenScriptParser.MethodContext ctx) {
        return super.visitMethod(ctx);
    }

    @Override
    public Symbol<? extends Type> visitLocalVariableDeclaration(ZenScriptParser.LocalVariableDeclarationContext ctx) {
        return super.visitLocalVariableDeclaration(ctx);
    }

    @Override
    public Symbol<? extends Type> visitGlobalVariableDeclaration(ZenScriptParser.GlobalVariableDeclarationContext ctx) {
        return super.visitGlobalVariableDeclaration(ctx);
    }

    @Override
    public Symbol<? extends Type> visitAsType(ZenScriptParser.AsTypeContext ctx) {
        return super.visitAsType(ctx);
    }

    @Override
    public Symbol<? extends Type> visitFormalParameters(ZenScriptParser.FormalParametersContext ctx) {
        return super.visitFormalParameters(ctx);
    }

    @Override
    public Symbol<? extends Type> visitFormalParameter(ZenScriptParser.FormalParameterContext ctx) {
        return super.visitFormalParameter(ctx);
    }

    @Override
    public Symbol<? extends Type> visitArguments(ZenScriptParser.ArgumentsContext ctx) {
        return super.visitArguments(ctx);
    }

    @Override
    public Symbol<? extends Type> visitArgument(ZenScriptParser.ArgumentContext ctx) {
        return super.visitArgument(ctx);
    }

    static class ParameterVisitor extends ZenScriptParserBaseVisitor<List<SymbolVariable<? extends Type>>> {

        private List<SymbolVariable<? extends Type>> parameters;
        private final Stack<Scope> scopeStack;

        public ParameterVisitor(Stack<Scope> scopeStack) {
            this.scopeStack = scopeStack;
        }

        @Override
        public List<SymbolVariable<? extends Type>> visitFormalParameters(ZenScriptParser.FormalParametersContext ctx) {
            super.visitFormalParameters(ctx);
            return parameters;
        }

        @Override
        public List<SymbolVariable<? extends Type>> visitFormalParameter(ZenScriptParser.FormalParameterContext ctx) {
            Token tName = ctx.IDENTIFIER().getSymbol();
            ZenScriptParser.AsTypeContext asTypeContext = ctx.asType();
            SymbolVariable<? extends Type> symbol = new SymbolVariable(scopeStack.peek(), ZenType.getZenType(asTypeContext), tName);
            if (parameters == null) { parameters = new LinkedList<>(); }
            parameters.add(symbol);

            return null;
        }

    }

}
