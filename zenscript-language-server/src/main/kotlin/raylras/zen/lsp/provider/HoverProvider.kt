package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.RuleNode
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.HoverParams
import org.eclipse.lsp4j.MarkupContent
import org.eclipse.lsp4j.MarkupKind
import raylras.zen.lsp.bracket.BracketHandlerService
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.BracketHandlerExprContext
import raylras.zen.util.getCstStackAt
import raylras.zen.util.textRange
import raylras.zen.util.toLspRange
import raylras.zen.util.toTextPosition
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

    private class HoverVisitor(private val unit: CompilationUnit) : Visitor<Hover?>() {
        override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext): Hover {
            val service = BracketHandlerService.getInstance(unit.env)
            val entry = service.getEntryRemote(ctx.raw().text)
            val hover = buildString {
                entry.getStringOrNull("_errorMessage")?.let {
                    append(it)
                    append("  \n")
                }
                entry.getStringOrNull("_icon")?.let {
                    append("![img](data:image/png;base64,${resize(it, 64)})")
                    append("  \n")
                }
                entry.getStringOrNull("_name")?.let {
                    append(it)
                    append("  \n")
                }
            }.let {
                toHover(it)
            }.apply {
                range = ctx.textRange.toLspRange()
            }
            return hover
        }

        override fun visitChildren(node: RuleNode): Hover? {
            return null
        }

        private fun toHover(text: String): Hover {
            return Hover(MarkupContent(MarkupKind.MARKDOWN, text))
        }

        private fun toCodeBlock(text: String): String {
            return String.format("```zenscript\n%s\n```\n", text)
        }

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
    }
}
