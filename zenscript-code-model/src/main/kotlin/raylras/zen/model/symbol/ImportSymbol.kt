package raylras.zen.model.symbol

interface ImportSymbol : Symbol, SymbolProvider {
    val qualifiedName: String
}
