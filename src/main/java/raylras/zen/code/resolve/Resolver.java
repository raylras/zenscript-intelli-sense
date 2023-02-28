package raylras.zen.code.resolve;

import raylras.zen.code.CompilationContext;
import raylras.zen.code.SourceUnit;

public interface Resolver {

    void resolve(SourceUnit sourceUnit);

    void resolve(CompilationContext context);

}
