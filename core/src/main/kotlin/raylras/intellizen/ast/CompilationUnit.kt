package raylras.intellizen.ast

import com.strumenta.kolasu.model.Derived
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class CompilationUnit(
    val toplevelEntities: List<Node>
) : Node() {
    @Derived
    val toplevelImports: List<ImportDeclaration>
        get() = toplevelEntities.filterIsInstance<ImportDeclaration>()

    @Derived
    val toplevelFunctions: List<FunctionDeclaration>
        get() = toplevelEntities.filterIsInstance<FunctionDeclaration>()

    @Derived
    val toplevelExpandFunctions: List<ExpandFunctionDeclaration>
        get() = toplevelEntities.filterIsInstance<ExpandFunctionDeclaration>()

    @Derived
    val toplevelClasses: List<ClassDeclaration>
        get() = toplevelEntities.filterIsInstance<ClassDeclaration>()

    @Derived
    val toplevelVariables: List<VariableDeclaration>
        get() = toplevelEntities.filterIsInstance<VariableDeclaration>()

    @Derived
    val toplevelStatements: List<Statement>
        get() = toplevelEntities.filterIsInstance<Statement>()
}
