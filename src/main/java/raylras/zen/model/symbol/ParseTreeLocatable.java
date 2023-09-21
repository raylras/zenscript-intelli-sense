package raylras.zen.model.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.CompilationUnit;

import java.nio.file.Path;

public interface ParseTreeLocatable extends Locatable {

    ParseTree getCst();

    CompilationUnit getUnit();

    default Path getPath() {
        return getUnit().getPath();
    }

    default String getUri() {
        return getPath().toUri().toString();
    }

}
