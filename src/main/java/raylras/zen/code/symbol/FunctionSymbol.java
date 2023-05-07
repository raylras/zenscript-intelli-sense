package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;
import raylras.zen.code.type.VoidType;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        super(enclScope, owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(nameVisitor);
    }

    public List<VariableSymbol> getParams() {
        return owner.accept(paramsVisitor);
    }

    public Type getReturnType() {
        return owner.accept(returnTypeVisitor);
    }

    private final Visitor<String> nameVisitor = new Visitor<String>() {
        @Override
        public String visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return ctx.IDENTIFIER().getText();
        }
        @Override
        public String visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return ctx.ZEN_CONSTRUCTOR().getText();
        }
    };

    private final Visitor<List<VariableSymbol>> paramsVisitor = new Visitor<List<VariableSymbol>>() {
        @Override
        public List<VariableSymbol> visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return ctx.parameter().stream()
                    .map(unit::<VariableSymbol>getSymbol)
                    .collect(Collectors.toList());
        }
        @Override
        public List<VariableSymbol> visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return ctx.parameter().stream()
                    .map(unit::<VariableSymbol>getSymbol)
                    .collect(Collectors.toList());
        }
    };

    private final Visitor<Type> returnTypeVisitor = new Visitor<Type>() {
        @Override
        public Type visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return new AnyType();
        }
        @Override
        public Type visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return new VoidType();
        }
    };

}
