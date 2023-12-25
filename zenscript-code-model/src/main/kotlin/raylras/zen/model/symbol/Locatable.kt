package raylras.zen.model.symbol

import raylras.zen.util.TextRange
import java.nio.file.Path

interface Locatable {
    val path: Path

    val range: TextRange

    val selectionRange: TextRange
}
