package raylras.intellizen.lsp

import org.eclipse.lsp4j.CompletionItem
import org.eclipse.lsp4j.CompletionList
import org.eclipse.lsp4j.CompletionParams
import org.eclipse.lsp4j.DefinitionParams
import org.eclipse.lsp4j.DidChangeConfigurationParams
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.DidCloseTextDocumentParams
import org.eclipse.lsp4j.DidOpenTextDocumentParams
import org.eclipse.lsp4j.DidSaveTextDocumentParams
import org.eclipse.lsp4j.DocumentSymbol
import org.eclipse.lsp4j.DocumentSymbolParams
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.HoverParams
import org.eclipse.lsp4j.InlayHint
import org.eclipse.lsp4j.InlayHintParams
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.LocationLink
import org.eclipse.lsp4j.ReferenceParams
import org.eclipse.lsp4j.SemanticTokens
import org.eclipse.lsp4j.SemanticTokensParams
import org.eclipse.lsp4j.SemanticTokensRangeParams
import org.eclipse.lsp4j.SymbolInformation
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WorkspaceService
import raylras.intellizen.lsp.provider.CompletionProvider
import raylras.intellizen.lsp.provider.DefinitionProvider
import raylras.intellizen.lsp.provider.DocumentSymbolProvider
import raylras.intellizen.lsp.provider.HoverProvider
import raylras.intellizen.lsp.provider.InlayHintProvider
import raylras.intellizen.lsp.provider.ReferencesProvider
import raylras.intellizen.lsp.provider.SemanticTokensProvider
import java.util.concurrent.CompletableFuture

class ZenLanguageService : TextDocumentService, WorkspaceService {
    override fun didOpen(params: DidOpenTextDocumentParams) {

    }

    override fun didChange(params: DidChangeTextDocumentParams) {

    }

    override fun didClose(params: DidCloseTextDocumentParams) {

    }

    override fun didSave(params: DidSaveTextDocumentParams) {

    }

    override fun didChangeConfiguration(params: DidChangeConfigurationParams) {

    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams?) {

    }

    override fun completion(params: CompletionParams): CompletableFuture<Either<List<CompletionItem>, CompletionList>> {
        val result = CompletionProvider.completion(params)
        return CompletableFuture.completedFuture(Either.forRight(result))
    }

    override fun resolveCompletionItem(unresolved: CompletionItem): CompletableFuture<CompletionItem> {
        val result = CompletionProvider.resolveCompletionItem(unresolved)
        return CompletableFuture.completedFuture(result)
    }

    override fun definition(params: DefinitionParams): CompletableFuture<Either<List<Location>, List<LocationLink>>> {
        val result = DefinitionProvider.definition(params)
        return CompletableFuture.completedFuture(Either.forRight(result))
    }

    override fun documentSymbol(params: DocumentSymbolParams): CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> {
        val result = DocumentSymbolProvider.documentSymbol(params)
        return CompletableFuture.completedFuture(result.map { Either.forRight<SymbolInformation, DocumentSymbol>(it) })
    }

    override fun hover(params: HoverParams): CompletableFuture<Hover?> {
        val result = HoverProvider.hover(params)
        return CompletableFuture.completedFuture(result)
    }

    override fun inlayHint(params: InlayHintParams): CompletableFuture<List<InlayHint>> {
        val result = InlayHintProvider.inlayHint(params)
        return CompletableFuture.completedFuture(result)
    }

    override fun references(params: ReferenceParams): CompletableFuture<List<Location>> {
        val result = ReferencesProvider.references(params)
        return CompletableFuture.completedFuture(result)
    }

    override fun semanticTokensFull(params: SemanticTokensParams): CompletableFuture<SemanticTokens> {
        val result = SemanticTokensProvider.semanticTokensFull(params)
        return CompletableFuture.completedFuture(result)
    }

    override fun semanticTokensRange(params: SemanticTokensRangeParams): CompletableFuture<SemanticTokens> {
        val result = SemanticTokensProvider.semanticTokensRange(params)
        return CompletableFuture.completedFuture(result)
    }
}
