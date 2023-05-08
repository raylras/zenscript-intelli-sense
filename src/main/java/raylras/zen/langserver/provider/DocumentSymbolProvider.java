package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import raylras.zen.code.CompilationUnit;

import java.util.Collections;
import java.util.List;

public class DocumentSymbolProvider {

    public static List<DocumentSymbol> documentSymbol(CompilationUnit unit, DocumentSymbolParams params) {
        return Collections.emptyList();
    }

}
