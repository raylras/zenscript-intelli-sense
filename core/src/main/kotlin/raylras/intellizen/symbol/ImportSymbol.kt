package raylras.intellizen.symbol

interface ImportSymbol : Symbol, SymbolProvider {
    val qualifiedName: String
}
