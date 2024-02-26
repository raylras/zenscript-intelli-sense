package raylras.zen.model.ast

import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class CompilationUnit(
    val statements: List<Statement> = emptyList(),
    val functions: List<FunctionDeclaration> = emptyList(),
    val expandFunctions: List<ExpandFunctionDeclaration> = emptyList(),
    val classes: List<ClassDeclaration> = emptyList(),
) : Node()
