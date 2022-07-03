package raylras.zen.ls;

import org.junit.jupiter.api.Assertions;

class TokenModifierTest {

//    @Test
    void getInt() {
        int actual = TokenModifier.toBitFlag(TokenModifier.Static, TokenModifier.Definition);
        int expected = TokenModifier.Static.getId() | TokenModifier.Definition.getId();
        Assertions.assertEquals(expected, actual);

    }


}