package raylras.zen.model.parser

import com.strumenta.kolasu.model.*
import com.strumenta.kolasu.parsing.ANTLRTokenFactory
import com.strumenta.kolasu.parsing.KolasuANTLRToken
import com.strumenta.kolasu.parsing.KolasuParser
import com.strumenta.kolasu.parsing.toPosition
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.TokenStream
import raylras.zen.model.ast.*
import raylras.zen.model.ast.expr.*
import raylras.zen.model.ast.stmt.*
import raylras.zen.model.parser.ZenScriptParser.*

object ZenScriptKolasuParser :
    KolasuParser<CompilationUnit, ZenScriptParser, CompilationUnitContext, KolasuANTLRToken>(ANTLRTokenFactory()) {
    private fun readResolve(): Any = ZenScriptKolasuParser

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
    ): CompilationUnit {
        return parseTreeRoot.toAst(considerPosition, issues, source)
    }
}

fun CompilationUnitContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): CompilationUnit {
    return CompilationUnit(
        statements = statement().map { it.toAst(considerPosition, issues, source) },
        functions = functionDeclaration().map { it.toAst(considerPosition, issues, source) },
        expandFunctions = expandFunctionDeclaration().map { it.toAst(considerPosition, issues, source) },
        classes = classDeclaration().map { it.toAst(considerPosition, issues, source) }
    ).withPosition(toPosition(considerPosition, source))
}

