package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// TODO: DocumentSymbolProvider
public class DocumentSymbolProvider extends ZenScriptParserBaseVisitor<DocumentSymbol> {

    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> provideDocumentSymbol(DocumentSymbolParams params) {
        return null;
    }

}
