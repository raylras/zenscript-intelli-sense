package raylras.intellizen.languageserver.provider

import org.antlr.v4.runtime.tree.RuleNode
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.HoverParams
import org.eclipse.lsp4j.MarkupContent
import org.eclipse.lsp4j.MarkupKind
import raylras.intellizen.CompilationUnit
import raylras.intellizen.Visitor
import raylras.intellizen.brackets.BracketHandlers
import raylras.intellizen.languageserver.util.toLspRange
import raylras.intellizen.languageserver.util.toTextPosition
import raylras.intellizen.parser.ZenScriptParser.BracketHandlerExprContext
import raylras.intellizen.util.getCstStackAt
import raylras.intellizen.util.textRange
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object HoverProvider {
    fun hover(unit: CompilationUnit, params: HoverParams): Hover? {
        val cursor = params.position.toTextPosition()
        val cstStack = unit.parseTree.getCstStackAt(cursor)
        val visitor = HoverVisitor(unit)
        for (cst in cstStack) {
            cst.accept(visitor)?.let {
                return it
            }
        }
        return null
    }
}

private class HoverVisitor(private val unit: CompilationUnit) : Visitor<Hover?>() {
    override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext): Hover? {
        val expr = ctx.raw().text
        return BracketHandlers.getIconRemote(expr).fold(
            onSuccess = {
                it?.let { "![img](data:image/png;base64,${resize(it, 128)})" }
            },
            onFailure = {
                it.message
            }
        )?.toHover()?.apply {
            range = ctx.textRange.toLspRange()
        }
    }

    override fun visitChildren(node: RuleNode): Hover? {
        return null
    }
}

private fun String.toHover(): Hover = Hover(MarkupContent(MarkupKind.MARKDOWN, this))

private fun String.toCodeBlock() = """
    ```zenscript
    $this
    ```
""".trimIndent()

@OptIn(ExperimentalEncodingApi::class)
private fun resize(image64: String, size: Int): String {
    val input: BufferedImage = Base64.decode(image64).let {
        ImageIO.read(it.inputStream())
    }
    val output: BufferedImage = BufferedImage(size, size, input.type).apply {
        createGraphics().drawImage(input, 0, 0, size, size, null)
    }
    val outputStream = ByteArrayOutputStream().apply {
        ImageIO.write(output, "png", this)
    }
    return Base64.encode(outputStream.toByteArray())
}
