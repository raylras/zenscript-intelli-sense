package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationUnit
import raylras.zen.model.scope.Scope

fun lookupScope(cst: ParseTree?, unit: CompilationUnit): Scope? {
    var current = cst
    while (current != null) {
        unit.scopeMap[current]?.let { return it }
        current = current.parent
    }
    return null
}
