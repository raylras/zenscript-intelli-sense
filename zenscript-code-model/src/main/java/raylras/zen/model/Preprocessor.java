package raylras.zen.model;

import java.util.Arrays;

public record Preprocessor(String header, String[] data) {

    public static Preprocessor create(String raw) {
        String[] split = raw.split(" ");
        String header = split[0];
        String[] data = Arrays.copyOfRange(split, 1, split.length);
        return new Preprocessor(header, data);
    }

}
