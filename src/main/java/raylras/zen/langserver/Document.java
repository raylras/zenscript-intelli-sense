package raylras.zen.langserver;

import raylras.zen.code.CompilationUnit;

import java.util.Optional;

public interface Document extends AutoCloseable {

    Optional<CompilationUnit> getUnit();

}
