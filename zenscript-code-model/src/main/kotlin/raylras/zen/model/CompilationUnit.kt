package raylras.zen.model

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import raylras.zen.model.parser.ZenScriptLexer
import raylras.zen.model.resolve.resolveDeclarations
import raylras.zen.model.scope.Scope
import raylras.zen.model.symbol.*
import java.nio.file.Path
import java.util.*
import kotlin.io.path.nameWithoutExtension

const val ZS_FILE_EXTENSION = ".zs"
const val DZS_FILE_EXTENSION = ".dzs"

class CompilationUnit(val path: Path, val env: CompilationEnvironment) {
    val qualifiedName: String = extractClassName(env.relativize(path))
    val simpleName: String = path.nameWithoutExtension

    val imports = ArrayList<ImportSymbol>()
    val scopeMap = IdentityHashMap<ParseTree, Scope>()
    val symbolMap = IdentityHashMap<ParseTree, Symbol>()

    val tokenStream: CommonTokenStream
        get() = _tokenStream!!

    private var _tokenStream: CommonTokenStream? = null
        get() {
            if (field == null) {
                this.load()
            }
            return field
        }

    val parseTree: ParseTree
        get() = _parseTree!!

    private var _parseTree: ParseTree? = null
        get() {
            if (field == null) {
                this.load()
            }
            return field
        }

    val symbols: Sequence<Symbol>
        get() = symbolMap.values.asSequence()

    fun lookupSymbols(qualifiedName: String): Sequence<Symbol> {
        when {
            qualifiedName == this.qualifiedName -> {
                return when {
                    this.isZsUnit -> topLevelStaticSymbols
                    this.isDzsUnit -> topLevelScope.getSymbols()
                        .filterIsInstance<ClassSymbol>()
                        .filter { it.qualifiedName == qualifiedName }
                        .ifEmpty { topLevelStaticSymbols }

                    else -> emptySequence()
                }
            }

            qualifiedName.startsWith(this.qualifiedName) -> {
                val memberName = qualifiedName.substringAfterLast('.')
                return topLevelStaticSymbols.filter { it.simpleName == memberName }
            }

            else -> {
                return emptySequence()
            }
        }
    }

    val globals: Sequence<Symbol>
        get() = _globals!!.values.asSequence()

    fun lookupGlobal(simpleName: String): Symbol? {
        return _globals!![simpleName]
    }

    private var _globals: Map<String, Symbol>? = null
        get() {
            if (field == null) {
                field = topLevelScope.symbols
                    .filter { it is Modifiable && it.isGlobal }
                    .associateBy { it.simpleName }
            }
            return field
        }

    val classes: Sequence<ClassSymbol>
        get() = _classes!!.values.asSequence()

    fun lookupClass(qualifiedName: String): ClassSymbol? {
        return _classes!![qualifiedName]
    }

    private var _classes: Map<String, ClassSymbol>? = null
        get() {
            if (field == null) {
                field = topLevelScope.symbols
                    .filterIsInstance<ClassSymbol>()
                    .associateBy { it.qualifiedName }
            }
            return field
        }

    val expandFunctions: Sequence<ExpandFunctionSymbol>
        get() = _expandFunctions!!.asSequence()

    private var _expandFunctions: List<ExpandFunctionSymbol>? = null
        get() {
            if (field == null) {
                field = topLevelScope.symbols
                    .filterIsInstance<ExpandFunctionSymbol>()
                    .toList()
            }
            return field
        }

    val topLevelScope: Scope
        get() = scopeMap[parseTree]!!

    val topLevelStaticSymbols: Sequence<Symbol>
        get() = when {
            this.isZsUnit -> {
                topLevelScope
                    .getSymbols().filter { it.isStatic }
            }

            this.isDzsUnit -> {
                (env.classes.firstOrNull { it.qualifiedName == this.qualifiedName } ?: topLevelScope)
                    .getSymbols().filter { it.isStatic }
            }

            else -> {
                emptySequence()
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
        parseTree.let { ParseTreeWalker.DEFAULT.walk(listener, it) }
    }

    fun load(charStream: CharStream) {
        this.clear()
        val tokenStream = lex(charStream)
        val parseTree = parse(tokenStream)
        this._tokenStream = tokenStream
        this._parseTree = parseTree
        this.resolveDeclarations()
    }

    private fun clear() {
        imports.clear()
        scopeMap.clear()
        symbolMap.clear()
        _tokenStream = null
        _parseTree = null
        _classes = null
        _globals = null
        _expandFunctions = null
    }

    override fun toString(): String = path.toString()
}
