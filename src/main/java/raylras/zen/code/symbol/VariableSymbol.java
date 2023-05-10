package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.ParameterContext;
import raylras.zen.code.parser.ZenScriptParser.SimpleVariableContext;
import raylras.zen.code.parser.ZenScriptParser.VariableDeclarationContext;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.Type;

public class VariableSymbol extends Symbol {

    public VariableSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(new NameResolver());
    }

    public Type getType() {
        return owner.accept(new TypeResolver(unit));
    }

    @Override
    public boolean isDeclaredBy(Declarator declarator) {
        return declarator == getDeclarator();
    }

    public Declarator getDeclarator() {
        return owner.accept(new Visitor<Declarator>() {
            @Override
            public Declarator visitParameter(ParameterContext ctx) {
                return Declarator.NONE;
            }

            @Override
            public Declarator visitVariableDeclaration(VariableDeclarationContext ctx) {
                switch (ctx.Declarator.getType()) {
                    case ZenScriptLexer.VAR:
                        return Declarator.VAR;
                    case ZenScriptLexer.VAL:
                        return Declarator.VAL;
                    case ZenScriptLexer.GLOBAL:
                        return Declarator.GLOBAL;
                    case ZenScriptLexer.STATIC:
                        return Declarator.STATIC;
                    default:
                        return null;
                }
            }

            @Override
            public Declarator visitSimpleVariable(SimpleVariableContext ctx) {
                return Declarator.NONE;
            }
        });
    }

}
