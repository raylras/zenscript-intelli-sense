package raylras.zen.lsp.bracket

import com.google.gson.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import raylras.zen.lsp.bracket.RpcClient.getEntryPropertiesRemote
import raylras.zen.model.CompilationEnvironment
import raylras.zen.util.l10n.L10N.localize
import java.io.IOException
import java.lang.reflect.Type
import java.net.ConnectException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.io.path.exists
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

class BracketHandlerService private constructor(env: CompilationEnvironment) {
    private var mirrorsLocal = emptyList<BracketHandlerMirror>()

    val entriesLocal: Sequence<BracketHandlerEntry>
        get() = mirrorsLocal.asSequence().flatMap { it.entries }

    fun getEntryRemote(expr: String): BracketHandlerEntry {
        try {
            return BracketHandlerEntry(getEntryPropertiesRemote(expr))
        } catch (e: ConnectException) {
            logger.warn("Failed to query <{}>: {}", expr, e.message)
            val message = localize("minecraft_instance_not_running", expr)
            return BracketHandlerEntry(mapOf("_errorMessage" to message))
        } catch (e: Exception) {
            logger.error("Failed to query <{}>: {}", expr, e.message)
            val message = localize("query_bracket_handler_failed", expr, e.message)
            return BracketHandlerEntry(mapOf("_errorMessage" to message))
        }
    }

    init {
        val path = env.generatedRoot.resolve("brackets.json")
        if (path.exists()) {
            loadMirrorsFromJson(path)
        } else {
            logger.error("brackets.json not found for env: {}", env)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadMirrorsFromJson(jsonPath: Path) {
        try {
            Files.newBufferedReader(jsonPath).use { reader ->
                mirrorsLocal = GSON.fromJson(reader, typeOf<List<BracketHandlerMirror>>().javaType)
            }
        } catch (e: IOException) {
            logger.error("Failed to load bracket handler mirrors from {}", jsonPath, e)
            mirrorsLocal = listOf()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BracketHandlerService::class.java)

        fun getInstance(env: CompilationEnvironment): BracketHandlerService {
            return BRACKET_SERVICES.computeIfAbsent(env) { BracketHandlerService(env) }
        }

        private val BRACKET_SERVICES = WeakHashMap<CompilationEnvironment, BracketHandlerService>(1)
        private val GSON = createGson()

        @OptIn(ExperimentalStdlibApi::class)
        private fun createGson(): Gson {
            val mirrorDeserializer =
                JsonDeserializer { json: JsonElement, _: Type?, context: JsonDeserializationContext ->
                    val jsonObject = json.asJsonObject
                    val type = jsonObject["type"].asString
                    val regex = jsonObject["regex"].asString
                    val entries = context.deserialize<List<BracketHandlerEntry>>(
                        jsonObject["entries"],
                        typeOf<List<BracketHandlerEntry>>().javaType
                    )
                    BracketHandlerMirror(type, regex, entries)
                }
            val entryDeserializer =
                JsonDeserializer { json: JsonElement, _: Type?, context: JsonDeserializationContext ->
                    val properties: MutableMap<String, Any> = HashMap()
                    json.asJsonObject.asMap().forEach { (key: String, value: JsonElement) ->
                        when (value) {
                            is JsonArray -> {
                                properties[key] = context.deserialize(value, typeOf<List<String>>().javaType)
                            }

                            is JsonPrimitive -> {
                                properties[key] = value.asString
                            }

                            else -> {
                                logger.warn("Unexpected type of value: {}", value)
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
    }
}
