package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.*;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.service.EnvironmentService;
import raylras.zen.util.MemberUtils;
import raylras.zen.util.Tuple;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ImportSymbol extends Symbol {

    public ImportSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return NameResolver.resolveName(getOwner());
    }

    @Override
    public Type getType() {
        Tuple<Symbol, List<FunctionSymbol>> target = resolveTarget();
        if (target.first == null && target.second.isEmpty()) {
            String qualifiedName = NameResolver.resolveName(((ImportDeclarationContext) getOwner()).qualifiedName());
            return new ErrorType(qualifiedName);
        }

        if (target.first == null) {
            if (target.second.size() == 1) {
                return target.second.get(0).getType();
            }
            // don't know because it is method overload
            return AnyType.INSTANCE;
        }

        return target.first.getType();
    }

    public boolean isFunctionImport() {
        return !resolveTarget().second.isEmpty();
    }

    private Tuple<Symbol, List<FunctionSymbol>> resolveTarget() {
        String target = NameResolver.resolveName(((ImportDeclarationContext) getOwner()).qualifiedName());
        EnvironmentService env = getUnit().environment();

        List<Symbol> symbols = MemberUtils.findImportedElement(env, target);

        if (symbols.size() == 1 && symbols.get(0) instanceof FunctionSymbol) {
            return Tuple.of(symbols.get(0), Collections.emptyList());
        } else {
            return Tuple.of(null, symbols
                .stream()
                .filter(it -> it instanceof FunctionSymbol)
                .map(FunctionSymbol.class::cast)
                .collect(Collectors.toList()));
        }

    }

    public Symbol getSimpleTarget() {
        Tuple<Symbol, List<FunctionSymbol>> target = resolveTarget();
        if (target.second.size() == 1) {
            return target.second.get(0);
        }
        return resolveTarget().first;
    }


    public List<FunctionSymbol> getFunctionTargets() {
        return resolveTarget().second;
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.IMPORT;
    }

    @Override
    public List<Symbol> getMembers() {
        Symbol symbol = getSimpleTarget();
        if (symbol != null)
            return symbol.getMembers();
        return Collections.emptyList();
    }

}
