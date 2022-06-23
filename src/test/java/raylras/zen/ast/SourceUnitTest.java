package raylras.zen.ast;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

class SourceUnitTest {

    Path scriptPath = Paths.get("src/test/resources/scripts/TestScript.zs");
    SourceUnit sourceUnit = new SourceUnit(scriptPath.toUri(), null);
    ASTBuilder builder = new ASTBuilder();

//    @Test
    void test() throws FileNotFoundException {
        sourceUnit.parse(new FileReader(scriptPath.toFile()));
        sourceUnit.convert(builder);
    }

}