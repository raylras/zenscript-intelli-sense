package raylras.intellizen.ast

import com.strumenta.kolasu.model.EntityDeclaration
import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class ImportDeclaration(
    val identifier: Identifier,
    val simpleIdentifier: Identifier,
    val alias: Identifier? = null,
) : Node(), EntityDeclaration, Named {
    override val name: String
        get() = (alias ?: simpleIdentifier).text
}
