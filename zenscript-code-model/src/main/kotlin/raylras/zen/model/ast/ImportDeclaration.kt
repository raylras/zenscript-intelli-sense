package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ImportDeclaration(
    val qualifiedName: Name,
    val alias: Name? = null,
    val simpleName: Name
) : Node(), EntityDeclaration, Named by simpleName
