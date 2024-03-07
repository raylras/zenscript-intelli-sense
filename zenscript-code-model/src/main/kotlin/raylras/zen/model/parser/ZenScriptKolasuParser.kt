package raylras.zen.model.parser

import com.strumenta.kolasu.model.Source
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
import raylras.zen.model.mapping.ZenScriptParseTreeMapper
import raylras.zen.model.parser.ZenScriptParser.CompilationUnitContext
import raylras.zen.model.semantics.scope.ZenScriptScopeProvider

class ZenScriptKolasuParser :
    KolasuParser<CompilationUnit, ZenScriptParser, CompilationUnitContext, KolasuANTLRToken>(ANTLRTokenFactory()) {

    override fun createANTLRLexer(charStream: CharStream): Lexer {
        return ZenScriptLexer(charStream)
    }

    override fun createANTLRParser(tokenStream: TokenStream): ZenScriptParser {
        return ZenScriptParser(tokenStream)
    }

    override fun parseTreeToAst(
        parseTreeRoot: CompilationUnitContext,
        considerPosition: Boolean,
        issues: MutableList<Issue>,
        source: Source?
    ): CompilationUnit? {
        val mapper = ZenScriptParseTreeMapper(issues = issues, source = source)
        return mapper.transform(parseTreeRoot) as? CompilationUnit
    }
}

fun main() {
    val code = """
        function foo(val arg0) {
            foo(arg0);
        }
    """.trimIndent()
    val parser = ZenScriptKolasuParser()
    val result = parser.parse(code)

    val symbolResolver = SymbolResolver(ZenScriptScopeProvider)
    val root = result.root!!

    root.assertReferencesNotResolved()
    symbolResolver.resolve(root, entireTree = true)
    root.assertReferencesResolved()

    println(root)
}