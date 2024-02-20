package raylras.zen.lsp.provider

import org.eclipse.lsp4j.DocumentFormattingParams
import org.eclipse.lsp4j.TextEdit
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.parser.ZenScriptParser.ImportDeclarationContext
import raylras.zen.model.parser.ZenScriptParser.QualifiedNameContext
import raylras.zen.util.textRange
import raylras.zen.util.toLspRange

object FormattingProvider {
    fun formatting(unit: CompilationUnit, params: DocumentFormattingParams): List<TextEdit> {
        val listener = FormattingListener(unit)
        unit.accept(listener)
        return listener.getResult()
    }
}

private class FormattingListener(private val unit: CompilationUnit): Listener() {
    private val builder = StringBuilder()

    fun getResult(): List<TextEdit> {
        return listOf(TextEdit(unit.parseTree.textRange.toLspRange(), builder.toString()))
    }

    override fun enterImportDeclaration(ctx: ImportDeclarationContext) {
        builder.append("import ")
    }

    override fun enterQualifiedName(ctx: QualifiedNameContext) {
        builder.append(ctx.simpleName().joinToString(".") { it.text })
    }

    override fun exitImportDeclaration(ctx: ImportDeclarationContext) {
        builder.append(";\n")
    }
}
