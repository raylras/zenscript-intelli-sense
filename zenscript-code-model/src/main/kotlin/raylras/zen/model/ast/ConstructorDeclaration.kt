package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement

data class ConstructorDeclaration(
    val parameters: List<ParameterDeclaration>,
    val body: List<Statement>,
) : EntityDeclaration, Node()
