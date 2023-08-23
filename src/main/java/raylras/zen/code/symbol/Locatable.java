package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.util.Range;

public interface Locatable {

    ParseTree getCst();

    CompilationUnit getUnit();

    Range getRange();

}
