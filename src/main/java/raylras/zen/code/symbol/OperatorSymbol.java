package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.util.List;

// TODO!!: change!
public class OperatorSymbol extends FunctionSymbol {
    private final String annoHead;
    private final String annoContent;
    public OperatorSymbol(CompilationUnit unit, String annoHead, String annoContent) {
        super(null, unit, false);
        this.annoHead = annoHead;
        this.annoContent = annoContent;
    }

    @Override
    public String getName() {
        // TODO: how operator named?
        return "<operator>";
    }

    public boolean isCaster() {
        return "caster".equals(annoHead);
    }


    @Override
    public List<VariableSymbol> getParams() {
        throw new IllegalStateException("Operators do not have actual param symbols");
    }

    @Override
    public FunctionType getType() {
        return super.getType();
    }

    @Override
    public Type getReturnType() {
        return super.getReturnType();
    }



    @Override
    public ZenSymbolKind getKind() {
        if (isCaster()) {
            return ZenSymbolKind.TYPE_CASTER;
        }
        return ZenSymbolKind.BINARY_OPERATOR;
    }

}
