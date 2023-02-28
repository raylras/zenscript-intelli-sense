package raylras.zen.code;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The compilation context contains all the source files under the compile root.
 */
public class CompilationContext {

    public Map<Path, SourceUnit> sourceMap;
    public Path root;

    public CompilationContext(Path root) {
        this.sourceMap = new HashMap<>();
        this.root = root;
    }

}
