package raylras.intellizen.brackets

import com.google.gson.*
import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.i18n.L10N.localize
import youyihj.probezs.api.BracketHandlerService
import java.lang.reflect.Type
import java.nio.file.Path
import java.rmi.Naming
import java.util.*
import kotlin.io.path.bufferedReader
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

object BracketHandlers {
    fun getBracketEntryLocal(expr: String, env: CompilationEnvironment): BracketHandlerEntry? {
        return env.bracketMirrorsLocal
            .filter { it.regex.matches(expr) }
            .map { it.entries[specialize(expr)] }
            .firstOrNull()
    }

    fun getLocalizedNameLocal(expr: String, env: CompilationEnvironment): String? {
        return getBracketEntryLocal(specialize(expr), env)?.getStringOrNull("_name")
    }

    fun getTypeNameLocal(expr: String, env: CompilationEnvironment): String? {
        return env.bracketMirrorsLocal.firstOrNull { it.entries.containsKey(specialize(expr)) }?.type
    }

    fun getIconRemote(expr: String): Result<String?> {
        return runCatchingRemote(expr) {
            bracketService.getIcon(expr)
        }
    }

    fun getLocalizedNameRemote(expr: String): Result<String?> {
        return runCatchingRemote(expr) {
            bracketService.getLocalizedName(expr)
        }
    }

    fun getTypeNameRemote(expr: String): Result<String?> {
        return runCatchingRemote(expr) {
            bracketService.getTypeName(expr)
        }
    }

    private fun <R> runCatchingRemote(expr: String, block: () -> R): Result<R> {
        return runCatching {
            block()
        }.recoverCatching {
            when (it) {
                is java.rmi.ConnectException -> {
                    throw RuntimeException(localize("minecraft_instance_not_running", expr))
                }

                else -> {
                    throw RuntimeException(localize("query_bracket_handler_failed", expr, it.message))
                }
            }
        }
    }
}

val CompilationEnvironment.bracketMirrorsLocal: Sequence<BracketHandlerMirror>
    get() = envToMirrors.computeIfAbsent(this) {
        loadMirrorsFromJson(this.generatedRoot.resolve("brackets.json"))
    }.asSequence()

val CompilationEnvironment.bracketEntriesLocal: Sequence<BracketHandlerEntry>
    get() = bracketMirrorsLocal.flatMap { it.entries.values }

private val bracketService: BracketHandlerService
    get() = Naming.lookup("rmi://localhost:6489/BracketHandler") as BracketHandlerService

private val envToMirrors = WeakHashMap<CompilationEnvironment, List<BracketHandlerMirror>>(1)
private val gson by lazy { createGson() }

@OptIn(ExperimentalStdlibApi::class)
private fun loadMirrorsFromJson(jsonPath: Path): List<BracketHandlerMirror> {
    return jsonPath.bufferedReader().use {
        gson.fromJson(it, typeOf<List<BracketHandlerMirror>>().javaType)
    }
}

@OptIn(ExperimentalStdlibApi::class)
private fun createGson(): Gson {
    val mirrorDeserializer =
        JsonDeserializer { json: JsonElement, _: Type, context: JsonDeserializationContext ->
            val jsonObject = json.asJsonObject
            val type = jsonObject["type"].asString
            val regex = jsonObject["regex"].asString.toRegex()
            val entries = context.deserialize<List<BracketHandlerEntry>>(
                jsonObject["entries"],
                typeOf<List<BracketHandlerEntry>>().javaType
            ).associateBy { it.getStringOrNull("_id") ?: "" }
            BracketHandlerMirror(type, regex, entries)
        }
    val entryDeserializer =
        JsonDeserializer { json: JsonElement, _: Type, context: JsonDeserializationContext ->
            val properties: MutableMap<String, Any> = HashMap()
            json.asJsonObject.asMap().forEach { (key: String, value: JsonElement) ->
                when (value) {
                    is JsonArray -> {
                        properties[key] = context.deserialize(value, typeOf<List<String>>().javaType)
                    }

                    is JsonPrimitive -> {
                        properties[key] = value.asString
                    }
                }
            }
            BracketHandlerEntry(properties)
        }
    return GsonBuilder()
        .registerTypeAdapter(BracketHandlerMirror::class.java, mirrorDeserializer)
        .registerTypeAdapter(BracketHandlerEntry::class.java, entryDeserializer)
        .create()
}

/**
 * @see raylras.intellizen.brackets.BracketHandlersKtTest.specialize
 */
fun specialize(expr: String): String {
    if (expr == "*") {
        return expr
    }

    val str = if (expr.startsWith("item:")) {
        expr.substringAfter("item:")
    } else {
        expr
    }

    return if (str.endsWith(":0")) {
        str.substringBeforeLast(":0")
    } else {
        str
    }
}
