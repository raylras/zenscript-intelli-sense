package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationUnit
import raylras.zen.model.scope.Scope

fun lookupScope(cst: ParseTree?, unit: CompilationUnit): Scope? {
    return generateSequence(cst) { it.parent }
            .mapNotNull { unit.scopeMap[it] }
            .firstOrNull()
}
