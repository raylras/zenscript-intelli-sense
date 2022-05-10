package raylras.zen.lsp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TokenModifierTest {

    @Test
    void getInt() {

        int actual = TokenModifier.getInt(new TokenModifier[]{TokenModifier.Static, TokenModifier.Definition});
        int expected = TokenModifier.Static.getId() | TokenModifier.Definition.getId();
        Assertions.assertEquals(expected, actual);

        actual = TokenModifier.getInt(Arrays.asList(TokenModifier.Static, TokenModifier.Definition, TokenModifier.Readonly));
        expected = 1 | 2 | 4;
        Assertions.assertEquals(expected, actual);

        actual = TokenModifier.getInt(new TokenModifier[0]);
        expected = 0;
        Assertions.assertEquals(expected, actual);

    }


}