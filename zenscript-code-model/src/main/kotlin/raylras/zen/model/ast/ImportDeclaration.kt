package raylras.zen.model.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ImportDeclaration(
    val qualifiedName: String,
    val alias: String? = null
) : EntityDeclaration, Named, Node() {
    val simpleName: String = alias ?: qualifiedName.substringAfterLast(".")
    override val name: String
        get() = simpleName
}
