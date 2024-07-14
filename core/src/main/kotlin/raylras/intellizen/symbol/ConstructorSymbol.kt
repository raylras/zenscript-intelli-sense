package raylras.intellizen.symbol

interface ConstructorSymbol : Symbol, Executable {
    val declaringClass: ClassSymbol
}
