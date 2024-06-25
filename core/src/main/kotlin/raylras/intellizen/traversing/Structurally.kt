package raylras.intellizen.traversing

import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement
import com.strumenta.kolasu.model.previousSibling
import com.strumenta.kolasu.traversing.walkAncestors

internal inline fun <reified T> Node.findAncestorOfType(): T? {
    return walkAncestors().filterIsInstance<T>().firstOrNull()
}

internal fun Statement.previousStatements(): Sequence<Statement> {
    return (this as Node).previousNodes().filterIsInstance<Statement>()
}

internal fun Node.previousNodes(): Sequence<Node> {
    return generateSequence(this) { it.previousSibling }
}
