package raylras.zen.model.ast

import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node

data class Name(
    override val name: String
) : Node(), Named

fun Name.isSimpleName() = name.none { it == '.' }

fun Name.isQualifiedName() = name.any { it == '.' }
