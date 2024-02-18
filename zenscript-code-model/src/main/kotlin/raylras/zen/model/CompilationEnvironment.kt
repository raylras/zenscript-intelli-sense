package raylras.zen.model

import com.google.gson.Gson
import raylras.zen.model.symbol.ClassSymbol
import raylras.zen.model.symbol.ExpandFunctionSymbol
import raylras.zen.model.symbol.PackageSymbol
import raylras.zen.model.symbol.Symbol
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
        get() = units.flatMap { it.globals }

    val classes: Sequence<ClassSymbol>
        get() = units.flatMap { it.classes }

    val expandFunctions: Sequence<ExpandFunctionSymbol>
        get() = units.flatMap { it.expandFunctions }

    fun lookupGlobal(simpleName: String): Symbol? {
        return lookupGlobals(simpleName).firstOrNull()
    }

    fun lookupGlobals(simpleName: String): Sequence<Symbol> {
        return units.mapNotNull { it.globalMap[simpleName] }.flatten()
    }

    fun lookupClass(qualifiedName: String): ClassSymbol? {
        return lookupClasses(qualifiedName).firstOrNull()
    }

    fun lookupClasses(qualifiedName: String): Sequence<ClassSymbol> {
        return units.mapNotNull { it.classMap[qualifiedName] }.flatten()
    }

    fun lookupSymbol(qualifiedName: String): Symbol? {
        return lookupSymbols(qualifiedName).firstOrNull()
    }

    fun lookupSymbols(qualifiedName: String): Sequence<Symbol> {
        return units.flatMap { it.lookupSymbols(qualifiedName) }
    }

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
