package raylras.zen.code.annotation;

import java.util.Arrays;

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

    protected final String header;
    protected final String[] data;

    public Annotation(String header, String[] data) {
        this.header = header;
        this.data = data;
    }

    public static Annotation create(String rawAnnotationString) {
        String[] raw = rawAnnotationString.split(" ");
        String header;
        String[] data;
        if (raw.length > 1) {
            header = raw[0];
            data = Arrays.copyOfRange(raw, 1, raw.length);
        } else if (raw.length == 1) {
            header = raw[0];
            data = new String[0];
        } else {
            return null;
        }
        return new Annotation(header, data);
    }

    public String getHeader() {
        return header;
    }

    public String[] getData() {
        return data;
    }

}
