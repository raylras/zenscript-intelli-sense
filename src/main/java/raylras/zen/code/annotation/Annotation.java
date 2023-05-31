package raylras.zen.code.annotation;

/**
 * Represents a special comment start with {@code '#'} that usually marked on the declarations.
 * <p>examples:
 * <pre>
 * #loader crafttweaker
 *
 * #extends IIngredient
 * zenClass IItemStack {
 *     #hidden
 *     #caster
 *     function asBlock() as IBlock {}
 * }
 * </pre>
 */
public class Annotation {

    protected final String[] data;

    public Annotation(String[] data) {
        this.data = data;
    }

    public static Annotation create(String rawAnnotationString) {
        return new Annotation(rawAnnotationString.split(" "));
    }

    public boolean isAnnotation(String header) {
        return header.equals(data[0].substring(1));
    }

}
