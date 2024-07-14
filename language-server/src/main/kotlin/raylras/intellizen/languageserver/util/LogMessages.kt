package raylras.intellizen.languageserver.util

import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.MessageType
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.services.LanguageClient
import org.slf4j.Logger
import kotlin.reflect.KFunction
import kotlin.time.Duration

fun Logger.start(func: KFunction<*>, uri: String? = null, pos: Position? = null) {
    info(buildMessage(func.name, uri, pos, "started"))
}

fun Logger.finish(func: KFunction<*>, uri: String? = null, pos: Position? = null, duration: Duration) {
    info(buildMessage(func.name, uri, pos, "finished ${duration.inWholeMilliseconds} ms"))
}

fun Logger.cancel(func: KFunction<*>, uri: String? = null, pos: Position? = null) {
    info(buildMessage(func.name, uri, pos, "cancelled"))
}

fun Logger.fail(func: KFunction<*>, uri: String? = null, pos: Position? = null, throwable: Throwable?) {
    error(buildMessage(func.name, uri, pos, "failed"), throwable)
}

fun LanguageClient?.info(message: String) {
    this?.showMessage(MessageParams(MessageType.Info, message))
}

private fun buildMessage(head: String, uri: String?, pos: Position?, tail: String): String {
    return buildString {
        append(head)
        uri?.let { append(" ${it.substringAfterLast('/')}") }
        pos?.let { append(" ${it.line + 1}:${it.character}") }
        append(" $tail")
    }
}
