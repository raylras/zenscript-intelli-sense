package raylras.zen.lsp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TokenModifierTest {

//    @Test
    void getInt() {
        int actual = TokenModifier.toBitFlag(TokenModifier.Static, TokenModifier.Definition);
        int expected = TokenModifier.Static.getId() | TokenModifier.Definition.getId();
        Assertions.assertEquals(expected, actual);

    }


}