package raylras.zen.lsp;

import raylras.zen.model.CompilationUnit;

import java.util.Optional;

public interface Document extends AutoCloseable {

    Optional<CompilationUnit> getUnit();

}
