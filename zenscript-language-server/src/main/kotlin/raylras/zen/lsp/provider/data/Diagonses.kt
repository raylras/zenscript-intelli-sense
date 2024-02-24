package raylras.zen.lsp.provider.data;

import org.eclipse.lsp4j.Diagnostic
import org.eclipse.lsp4j.DiagnosticSeverity
import org.eclipse.lsp4j.PublishDiagnosticsParams
import raylras.zen.lsp.StandardIOLauncher
import raylras.zen.model.diagnose.DiagnoseEntity
import raylras.zen.model.diagnose.DiagnoseHandler
import raylras.zen.model.diagnose.DiagnoseLevel
import raylras.zen.util.toLspRange


fun DiagnoseEntity.toLSPDiagnostic(): Diagnostic {

    val level = when(this.level) {
        DiagnoseLevel.Error -> DiagnosticSeverity.Error
        DiagnoseLevel.Warning ->  DiagnosticSeverity.Warning
        DiagnoseLevel.Hint ->  DiagnosticSeverity.Hint
    }

    return Diagnostic(this.range.toLspRange(), this.msg, level, "zenscript")
}

fun DiagnoseHandler.publish() {
    val params = PublishDiagnosticsParams()
    params.uri = this.unit.path.toUri().toString()
    for (entity in this.entities) {
        params.diagnostics.add(entity.toLSPDiagnostic())
    }
    StandardIOLauncher.CLIENT?.publishDiagnostics(params)
}