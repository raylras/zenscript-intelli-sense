package raylras.zen.symbol;

import org.antlr.v4.runtime.Token;
import raylras.zen.scope.Scope;
import raylras.zen.type.TypeBracketHandler;

import java.util.List;
import java.util.stream.Collectors;

public class SymbolBracketHandler extends SymbolBase<TypeBracketHandler> {

    private final String symbolName;

    public SymbolBracketHandler(List<Token> tokens, Scope parent) {
        super(parent, TypeBracketHandler.INSTANCE);
        symbolName = tokens.stream().map(Token::getText).collect(Collectors.joining());
        this.setScopeName(symbolName);
    }

    @Override
    public String getSymbolName() {
        return symbolName;
    }

}
