package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.ConstructorDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FunctionDeclarationContext;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Kind;
import raylras.zen.code.type.Type;
import raylras.zen.code.type.VoidType;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    public FunctionSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(new NameResolver());
    }

    @Override
    public Type getType() {
        return owner.accept(new TypeResolver(unit));
    }

    @Override
    public Kind getKind() {
        return Kind.FUNCTION;
    }

    public List<VariableSymbol> getParams() {
        return owner.accept(new Visitor<List<VariableSymbol>>() {
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
        });
    }

    public Type getReturnType() {
        return owner.accept(new Visitor<Type>() {
            @Override
            public Type visitFunctionDeclaration(FunctionDeclarationContext ctx) {
                return new AnyType();
            }

            @Override
            public Type visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
                return new VoidType();
            }
        });
    }

}
