package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.scope.Scope;

import java.util.List;

public class ClassSymbol extends Symbol {

    public Symbol superClass;
    public List<Symbol> interfaces;

    public ClassSymbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        super(enclScope, owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(nameVisitor);
    }

    private final Visitor<String> nameVisitor = new Visitor<String>() {
        @Override
        public String visitClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
            return ctx.IDENTIFIER().getText();
        }
    };

}
