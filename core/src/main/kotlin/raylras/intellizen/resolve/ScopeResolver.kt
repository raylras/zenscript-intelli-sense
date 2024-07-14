package raylras.intellizen.resolve

import org.antlr.v4.runtime.tree.ParseTree
import raylras.intellizen.CompilationUnit
import raylras.intellizen.scope.Scope

fun lookupScope(cst: ParseTree?, unit: CompilationUnit): Scope? {
    return generateSequence(cst) { it.parent }
            .mapNotNull { unit.scopeMap[it] }
            .firstOrNull()
}
