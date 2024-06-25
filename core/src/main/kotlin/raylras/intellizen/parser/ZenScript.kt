package raylras.intellizen.parser

import com.strumenta.kolasu.mapping.ParseTreeToASTTransformer
import com.strumenta.kolasu.mapping.translateCasted
import com.strumenta.kolasu.mapping.translateList
import com.strumenta.kolasu.mapping.translateOnlyChild
import com.strumenta.kolasu.mapping.translateOptional
import com.strumenta.kolasu.model.Node
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
import raylras.intellizen.ast.Identifier
import raylras.intellizen.ast.ImportDeclaration
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.parser.ZenScriptParser.CompilationUnitContext

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
        val mapper = ZenScriptKolasuMapper(issues, source)
        return mapper.transform(parseTreeRoot) as? CompilationUnit
    }
}

class ZenScriptKolasuMapper(
    issues: MutableList<Issue> = mutableListOf(),
    source: Source? = null
) : ParseTreeToASTTransformer(issues, true, source) {
    init {
        registerNodeFactory<CompilationUnitContext, CompilationUnit> {
            CompilationUnit(
                toplevelEntities = translateList(toplevelEntity())
            )
        }
        registerNodeFactory<ToplevelEntityContext, Node> {
            translateOnlyChild<Node>(this)
        }
        registerNodeFactory<ImportDeclarationContext, ImportDeclaration> {
            ImportDeclaration(
                identifier = translateCasted(identifier()),
                simpleIdentifier = translateCasted(identifier().simpleIdentifier().last()),
                alias = translateOptional(alias),
            )
        }
        registerNodeFactory<IdentifierContext, Identifier> {
            Identifier(
                text = text
            )
        }
        registerNodeFactory<SimpleIdentifierContext, Identifier> {
            Identifier(
                text = text
            )
        }

        // TODO
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
    val root = result.root!!

    println(root.debugPrint())
}
