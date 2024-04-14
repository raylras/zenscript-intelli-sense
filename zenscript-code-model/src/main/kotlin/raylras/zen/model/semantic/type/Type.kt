package raylras.zen.model.semantic.type

import com.strumenta.kolasu.model.PossiblyNamed

interface Type {
    val typeName: String

    val denotable: Boolean
        get() = true

    val members: Iterable<PossiblyNamed>
        get() = emptyList()
}
