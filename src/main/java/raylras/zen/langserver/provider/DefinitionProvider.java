package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.LocationLink;
import raylras.zen.code.CompilationUnit;

import java.util.Collections;
import java.util.List;

public class DefinitionProvider {

    public static List<LocationLink> definition(CompilationUnit unit, DefinitionParams params) {
        return Collections.emptyList();
    }

}

