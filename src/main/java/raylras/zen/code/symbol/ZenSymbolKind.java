package raylras.zen.code.symbol;

public enum ZenSymbolKind {
    /**
     * a package of java library
     */
    LIBRARY_PACKAGE,
    /**
     * a package of script folders
     */
    SCRIPT_PACKAGE,
    /**
     * a class created by .zs files
     */
    ZEN_CLASS,
    /**
     * a class defined natively, like string
     */
    NATIVE_CLASS,
    /**
     * a class defined by java libraries
     */
    LIBRARY_CLASS,
    /**
     * a class defined by java libraries, but do not have an constructor
     * (TODO: is this should take original java interface into consideration?
     */
    INTERFACE,
    /**
     * a type defined by java functional interface and accepts a lambda expression
     */
    FUNCTIONAL_INTERFACE,
    /**
     * an operator accepts one operand
     */
    UNARY_OPERATOR,
    /**
     * an operator accepts two operands
     */
    BINARY_OPERATOR,
    /**
     * a unary operator but function as type casters
     */
    TYPE_CASTER,
    /**
     * a local variable
     */
    LOCAL_VARIABLE,
    /**
     * a global or static variable
     */
    GLOBAL_VARIABLE,
    /**
     * a field
     */
    FIELD,
    /**
     * a field, but is created by zen getters or setters
     * TODO: EXPORT SUPPORT
     */
    PROPERTY,
    /**
     * a function parameter
     */
    FUNCTION_PARAMETER,
    /**
     * a method
     */
    FUNCTION,
    /**
     * an expand function
     */
    EXPAND_FUNCTION,
    /**
     * a constructor
     */
    CONSTRUCTOR,
    /**
     * other
     */
    NONE;

    public boolean isClass() {
        return this == ZEN_CLASS || this == LIBRARY_CLASS || this == NATIVE_CLASS || this == INTERFACE || this == FUNCTIONAL_INTERFACE;
    }

    public boolean isPackage() {
        return this == LIBRARY_PACKAGE || this == SCRIPT_PACKAGE;
    }

    public boolean isVariable() {
        return this == FUNCTION_PARAMETER || this == LOCAL_VARIABLE || this == GLOBAL_VARIABLE || this == FIELD || this == PROPERTY;
    }

    public boolean isFunction() {
        return this == FUNCTION || this == EXPAND_FUNCTION || this == CONSTRUCTOR;
    }

    public boolean isOperator() {
        return this == UNARY_OPERATOR || this == BINARY_OPERATOR || this == TYPE_CASTER;
    }

    public boolean isLocal() {
        return this == LOCAL_VARIABLE || this == FUNCTION_PARAMETER;
    }
}
