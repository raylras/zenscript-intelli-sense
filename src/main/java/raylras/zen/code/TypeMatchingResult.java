package raylras.zen.code;

/**
 * @author youyihj
 */
public enum TypeMatchingResult {
    EQUALS,
    INHERIT,
    CASTER,
    INVALID;

    public TypeMatchingResult min(TypeMatchingResult typeMatchingResult) {
        return typeMatchingResult.ordinal() > this.ordinal() ? typeMatchingResult : this;
    }
}
