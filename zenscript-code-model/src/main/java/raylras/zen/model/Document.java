package raylras.zen.model;

import java.util.Optional;

public interface Document extends AutoCloseable {

    Optional<CompilationUnit> getUnit();

}
