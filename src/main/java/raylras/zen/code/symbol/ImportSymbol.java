package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.ErrorType;
import raylras.zen.code.type.resolve.NameResolver;
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
        return new NameResolver().resolve(getOwner());
    }

    @Override
    public Type getType() {
        Symbol target = getTarget();
        if (target != null) {
            return target.getType();
        }
        String qualifiedName = new NameResolver().resolve(((ImportDeclarationContext) getOwner()).qualifiedName());
        return new ErrorType(qualifiedName);
    }

    public Symbol getTarget() {
        // TODO: finish
        return null;
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.IMPORT;
    }

    @Override
    public List<Symbol> getMembers() {
        Symbol symbol = getTarget();
        if (symbol != null)
            return symbol.getMembers();
        return Collections.emptyList();
    }

}
