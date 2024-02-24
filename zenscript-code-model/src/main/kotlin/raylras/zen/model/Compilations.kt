package raylras.zen.model

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.misc.ParseCancellationException
import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.diagnose.ParserErrorListener
import raylras.zen.model.diagnose.PrettyErrorStrategy
import raylras.zen.model.diagnose.resolveSyntaxErrors
import raylras.zen.model.parser.ZenScriptLexer
import raylras.zen.model.parser.ZenScriptParser
import raylras.zen.model.resolve.resolveDeclarations
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.isReadable
import kotlin.io.path.isRegularFile
import kotlin.io.path.walk

fun isSourceFile(path: Path): Boolean {
    return isZsFile(path) || isDzsFile(path)
}

fun isZsFile(path: Path): Boolean {
    return path.toString().endsWith(ZS_FILE_EXTENSION)
}

fun isDzsFile(path: Path): Boolean {
    return path.toString().endsWith(DZS_FILE_EXTENSION)
}

val CompilationUnit.isZsUnit: Boolean
    get() = isZsFile(this.path)

val CompilationUnit.isDzsUnit: Boolean
    get() = isDzsFile(this.path)

/**
 * @see raylras.zen.model.CompilationsKtTest.extractClassName
 */
fun extractClassName(path: Path): String {
    return path.toString()
        .substringBeforeLast('.')
        .replace('.', '_')
        .replace('-', '_')
        .replace(' ', '_')
        .replace(File.separatorChar, '.')
}

fun createUnit(unitPath: Path, env: CompilationEnvironment): CompilationUnit {
    val unit = CompilationUnit(unitPath, env)
    env.unitMap[unitPath] = unit
    return unit
}

fun CompilationEnvironment.load(unitCallback: (CompilationUnit) -> Unit = {}) {
    this.clear()
    this.getUnitPaths().forEach { path ->
        val unit = createUnit(path, this)
        unit.load()
        unitCallback(unit)
    }
}

fun CompilationUnit.load() {
    this.load(CharStreams.fromPath(this.path))
}

fun CompilationUnit.load(source: String) {
    this.load(CharStreams.fromString(source, this.path.toString()))
}

fun CompilationUnit.load(charStream: CharStream) {
    this.clear()
    val tokenStream = lex(charStream)
    val parseTree = parse(tokenStream, ParserErrorListener(this.diagnoseHandler), this.isDzsUnit)
    this.tokenStream = tokenStream
    this.parseTree = parseTree
    this.resolveDeclarations()
    this.resolveSyntaxErrors()
}

fun lex(charStream: CharStream): CommonTokenStream {
    val lexer = ZenScriptLexer(charStream)
    lexer.removeErrorListeners()
    return CommonTokenStream(lexer)
}

fun parse(tokenStream: TokenStream, errorListener: ANTLRErrorListener, isDzs: Boolean): ParseTree {
    val parser = ZenScriptParser(tokenStream)
    parser.removeErrorListeners()
    // faster but less robust strategy, effective when no syntax errors
    parser.interpreter.predictionMode = PredictionMode.SLL
    parser.errorHandler = BailErrorStrategy()
    parser.dzs = isDzs
    try {
        return parser.compilationUnit()
    } catch (_: ParseCancellationException) {
        parser.reset()
        // fall back to default strategy, slower but more robust
        parser.interpreter.predictionMode = PredictionMode.LL
        parser.errorHandler = PrettyErrorStrategy()
        parser.addErrorListener(errorListener)
        return parser.compilationUnit()
    }
}

fun CompilationEnvironment.getUnitPaths(): Sequence<Path> {
    return collectUnitFiles(root) + collectUnitFiles(generatedRoot)
}

@OptIn(ExperimentalPathApi::class)
private fun collectUnitFiles(root: Path?): Sequence<Path> {
    try {
        return root?.walk()
            ?.filter { it.isRegularFile() }
            ?.filter { it.isReadable() }
            ?.filter { isSourceFile(it) }
            ?: emptySequence()
    } catch (e: IOException) {
        throw RuntimeException("Failed to collect unit files of root: $root", e)
    }
}
