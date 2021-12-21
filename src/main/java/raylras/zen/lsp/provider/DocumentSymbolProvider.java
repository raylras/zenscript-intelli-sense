package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.util.PosUtil;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.definitions.ParsedFunction;
import stanhebben.zenscript.util.ZenPosition;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DocumentSymbolProvider {

    private final String uri;
    private final ZenParsedFile parsedFile;

    public DocumentSymbolProvider(String uri, ZenParsedFile parsedFile) {
        this.uri = uri;
        this.parsedFile = parsedFile;
    }

    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> provideDocumentSymbol(DocumentSymbolParams params) {
        List<DocumentSymbol> symbols = new LinkedList<>();

        for (Map.Entry<String, ParsedFunction> entry : parsedFile.getFunctions().entrySet()) {
            ZenPosition fnStart = entry.getValue().getStart();
            ZenPosition fnEnd = entry.getValue().getEnd();

            Position start = PosUtil.convert(fnStart);
            Position end = PosUtil.convert(fnEnd);
            Range range = new Range(start, end);

            symbols.add(new DocumentSymbol(entry.getKey(), SymbolKind.Function, range, range));
        }

        List<Either<SymbolInformation, DocumentSymbol>> list = symbols.stream().map(Either::<SymbolInformation, DocumentSymbol>forRight).collect(Collectors.toList());

        return CompletableFuture.completedFuture(list);
    }

}
