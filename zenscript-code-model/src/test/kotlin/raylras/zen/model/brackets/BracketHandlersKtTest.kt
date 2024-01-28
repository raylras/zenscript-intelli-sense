package raylras.zen.model.brackets

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BracketHandlersKtTest {

    @Test
    fun specialize() {
        assertEquals("*", specialize("*"))
        assertEquals("minecraft:stone", specialize("minecraft:stone"))
        assertEquals("minecraft:stone", specialize("minecraft:stone:0"))
        assertEquals("minecraft:stone", specialize("item:minecraft:stone"))
        assertEquals("minecraft:stone", specialize("item:minecraft:stone:0"))
        assertEquals("minecraft:stone:10", specialize("minecraft:stone:10"))
        assertEquals("minecraft:stone:10", specialize("item:minecraft:stone:10"))
    }

}