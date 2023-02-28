package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.LocationLink;
import raylras.zen.code.SourceUnit;

import java.util.Collections;
import java.util.List;

public class DefinitionProvider {

    public static List<LocationLink> definition(SourceUnit sourceUnit, DefinitionParams params) {
        if (sourceUnit == null)
            return Collections.emptyList();
        if (sourceUnit.ast == null)
            sourceUnit.updateAll(null);
        // TODO
        return Collections.emptyList();
    }

}

