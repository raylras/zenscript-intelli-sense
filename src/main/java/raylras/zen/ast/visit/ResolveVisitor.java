package raylras.zen.ast.visit;

import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.CompileUnit;
import raylras.zen.ast.Node;

import java.util.HashMap;
import java.util.Map;

public class ResolveVisitor extends DefaultVisitor {

    private final CompileUnit compileUnit;
    private final Map<String, Node> globals;

    public ResolveVisitor(@NotNull CompileUnit compileUnit) {
        this.compileUnit = compileUnit;
        this.globals = new HashMap<>();
        lowerGlobal();

    }

    private void lowerGlobal(){
    }


}
