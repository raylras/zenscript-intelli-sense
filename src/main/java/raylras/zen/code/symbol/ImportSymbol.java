package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.scope.Scope;

import java.util.List;

public class ImportSymbol extends Symbol {

    public Object delegate;

    public ImportSymbol(Scope enclScope, ParseTree owner, CompilationUnit unit) {
        super(enclScope, owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(nameVisitor);
    }

    private final Visitor<String> nameVisitor = new Visitor<String>() {
        @Override
        public String visitImportDeclaration(ImportDeclarationContext ctx) {
            if (ctx.alias() != null) {
                return ctx.alias().getText();
            }
            List<TerminalNode> names = ctx.qualifiedName().IDENTIFIER();
            return names.get(names.size() - 1).getText();
        }
    };

}
