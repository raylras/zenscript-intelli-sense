package raylras.intellizen.parser

import com.strumenta.kolasu.mapping.ParseTreeToASTTransformer
import com.strumenta.kolasu.mapping.translateList
import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.model.debugPrint
import com.strumenta.kolasu.parsing.ANTLRTokenFactory
import com.strumenta.kolasu.parsing.KolasuANTLRToken
import com.strumenta.kolasu.parsing.KolasuParser
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.TokenStream
import raylras.intellizen.ast.CompilationUnit
import raylras.intellizen.parser.ZenScriptDeclarationsParser.*

class ZenScriptDeclarationsKolasuParser :
    KolasuParser<CompilationUnit, ZenScriptDeclarationsParser, CompilationUnitContext, KolasuANTLRToken>(ANTLRTokenFactory()) {
    override fun createANTLRLexer(charStream: CharStream): Lexer {
        return ZenScriptDeclarationsLexer(charStream)
    }

    override fun createANTLRParser(tokenStream: TokenStream): ZenScriptDeclarationsParser {
        return ZenScriptDeclarationsParser(tokenStream)
    }

    override fun parseTreeToAst(
        parseTreeRoot: CompilationUnitContext,
        considerPosition: Boolean,
        issues: MutableList<Issue>,
        source: Source?
    ): CompilationUnit? {
        val mapper = ZenScriptDeclarationsKolasuMapper(issues, source)
        return mapper.transform(parseTreeRoot) as? CompilationUnit
    }
}
class ZenScriptDeclarationsKolasuMapper(
    issues: MutableList<Issue> = mutableListOf(),
    source: Source? = null
) : ParseTreeToASTTransformer(issues, true, source) {
    init {
        registerNodeFactory<CompilationUnitContext, CompilationUnit> {
            CompilationUnit(
                toplevelEntities = translateList(toplevelEntity())
            )
        }

        // TODO
    }
}

fun main() {
    val code = """
        zenClass Foo extends Bar {
            val foo as Foo;
        }

        zenClass Bar {
            val bar as Bar;
        }
    """.trimIndent()
    val parser = ZenScriptDeclarationsKolasuParser()
    val result = parser.parse(code)
    val root = result.root!!

    println(root.debugPrint())
}
