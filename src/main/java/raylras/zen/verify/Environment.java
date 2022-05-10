package raylras.zen.verify;

import raylras.zen.ast.ASTNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {

    public final Map<String, NativeClass> nativeClassMap = new HashMap<>();
    public final List<ASTNode> globals = new ArrayList<>();

}
