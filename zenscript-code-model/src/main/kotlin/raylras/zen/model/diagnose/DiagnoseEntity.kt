package raylras.zen.model.diagnose

import raylras.zen.util.TextRange


enum class DiagnoseLevel {
    Error,
    Warning,
    Hint
}
data class DiagnoseEntity(
    val level: DiagnoseLevel,
    val msg: String,
    val range: TextRange,

) {


}

