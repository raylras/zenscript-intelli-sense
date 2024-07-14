package raylras.intellizen.symbol

interface PackageSymbol : Symbol, SymbolProvider {
    val subpackages: Sequence<PackageSymbol>

    val members: Sequence<Symbol>
}
