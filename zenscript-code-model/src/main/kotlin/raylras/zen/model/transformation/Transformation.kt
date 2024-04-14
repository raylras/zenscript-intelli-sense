package raylras.zen.model.transformation

import com.strumenta.kolasu.mapping.ParseTreeToASTTransformer
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.transformation.NodeFactory
import org.antlr.v4.runtime.tree.ParseTree

/**
 * Replacement for the `registerNodeFactory` function.
 *
 * @param I the type of the input to be transformed.
 *
 * @see com.strumenta.kolasu.transformation.ASTTransformer.registerNodeFactory
 */
inline fun <reified I : ParseTree> ParseTreeToASTTransformer.nodeFor(
    noinline rule: I.() -> Node?
) {
    factories[I::class] = NodeFactory.single({ input, _, _ -> rule(input) })
}

/**
 * Replacement for the `registerMultipleNodeFactory` function.
 *
 * @param I the type of the input to be transformed.
 *
 * @see com.strumenta.kolasu.transformation.ASTTransformer.registerMultipleNodeFactory
 */
inline fun <reified I : ParseTree> ParseTreeToASTTransformer.multipleNodeFor(
    noinline rule: I.() -> List<Node>
) {
    factories[I::class] = NodeFactory({ input, _, _ -> rule(input) })
}
