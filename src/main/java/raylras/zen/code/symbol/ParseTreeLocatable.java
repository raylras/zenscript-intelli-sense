package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;

import java.nio.file.Path;

public interface ParseTreeLocatable extends Locatable {

    ParseTree getCst();

    CompilationUnit getUnit();

    default Path getPath() {
        return getUnit().getPath();
    }

    default String getUri() {
        return getUnit().getPath().toUri().toString();
    }

}
