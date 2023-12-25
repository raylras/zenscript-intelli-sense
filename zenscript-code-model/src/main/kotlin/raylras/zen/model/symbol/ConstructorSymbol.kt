package raylras.zen.model.symbol

interface ConstructorSymbol : Symbol, Executable {
    val declaringClass: ClassSymbol
}
