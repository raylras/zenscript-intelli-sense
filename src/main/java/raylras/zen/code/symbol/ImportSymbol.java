package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

public class ImportSymbol extends Symbol {

    public ImportSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return owner.accept(new NameResolver());
    }

    @Override
    public Type getType() {
        ParseTree name;
        if (owner instanceof ImportDeclarationContext) {
            name = ((ImportDeclarationContext) owner).qualifiedName();
        } else {
            name = owner;
        }
        return new ClassType(name, unit);
    }

    @Override
    public List<Symbol> getMembers() {
        Symbol symbol = getType().lookupSymbol();
        if (symbol != null)
            return symbol.getMembers();
        return Collections.emptyList();
    }

}
