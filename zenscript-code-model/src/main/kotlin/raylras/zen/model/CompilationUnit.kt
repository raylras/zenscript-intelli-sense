package raylras.zen.model

import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import raylras.zen.model.parser.ZenScriptLexer
import raylras.zen.model.scope.Scope
import raylras.zen.model.symbol.*
import java.nio.file.Path
import java.util.*
import java.util.Collections.*
import kotlin.io.path.nameWithoutExtension

const val ZS_FILE_EXTENSION = ".zs"
const val DZS_FILE_EXTENSION = ".dzs"

class CompilationUnit(val path: Path, val env: CompilationEnvironment) {
    val qualifiedName: String = extractClassName(env.relativize(path))
    val simpleName: String = path.nameWithoutExtension

    val scopeMap = IdentityHashMap<ParseTree, Scope>()
    val symbolMap = IdentityHashMap<ParseTree, Symbol>()

    lateinit var tokenStream: CommonTokenStream
    lateinit var parseTree: ParseTree

    val globals: Sequence<Symbol>
        get() = globalMap.values.asSequence().flatten()

    val classes: Sequence<ClassSymbol>
        get() = classMap.values.asSequence().flatten()

    val expandFunctions: Sequence<ExpandFunctionSymbol>
        get() = expandFunctionMap.values.asSequence().flatten()

    val staticSymbols: Sequence<Symbol>
        get() = staticSymbolMap.values.asSequence().flatten()

    val toplevelSymbols: Sequence<Symbol>
        get() = scopeMap[parseTree]?.getSymbols().orEmpty()

    var importMap: Map<String, List<ImportSymbol>> = emptyMap()
        get() {
            if (field === EMPTY_MAP) {
                field = toplevelSymbols
                    .filterIsInstance<ImportSymbol>()
                    .groupBy { it.simpleName }
            }
            return field
        }
        private set

    var globalMap: Map<String, List<Symbol>> = emptyMap()
        get() {
            if (field === EMPTY_MAP) {
                field = toplevelSymbols
                    .filter { it is Modifiable && it.isGlobal }
                    .groupBy { it.simpleName }
            }
            return field
        }
        private set

    var classMap: Map<String, List<ClassSymbol>> = emptyMap()
        get() {
            if (field === EMPTY_MAP) {
                field = toplevelSymbols
                    .filterIsInstance<ClassSymbol>()
                    .groupBy { it.qualifiedName }
            }
            return field
        }
        private set

    var expandFunctionMap: Map<String, List<ExpandFunctionSymbol>> = emptyMap()
        get() {
            if (field === EMPTY_LIST) {
                field = toplevelSymbols
                    .filterIsInstance<ExpandFunctionSymbol>()
                    .groupBy { it.simpleName }
            }
            return field
        }
        private set

    var staticSymbolMap: Map<String, List<Symbol>> = emptyMap()
        get() {
            if (field === EMPTY_MAP) {
                field = toplevelSymbols
                    .filter { it.isStatic || it is ClassSymbol }
                    .groupBy { it.simpleName }
            }
            return field
        }
        private set

    fun lookupSymbols(qualifiedName: String): Sequence<Symbol> {
        when {
            qualifiedName == this.qualifiedName -> {
                return when {
                    this.isZsUnit -> staticSymbols
                    this.isDzsUnit -> classMap[qualifiedName]?.asSequence() ?: staticSymbols
                    else -> emptySequence()
                }
            }

            qualifiedName.startsWith(this.qualifiedName) -> {
                val memberName = qualifiedName.substringAfterLast('.')
                return when {
                    this.isZsUnit -> {
                        staticSymbols.filter { it.simpleName == memberName }
                    }

                    this.isDzsUnit -> {
                        classMap[this.qualifiedName]?.firstOrNull()?.getSymbols()
                            ?.filter { it.simpleName == memberName }
                            ?: staticSymbols.filter { it.simpleName == memberName }
                    }

                    else -> {
                        throw IllegalStateException("Unknown CompilationUnit $path")
                    }
                }
            }

            else -> {
                return emptySequence()
            }
        }
    }

    val preprocessors: Sequence<Preprocessor>
        get() {
            return tokenStream.let { stream ->
                stream.getTokens(0, stream.size() - 1, ZenScriptLexer.PREPROCESSOR)
                    .asSequence()
                    .map { Preprocessor(it.toString()) }
                    .filter { it.name in env.availablePreprocessors }
            }
        }

    fun accept(visitor: Visitor<*>) {
        parseTree.accept(visitor)
    }

    fun accept(listener: Listener) {
        ParseTreeWalker.DEFAULT.walk(listener, parseTree)
    }

    fun clear() {
        scopeMap.clear()
        symbolMap.clear()
        importMap = emptyMap()
        globalMap = emptyMap()
        classMap = emptyMap()
        expandFunctionMap = emptyMap()
        staticSymbolMap = emptyMap()
    }

    override fun toString(): String = path.toString()
}
