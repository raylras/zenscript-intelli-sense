package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.ParameterContext;
import raylras.zen.code.parser.ZenScriptParser.SimpleVariableContext;
import raylras.zen.code.parser.ZenScriptParser.VariableDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;

public class VariableSymbol extends Symbol {

    public VariableSymbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        super(enclScope, owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(nameVisitor);
    }

    public Declarator getDeclarator() {
        return owner.accept(declaratorVisitor);
    }

    public Type getType() {
        return owner.accept(typeVisitor);
    }

    private final Visitor<String> nameVisitor = new Visitor<String>(){
        @Override
        public String visitParameter(ParameterContext ctx) {
            return ctx.IDENTIFIER().getText();
        }

        @Override
        public String visitVariableDeclaration(VariableDeclarationContext ctx) {
            return ctx.IDENTIFIER().getText();
        }
        @Override
        public String visitSimpleVariable(SimpleVariableContext ctx) {
            return ctx.IDENTIFIER().getText();
        }
    };

    private final Visitor<Declarator> declaratorVisitor = new Visitor<Declarator>() {
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
    };

    private final Visitor<Type> typeVisitor = new Visitor<Type>(){
        @Override
        public Type visitParameter(ParameterContext ctx) {
            return new AnyType();
        }
        @Override
        public Type visitVariableDeclaration(VariableDeclarationContext ctx) {
            return new AnyType();
        }
        @Override
        public Type visitSimpleVariable(SimpleVariableContext ctx) {
            return new AnyType();
        }
    };

}
