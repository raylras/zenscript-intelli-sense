package raylras.zen.util;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.PackageSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResolveUtils {

    public static List<Symbol> lookupSymbols(CompilationUnit unit, ParseTree cst, String simpleName) {
        Scope scope = unit.lookupScope(cst);
        List<Symbol> symbols = new ArrayList<>();
        if (scope != null) {
            symbols = scope.lookupSymbols(simpleName);
        }
        if (symbols.isEmpty()) {
            for (Symbol globalSymbol : unit.getEnv().getGlobalSymbols()) {
                if (simpleName.equals(globalSymbol.getName())) {
                    // assume global symbols can not overload, so return directly
                    return Collections.singletonList(globalSymbol);
                }
            }
        }
        return symbols;
    }
    public static Symbol lookupSymbol(CompilationUnit unit, ParseTree cst, String simpleName) {
        Scope scope = unit.lookupScope(cst);
        Symbol symbol = null;
        if (scope != null) {
            symbol = scope.lookupSymbol(simpleName);
        }
        if (symbol != null) {
            for (Symbol globalSymbol : unit.getEnv().getGlobalSymbols()) {
                if (simpleName.equals(globalSymbol.getName())) {
                    return globalSymbol;
                }
            }
        }
        return symbol;
    }
    public static PackageSymbol getRootPackage(CompilationUnit unit, String packageName) {

        // TODO:xxx
        return null;
    }


}
