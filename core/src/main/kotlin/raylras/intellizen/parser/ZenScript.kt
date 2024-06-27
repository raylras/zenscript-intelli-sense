package raylras.intellizen.parser

import com.strumenta.kolasu.mapping.*
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.model.debugPrint
import com.strumenta.kolasu.parsing.ANTLRTokenFactory
import com.strumenta.kolasu.parsing.KolasuANTLRToken
import com.strumenta.kolasu.parsing.KolasuParser
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.TokenStream
import raylras.intellizen.ast.*
import raylras.intellizen.parser.ZenScriptParser.*

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
        registerNodeFactory<VariableDeclarationContext, VariableDeclaration> {
            VariableDeclaration(
                declaringKind = declaringKind(prefix),
                identifier = translateCasted(simpleIdentifier()),
                typeLiteral = translateOptional(type()),
                initializer = translateOptional(expression())
            )
        }
        registerNodeFactory<FunctionDeclarationContext, FunctionDeclaration> {
            FunctionDeclaration(
                declaringKind = declaringKind(prefix),
                identifier = translateOptional(simpleIdentifier()),
                parameters = functionValueParameters().functionValueParameter().map { translateCasted(it) },
                returnTypeLiteral = translateOptional(type()),
                body = block().statement().map { translateCasted(it) }
            )
        }
        registerNodeFactory<ExpandFunctionDeclarationContext, ExpandFunctionDeclaration> {
            ExpandFunctionDeclaration(
                receiverTypeLiteral = translateCasted(type()),
                identifier = translateCasted(simpleIdentifier()),
                parameters = functionValueParameters().functionValueParameter().map { translateCasted(it) },
                returnTypeLiteral = translateOptional(type()),
                body = block().statement().map { translateCasted(it) }
            )
        }
        registerNodeFactory<FunctionValueParameterContext, ParameterDeclaration> {
            ParameterDeclaration(
                identifier = translateCasted(simpleIdentifier()),
                typeLiteral = translateOptional(type()),
                defaultValue = translateOptional(expression())
            )
        }
        registerNodeFactory<ClassDeclarationContext, ClassDeclaration> {
            ClassDeclaration(
                identifier = translateCasted(simpleIdentifier()),
                classBodyEntities = classBody().classMemberDeclaration().map { translateOnlyChild(it) }
            )
        }
        registerNodeFactory<ConstructorDeclarationContext, ConstructorDeclaration> {
            ConstructorDeclaration(
                parameters = functionValueParameters().functionValueParameter().map { translateCasted(it) },
                body = block().statement().map { translateCasted(it) }
            )
        }

        //region TypeLiteral
        registerNodeFactory<TypeContext, TypeLiteral> {
            translateOnlyChild(this)
        }
        registerNodeFactory<FunctionTypeContext, FunctionTypeLiteral> {
            FunctionTypeLiteral(
                parameterTypes = translateList(parameters),
                returnType = translateCasted(returnType)
            )
        }
        registerNodeFactory<ParenthesizedTypeContext, TypeLiteral> {
            translateOnlyChild(this)
        }
        registerNodeFactory<ListTypeContext, ListTypeLiteral> {
            ListTypeLiteral(
                baseType = translateCasted(type())
            )
        }
        registerNodeFactory<ArrayTypeContext, ArrayTypeLiteral> {
            ArrayTypeLiteral(
                baseType = translateCasted(type())
            )
        }
        registerNodeFactory<MapTypeContext, MapTypeLiteral> {
            MapTypeLiteral(
                keyType = translateCasted(key),
                valueType = translateCasted(value)
            )
        }
        registerNodeFactory<PrimitiveTypeContext, PrimitiveTypeLiteral> {
            PrimitiveTypeLiteral(
                typeName = text
            )
        }
        //endregion

        // TODO
    }
}

fun declaringKind(token: Token?): DeclaringKind = when (token?.type) {
    VAR -> DeclaringKind.VAR
    VAL -> DeclaringKind.VAL
    STATIC -> DeclaringKind.STATIC
    GLOBAL -> DeclaringKind.GLOBAL
    else -> DeclaringKind.NONE
}

fun main() {
    val code = """
        static foo as
    """.trimIndent()
    val parser = ZenScriptKolasuParser()
    val result = parser.parse(code)
    val root = result.root!!

    println(root.debugPrint())
}
