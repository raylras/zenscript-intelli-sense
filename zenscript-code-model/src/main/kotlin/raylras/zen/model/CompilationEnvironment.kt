package raylras.zen.model

import com.google.gson.Gson
import raylras.zen.model.symbol.*
import raylras.zen.model.symbol.impl.createPackageSymbol
import raylras.zen.util.toHash
import java.nio.file.FileSystems
import java.nio.file.Path
import kotlin.io.path.reader
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

const val DEFAULT_ROOT_DIRECTORY = "scripts"
const val DEFAULT_GENERATED_DIRECTORY = "generated"

class CompilationEnvironment(val root: Path) {
    val unitMap = HashMap<Path, CompilationUnit>()
    val availablePreprocessors: Set<String> by lazy { loadAvailablePreprocessors() }

    val units: Sequence<CompilationUnit>
        get() = unitMap.values.asSequence()

    val globals: Sequence<Symbol>
        get() = units.flatMap { it.symbols }.filter { it is Modifiable && it.isGlobal }

    val classes: Sequence<ClassSymbol>
        get() = units.flatMap { it.topLevelScope.symbols }.filterIsInstance<ClassSymbol>()

    val expandFunctions: Sequence<ExpandFunctionSymbol>
        get() = units.flatMap { it.topLevelScope.symbols }.filterIsInstance<ExpandFunctionSymbol>()

    val rootPackage: PackageSymbol
        get() = createPackageSymbol(this)

    val generatedRoot: Path by lazy {
        FileSystems.getDefault()
            .getPath(System.getProperty("user.home"))
            .resolve(".probezs")
            .resolve(toHash(root))
            .resolve(DEFAULT_GENERATED_DIRECTORY)
    }

    fun relativize(other: Path): Path {
        return when {
            other.startsWith(generatedRoot) -> {
                generatedRoot.relativize(other)
            }
            else -> {
                root.parent.relativize(other)
            }
        }
    }

    fun clear(): Unit = unitMap.clear()

    override fun toString() = root.toString()

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadAvailablePreprocessors(): Set<String> {
        return try {
            generatedRoot.resolve("preprocessors.json").reader().use {
                Gson().fromJson(it, typeOf<HashSet<String>>().javaType)
            }
        } catch (e: Exception) {
            emptySet()
        }
    }
}
