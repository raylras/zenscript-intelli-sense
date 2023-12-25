package raylras.zen.util

import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.MessageType
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.services.LanguageClient
import org.slf4j.Logger
import java.nio.file.Path
import kotlin.time.Duration

class LogMessage(
    private val label: String,
    private var path: Path? = null,
    private var position: Position? = null,
    val logger: Logger
) {
    fun start() {
        buildString {
            append(label)
            path?.let { append(" ${it.fileName}") }
            position?.let { append(" ${it.line + 1}:${it.character}") }
            append(" started")
        }.let { logger.info(it) }
    }

    fun finish(duration: Duration) {
        buildString {
            append(label)
            path?.let { append(" ${it.fileName}") }
            position?.let { append(" ${it.line + 1}:${it.character}") }
            append(" finished ${duration.inWholeMilliseconds} ms")
        }.let { logger.info(it) }
    }
}

fun LanguageClient?.info(message: String) {
    this?.showMessage(MessageParams(MessageType.Info, message))
}
