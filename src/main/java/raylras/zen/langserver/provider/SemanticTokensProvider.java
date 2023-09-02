package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensParams;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.util.Range;

import java.util.ArrayList;
import java.util.List;

public class SemanticTokensProvider extends Listener {

    public static SemanticTokens semanticTokensFull(CompilationUnit unit, SemanticTokensParams params) {
        SemanticTokensProvider provider = new SemanticTokensProvider(unit);
        return new SemanticTokens(provider.data);
    }

    private final CompilationUnit unit;
    private final List<Integer> data = new ArrayList<>();
    private int prevLine = Range.FIRST_LINE;
    private int prevColumn = Range.FIRST_COLUMN;

    private SemanticTokensProvider(CompilationUnit unit) {
        this.unit = unit;
    }

    private void push(Range range, int tokenType, int tokenModifiers) {
        if (range == null) return;
        int line = range.start().line() - prevLine;
        int column = range.start().line() == prevLine ? range.start().column() - prevColumn : range.start().column();
        int length = range.end().column() - range.start().column();
        prevLine = range.start().line();
        prevColumn = range.start().column();
        data.add(line);
        data.add(column);
        data.add(length);
        data.add(tokenType);
        data.add(tokenModifiers);
    }

}
