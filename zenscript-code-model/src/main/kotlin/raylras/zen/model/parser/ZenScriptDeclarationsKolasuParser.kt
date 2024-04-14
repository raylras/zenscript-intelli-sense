package raylras.zen.model.parser

import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.model.debugPrint
import com.strumenta.kolasu.parsing.ANTLRTokenFactory
import com.strumenta.kolasu.parsing.KolasuANTLRToken
import com.strumenta.kolasu.parsing.KolasuParser
import com.strumenta.kolasu.semantics.symbol.resolver.SymbolResolver
import com.strumenta.kolasu.testing.assertReferencesNotResolved
import com.strumenta.kolasu.testing.assertReferencesResolved
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.TokenStream
import raylras.zen.model.ast.CompilationUnit
import raylras.zen.model.parser.ZenScriptDeclarationsParser.CompilationUnitContext
import raylras.zen.model.parser.mapper.ZenScriptDeclarationsNodeMapper
import raylras.zen.model.semantic.scope.provider.ZenScriptScopeProvider

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
        val mapper = ZenScriptDeclarationsNodeMapper(issues = issues, source = source)
        return mapper.transform(parseTreeRoot) as? CompilationUnit
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

    val symbolResolver = SymbolResolver(ZenScriptScopeProvider)
    root.assertReferencesNotResolved()
    symbolResolver.resolve(root, entireTree = true)
    root.assertReferencesResolved()

    println(root.debugPrint())
}
