package raylras.zen.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class CompilationsKtTest {

    @Test
    fun extractClassName() {
        assertEquals("my_path.a_file_100", extractClassName(Path("my path\\a-file.100.zs")))
        assertEquals("my_path.a_file_100", extractClassName(Path("my path/a-file.100.zs")))
    }

}
