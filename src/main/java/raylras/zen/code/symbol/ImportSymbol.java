package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;


//TODO: is it possible to import non-class types?
public class ImportSymbol extends Symbol {

    public ImportSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return new NameResolver().resolve(getOwner());
    }

    @Override
    public Type getType() {
        if (getOwner() instanceof ImportDeclarationContext) {
            String qualifiedName = new NameResolver().resolve(((ImportDeclarationContext) getOwner()).qualifiedName());
            if (qualifiedName == null)
                return null;
            return new ClassType(qualifiedName);

        }
        return AnyType.INSTANCE;
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.ZEN_CLASS;
    }

    @Override
    public List<Symbol> getMembers() {
        Symbol symbol = getType().lookupSymbol(getUnit());
        if (symbol != null)
            return symbol.getMembers();
        return Collections.emptyList();
    }

}
