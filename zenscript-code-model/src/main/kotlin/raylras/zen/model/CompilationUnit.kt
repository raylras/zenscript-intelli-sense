package raylras.zen.model

import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import raylras.zen.model.parser.ZenScriptLexer
import raylras.zen.model.scope.Scope
import raylras.zen.model.symbol.ImportSymbol
import raylras.zen.model.symbol.Symbol
import java.nio.file.Path
import java.util.*
import kotlin.io.path.nameWithoutExtension

const val ZS_FILE_EXTENSION: String = ".zs"
const val DZS_FILE_EXTENSION: String = ".dzs"

class CompilationUnit(val path: Path, val env: CompilationEnvironment) {
    val qualifiedName: String = extractClassName(env.relativize(path))
    val simpleName: String = path.nameWithoutExtension

    val imports = ArrayList<ImportSymbol>()
    val scopeMap = IdentityHashMap<ParseTree, Scope>()
    val symbolMap = IdentityHashMap<ParseTree, Symbol>()

    var tokenStream: CommonTokenStream? = null
    var parseTree: ParseTree? = null

    val symbols: Sequence<Symbol>
        get() = symbolMap.values.asSequence()

    val topLevelSymbols: Sequence<Symbol>
        get() = scopeMap[parseTree]?.getSymbols() ?: emptySequence()

    val preprocessors: Sequence<Preprocessor>
        get() {
            return tokenStream?.let { stream ->
                stream.getTokens(0, stream.size() - 1, ZenScriptLexer.PREPROCESSOR)
                    .asSequence()
                    .map { Preprocessor(it.toString()) }
                    .filter { it.name in env.availablePreprocessors }
            } ?: emptySequence()
        }

    fun accept(visitor: Visitor<*>) {
        parseTree?.accept(visitor)
    }

    fun accept(listener: Listener) {
        parseTree?.let { ParseTreeWalker.DEFAULT.walk(listener, it) }
    }

    fun clear() {
        imports.clear()
        scopeMap.clear()
        symbolMap.clear()
        tokenStream = null
        parseTree = null
    }

    override fun toString(): String = path.toString()
}
