package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.type.AnyType;
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
        return new NameResolver().resolve(owner);
    }

    @Override
    public Type getType() {
        if (owner instanceof ImportDeclarationContext) {
            String qualifiedName = new NameResolver().resolve(((ImportDeclarationContext) owner).qualifiedName());
            if (qualifiedName == null)
                return null;
            return new ClassType(qualifiedName);

        }
        return AnyType.INSTANCE;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public List<Symbol> getMembers() {
        Symbol symbol = getType().lookupSymbol(unit);
        if (symbol != null)
            return symbol.getMembers();
        return Collections.emptyList();
    }

}
