package raylras.intellizen.symbol

import raylras.intellizen.util.TextRange
import java.nio.file.Path

interface Locatable {
    val path: Path

    val textRange: TextRange

    val simpleNameTextRange: TextRange
}
