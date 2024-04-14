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
import raylras.zen.model.parser.ZenScriptParser.CompilationUnitContext
import raylras.zen.model.parser.mapper.ZenScriptNodeMapper
import raylras.zen.model.semantic.scope.provider.ZenScriptScopeProvider

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
        val mapper = ZenScriptNodeMapper(issues = issues, source = source)
        return mapper.transform(parseTreeRoot) as? CompilationUnit
    }
}

fun main() {
    val code = """
        A.foo
        
        zenClass A {
            static foo as int;
        }
    """.trimIndent()
    val parser = ZenScriptKolasuParser()
    val result = parser.parse(code)

    val symbolResolver = SymbolResolver(ZenScriptScopeProvider)
    val root = result.root!!

    root.assertReferencesNotResolved()
    symbolResolver.resolve(root, entireTree = true)
    symbolResolver.resolve(root, entireTree = true)
    root.assertReferencesResolved()

    println(root.debugPrint())
}
