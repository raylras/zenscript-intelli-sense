package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.*;
import raylras.zen.util.PosUtil;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.definitions.ParsedFunction;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.util.ZenPosition;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DocumentHighlightProvider {

    private final String uri;
    private final ZenParsedFile parsedFile;

    public DocumentHighlightProvider(String uri, ZenParsedFile parsedFile) {
        this.uri = uri;
        this.parsedFile = parsedFile;
    }

    public CompletableFuture<List<? extends DocumentHighlight>> provideDocumentHighlight(DocumentHighlightParams params) {
        List<DocumentHighlight> list = new LinkedList<>();

        for (Map.Entry<String, ParsedFunction> entry : parsedFile.getFunctions().entrySet()) {
            Token fnName = entry.getValue().getName();
            ZenPosition nameStart = fnName.getStart();
            ZenPosition nameEnd = fnName.getEnd();

            Position start = PosUtil.convert(nameStart);
            Position end = PosUtil.convert(nameEnd);
            Range range = new Range(start, end);

            list.add(new DocumentHighlight(range, DocumentHighlightKind.Read));
        }

        return CompletableFuture.completedFuture(list);
    }

}
