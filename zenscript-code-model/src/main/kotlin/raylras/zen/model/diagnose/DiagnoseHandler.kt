package raylras.zen.model.diagnose

import raylras.zen.model.CompilationUnit
import raylras.zen.util.TextRange

class DiagnoseHandler(
    val unit: CompilationUnit
) {
    val entities = mutableListOf<DiagnoseEntity>()
    fun addEntity(entity: DiagnoseEntity) {
        entities.add(entity)
    }

    fun addError(msg: String, range: TextRange) {
        addEntity(DiagnoseEntity(DiagnoseLevel.Error, msg, range))
    }

    fun addSystemError(msg: String) {

    }

    fun clear() {
        entities.clear()
    }
}