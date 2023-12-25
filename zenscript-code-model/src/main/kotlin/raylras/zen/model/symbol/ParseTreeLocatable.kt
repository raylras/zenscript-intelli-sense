package raylras.zen.model.symbol

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationUnit
import java.nio.file.Path

interface ParseTreeLocatable : Locatable {
    val cst: ParseTree

    val unit: CompilationUnit

    override val path: Path
        get() = unit.path
}
