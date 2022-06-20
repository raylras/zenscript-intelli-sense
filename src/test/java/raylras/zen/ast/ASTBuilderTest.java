package raylras.zen.ast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.decl.ImportDeclaration;
import raylras.zen.ast.decl.ZenClassDeclaration;
import raylras.zen.ast.visit.DefaultVisitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class ASTBuilderTest {

    static final class TestVisitor extends DefaultVisitor<Object> {
        @Override
        public Object visit(ImportDeclaration importDecl) {
            System.out.println("\t" + importDecl);
            return super.visit(importDecl);
        }
        @Override
        public Object visit(FunctionDeclaration funcDecl) {
            System.out.println("\t" + funcDecl);
            return super.visit(funcDecl);
        }

        @Override
        public Object visit(ZenClassDeclaration classDecl) {
            System.out.println("\t" + classDecl);
            return super.visit(classDecl);
        }
    }

    static final TestVisitor testVisitor = new TestVisitor();

    @Test
    void visitScriptUnit() throws IOException {
        Path scriptPath = Paths.get("src/test/resources/scripts/TestScript.zs");

        // ANTLR
        CharStream charStream = CharStreams.fromPath(scriptPath);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);

        // get a CST form antlr
        ZenScriptParser.ScriptUnitContext cst = parser.scriptUnit();

        // get an AST
        ASTBuilder astBuilder = new ASTBuilder(new CompileUnit(null));
        ScriptNode scriptNode = astBuilder.lower(scriptPath.toUri(), cst);

        scriptNode.accept(testVisitor);
    }

    @Test
    void visitScripts() throws FileNotFoundException {
        Path scriptsPath = Paths.get("src/test/resources/scripts");
        CompileUnit compileUnit = CompileUnit.fromPath(scriptsPath);
        compileUnit.getScriptNodes().forEach(scriptNode -> {
            System.out.println(scriptNode.getURI().getPath());
            scriptNode.accept(testVisitor);
        });
    }

}