fun ClassDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ClassDeclaration {
    return ClassDeclaration(
        simpleName = simpleName().text,
        members = classBody().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ClassBodyContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): List<EntityDeclaration> {
    return listOf(
        constructorDeclaration().map { it.toAst(considerPosition, issues, source) },
        methodDeclaration().map { it.toAst(considerPosition, issues, source) },
        fieldDeclaration().map { it.toAst(considerPosition, issues, source) }
    ).flatten()
}

fun ConstructorDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ConstructorDeclaration {
    return ConstructorDeclaration(
        parameters = formalParameter().map { it.toAst(considerPosition, issues, source) },
        body = constructorBody().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ConstructorBodyContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): List<Statement> {
    return statement().map { it.toAst(considerPosition, issues, source) }
}

fun MethodDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): FunctionDeclaration {
    return FunctionDeclaration(
        simpleName = simpleName().text,
        parameters = formalParameter().map { it.toAst(considerPosition, issues, source) },
        returnTypeAnnotation = returnType()?.toAst(considerPosition, issues, source),
        body = methodBody().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun MethodBodyContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): List<Statement> {
    return statement().map { it.toAst(considerPosition, issues, source) }
}

fun FieldDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): FieldDeclaration {
    return FieldDeclaration(
        simpleName = simpleName().text,
        typeAnnotation = typeLiteral()?.toAst(considerPosition, issues, source),
        initializer = expression()?.toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun FunctionDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): FunctionDeclaration {
    return FunctionDeclaration(
        simpleName = simpleName().text,
        parameters = formalParameter().map { it.toAst(considerPosition, issues, source) },
        returnTypeAnnotation = returnType()?.toAst(considerPosition, issues, source),
        body = functionBody().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ExpandFunctionDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ExpandFunctionDeclaration {
    return ExpandFunctionDeclaration(
        receiver = typeLiteral().toAst(considerPosition, issues, source),
        simpleName = simpleName().text,
        parameters = formalParameter().map { it.toAst(considerPosition, issues, source) },
        returnTypeAnnotation = returnType()?.toAst(considerPosition, issues, source),
        body = functionBody().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun FunctionBodyContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): List<Statement> {
    return statement().map { it.toAst(considerPosition, issues, source) }
}

fun FormalParameterContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ParameterDeclaration {
    return ParameterDeclaration(
        simpleName = simpleName().text,
        defaultValue = defaultValue?.toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun VariableDeclarationContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): VariableDeclaration {
    return VariableDeclaration(
        simpleName = simpleName().text,
        typeAnnotation = typeLiteral()?.toAst(considerPosition, issues, source),
        initializer = expression()?.toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun StatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): Statement {
    return when {
        variableDeclaration() != null -> variableDeclaration().toAst(considerPosition, issues, source)
        blockStatement() != null -> blockStatement().toAst(considerPosition, issues, source)
        returnStatement() != null -> returnStatement().toAst(considerPosition, issues, source)
        breakStatement() != null -> breakStatement().toAst(considerPosition, issues, source)
        continueStatement() != null -> continueStatement().toAst(considerPosition, issues, source)
        ifStatement() != null -> ifStatement().toAst(considerPosition, issues, source)
        foreachStatement() != null -> foreachStatement().toAst(considerPosition, issues, source)
        whileStatement() != null -> whileStatement().toAst(considerPosition, issues, source)
        expressionStatement() != null -> expressionStatement().toAst(considerPosition, issues, source)
        else -> InvalidStatement(text).withPosition(toPosition(considerPosition, source))
    }
}

fun BlockStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): BlockStatement {
    return BlockStatement(
        statements = statement().map { it.toAst(considerPosition, issues, source) }
    ).withPosition(toPosition(considerPosition, source))
}

fun ReturnStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ReturnStatement {
    return ReturnStatement(
        value = expression()?.toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun BreakStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): BreakStatement {
    return BreakStatement().withPosition(toPosition(considerPosition, source))
}

fun ContinueStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ContinueStatement {
    return ContinueStatement().withPosition(toPosition(considerPosition, source))
}

fun IfStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): IfStatement {
    return IfStatement(
        condition = expression().toAst(considerPosition, issues, source),
        thenPart = thenPart.toAst(considerPosition, issues, source),
        elsePart = elsePart?.toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ForeachStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ForeachStatement {
    return ForeachStatement(
        variables = foreachVariable().map { it.toAst(considerPosition, issues, source) },
        iterable = expression().toAst(considerPosition, issues, source),
        body = foreachBody().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ForeachVariableContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): VariableDeclaration {
    return VariableDeclaration(
        simpleName = simpleName().text
    ).withPosition(toPosition(considerPosition, source))
}

fun ForeachBodyContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): List<Statement> {
    return statement().map { it.toAst(considerPosition, issues, source) }
}

fun WhileStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): WhileStatement {
    return WhileStatement(
        condition = expression().toAst(considerPosition, issues, source),
        body = statement().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ExpressionStatementContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): ExpressionStatement {
    return ExpressionStatement(
        expression = expression().toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ExpressionContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): Expression {
    val expression: Expression = when (this) {
        is ThisExprContext -> ThisExpression()

        is BoolLiteralContext -> BoolLiteral(
            value = text.toBoolean()
        )

        is IntLiteralContext -> runCatching {
            IntLiteral(
                value = java.lang.Integer.decode(text),
                radix = text.toRadix(),
            )
        }.recoverCatching {
            LongLiteral(
                value = java.lang.Long.decode(text),
                radix = text.toRadix()
            )
        }.getOrElse {
            InvalidExpression(text, it)
        }

        is LongLiteralContext -> runCatching {
            LongLiteral(
                value = java.lang.Long.decode(text),
                radix = text.toRadix()
            )
        }.getOrElse {
            InvalidExpression(text, it)
        }

        is FloatLiteralContext -> runCatching {
            FloatLiteral(
                value = text.toFloat()
            )
        }.getOrElse {
            InvalidExpression(text, it)
        }

        is DoubleLiteralContext -> runCatching {
            DoubleLiteral(
                value = text.toDouble()
            )
        }.getOrElse {
            InvalidExpression(text, it)
        }

        is StringLiteralContext -> StringLiteral(
            value = text
        )

        is NullLiteralContext -> NullLiteral()

        is SimpleNameExprContext -> ReferenceExpression(
            ref = ReferenceByName(simpleName().text),
        )

        is FunctionExprContext -> FunctionExpression(
            parameters = formalParameter().map { it.toAst(considerPosition, issues, source) },
            returnTypeAnnotation = returnType()?.toAst(considerPosition, issues, source),
            body = functionBody().toAst(considerPosition, issues, source)
        )

        is BracketHandlerExprContext -> BracketHandlerExpression(
            content = content().text,
        )

        is ArrayLiteralContext -> ArrayLiteral(
            elements = expression().map { it.toAst(considerPosition, issues, source) }
        )

        is MapLiteralContext -> MapLiteral(
            entries = mapEntry().map { it.toAst(considerPosition, issues, source) }
        )

        is ParensExprContext -> ParensExpression(
            expression = expression().toAst(considerPosition, issues, source)
        )

        is InstanceOfExprContext -> BinaryExpression(
            left = left.toAst(considerPosition, issues, source),
            operator = BinaryOperator.INSTANCEOF,
            right = right.toAst(considerPosition, issues, source)
        )

        is TypeCastExprContext -> CastExpression(
            expression = expression().toAst(considerPosition, issues, source),
            typeLiteral = typeLiteral().toAst(considerPosition, issues, source)
        )

        is CallExprContext -> CallExpression(
            receiver = callee.toAst(considerPosition, issues, source),
            arguments = argument().map { it.toAst(considerPosition, issues, source) }
        )

        is MemberIndexExprContext -> MemberIndexExpression(
            receiver = left.toAst(considerPosition, issues, source),
            index = index.toAst(considerPosition, issues, source)
        )

        is MemberAccessExprContext -> MemberAccessExpression(
            receiver = expression().toAst(considerPosition, issues, source),
            memberName = (simpleName() ?: STRING_LITERAL()).text
        )

        is IntRangeExprContext -> IntRangeExpression(
            from = from.toAst(considerPosition, issues, source),
            to = to.toAst(considerPosition, issues, source)
        )

        is UnaryExprContext -> UnaryExpression(
            operator = UnaryOperator.fromString(op.text),
            expression = expression().toAst(considerPosition, issues, source)
        )

        is BinaryExprContext -> BinaryExpression(
            left = left.toAst(considerPosition, issues, source),
            operator = BinaryOperator.fromString(op.text),
            right = right.toAst(considerPosition, issues, source)
        )

        is TernaryExprContext -> TernaryExpression(
            condition = condition.toAst(considerPosition, issues, source),
            truePart = truePart.toAst(considerPosition, issues, source),
            falsePart = falsePart.toAst(considerPosition, issues, source)
        )

        else -> InvalidExpression(text)
    }.withPosition(toPosition(considerPosition, source))
    return expression
}

fun MapEntryContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): MapEntry {
    return MapEntry(
        key = key.toAst(considerPosition, issues, source),
        value = value.toAst(considerPosition, issues, source)
    ).withPosition(toPosition(considerPosition, source))
}

fun ArgumentContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): Expression {
    return expression().toAst(considerPosition, issues, source)
}

fun TypeLiteralContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): TypeLiteral {
    val typeLiteral = when (this) {
        is ReferenceTypeContext -> ReferenceTypeLiteral(
            qualifiedName = qualifiedName().text
        )

        is ArrayTypeContext -> ArrayTypeLiteral(
            baseType = typeLiteral().toAst(considerPosition, issues, source)
        )

        is ListTypeContext -> ListTypeLiteral(
            baseType = typeLiteral().toAst(considerPosition, issues, source)
        )

        is MapTypeContext -> MapTypeLiteral(
            keyType = key.toAst(considerPosition, issues, source),
            valueType = value.toAst(considerPosition, issues, source)
        )

        is FunctionTypeContext -> FunctionTypeLiteral(
            parameterTypes = typeLiteral().map { it.toAst(considerPosition, issues, source) },
            returnType = returnType().toAst(considerPosition, issues, source)
        )

        is PrimitiveTypeContext -> PrimitiveTypeLiteral(
            simpleName = text
        )

        else -> InvalidTypeLiteral(
            text = text
        )
    }.withPosition(toPosition(considerPosition, source))
    return typeLiteral
}

fun ReturnTypeContext.toAst(
    considerPosition: Boolean,
    issues: MutableList<Issue>,
    source: Source?
): TypeLiteral {
    return typeLiteral().toAst(considerPosition, issues, source)
}

fun main() {
    val code = """
        function foo() {
            foo();
        }
    """.trimIndent()
    val result = ZenScriptKolasuParser.parse(code)
    println(result)
}
