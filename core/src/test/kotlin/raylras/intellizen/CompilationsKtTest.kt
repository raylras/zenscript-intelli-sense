package raylras.intellizen

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class CompilationsKtTest {

    @Test
    fun extractClassName() {
        assertEquals("my_path.a_file_100", raylras.intellizen.extractClassName(Path("my path\\a-file.100.zs")))
        assertEquals("my_path.a_file_100", raylras.intellizen.extractClassName(Path("my path/a-file.100.zs")))
    }

}
