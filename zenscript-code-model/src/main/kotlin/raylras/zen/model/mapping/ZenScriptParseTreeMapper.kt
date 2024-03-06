package raylras.zen.model.mapping

import com.strumenta.kolasu.mapping.*
import com.strumenta.kolasu.model.ReferenceByName
import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.validation.Issue
import raylras.zen.model.ast.*
import raylras.zen.model.ast.expr.*
import raylras.zen.model.ast.stmt.*
import raylras.zen.model.parser.ZenScriptParser.*

class ZenScriptParseTreeMapper(
    issues: MutableList<Issue> = mutableListOf(),
    allowGenericNode: Boolean = true,
    source: Source? = null
) : ParseTreeToASTTransformer(issues, allowGenericNode, source) {
    init {
        registerNodeFactory(CompilationUnitContext::class) { ctx ->
            CompilationUnit(
                statements = translateList(ctx.statement()),
                functions = translateList(ctx.functionDeclaration()),
                expandFunctions = translateList(ctx.expandFunctionDeclaration()),
                classes = translateList(ctx.classDeclaration())
            )
        }

        //region Declaration
        registerNodeFactory(ClassDeclarationContext::class) { ctx ->
            ClassDeclaration(
                simpleName = ctx.simpleName().text,
                declaredFields = translateList(ctx.classBody()?.fieldDeclaration()),
                declaredConstructors = translateList(ctx.classBody()?.constructorDeclaration()),
                declaredMethods = translateList(ctx.classBody()?.methodDeclaration())
            )
        }
        registerNodeFactory(FieldDeclarationContext::class) { ctx ->
            FieldDeclaration(
                simpleName = ctx.simpleName().text,
                typeAnnotation = translateOptional(ctx.typeLiteral()),
                initializer = translateOptional(ctx.initializer)
            )
        }
        registerNodeFactory(ConstructorDeclarationContext::class) { ctx ->
            ConstructorDeclaration(
                parameters = translateList(ctx.formalParameter()),
                body = translateList(ctx.constructorBody()?.statement())
            )
        }
        registerNodeFactory(MethodDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                simpleName = ctx.simpleName().text,
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateOptional(ctx.returnType),
                body = translateList(ctx.methodBody()?.statement())
            )
        }
        registerNodeFactory(FunctionDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                simpleName = ctx.simpleName().text,
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateOptional(ctx.returnType),
                body = translateList(ctx.functionBody()?.statement())
            )
        }
        registerNodeFactory(ExpandFunctionDeclarationContext::class) { ctx ->
            ExpandFunctionDeclaration(
                receiver = translateCasted(ctx.typeLiteral()),
                simpleName = ctx.simpleName().text,
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateOptional(ctx.returnType),
                body = translateList(ctx.functionBody()?.statement())
            )
        }
        registerNodeFactory(FormalParameterContext::class) { ctx ->
            ParameterDeclaration(
                simpleName = ctx.simpleName().text,
                defaultValue = translateOptional(ctx.defaultValue)
            )
        }
        registerNodeFactory(VariableDeclarationContext::class) { ctx ->
            VariableDeclaration(
                simpleName = ctx.simpleName().text,
                typeAnnotation = translateOptional(ctx.typeLiteral()),
                initializer = translateOptional(ctx.initializer)
            )
        }
        //endregion

        //region Statement
        registerNodeFactory(StatementContext::class) { ctx ->
            translateOnlyChild(ctx)
        }
        registerNodeFactory(BlockStatementContext::class) { ctx ->
            BlockStatement(
                statements = translateList(ctx.statement())
            )
        }
        registerNodeFactory(ReturnStatementContext::class) { ctx ->
            ReturnStatement(
                value = translateOptional(ctx.expression())
            )
        }
        registerNodeFactory(BreakStatementContext::class) { ctx ->
            BreakStatement()
        }
        registerNodeFactory(ContinueStatementContext::class) { ctx ->
            ContinueStatement()
        }
        registerNodeFactory(IfStatementContext::class) { ctx ->
            IfStatement(
                condition = translateCasted(ctx.expression()),
                thenPart = translateCasted(ctx.thenPart),
                elsePart = translateOptional(ctx.elsePart)
            )
        }
        registerNodeFactory(ForeachStatementContext::class) { ctx ->
            ForeachStatement(
                variables = translateList(ctx.variables),
                iterable = translateCasted(ctx.iterable),
                body = translateList(ctx.foreachBody()?.statement())
            )
        }
        registerNodeFactory(ForeachVariableContext::class) { ctx ->
            VariableDeclaration(
                simpleName = ctx.simpleName().text
            )
        }
        registerNodeFactory(WhileStatementContext::class) { ctx ->
            WhileStatement(
                condition = translateCasted(ctx.expression()),
                body = translateCasted(ctx.statement())
            )
        }
        registerNodeFactory(ExpressionStatementContext::class) { ctx ->
            ExpressionStatement(
                expression = translateCasted(ctx.expression())
            )
        }
        //endregion

        // region Expression
        registerNodeFactory(ThisExprContext::class) { ctx ->
            ThisExpression()
        }
        registerNodeFactory(BoolLiteralContext::class) { ctx ->
            BoolLiteral(
                value = ctx.text.toBoolean()
            )
        }
        registerNodeFactory(IntLiteralContext::class) { ctx ->
            val radix = ctx.text.toRadix()
            val digits = ctx.text.asDigits()
            runCatching {
                IntLiteral(
                    value = digits.toInt(radix),
                    radix = radix,
                )
            }.getOrElse {
                LongLiteral(
                    value = digits.toLong(radix),
                    radix = radix
                )
            }
        }
        registerNodeFactory(LongLiteralContext::class) { ctx ->
            val radix = ctx.text.toRadix()
            val digits = ctx.text.asDigits()
            LongLiteral(
                value = digits.toLong(radix),
                radix = radix
            )
        }
        registerNodeFactory(FloatLiteralContext::class) { ctx ->
            FloatLiteral(
                value = ctx.text.toFloat()
            )
        }
        registerNodeFactory(DoubleLiteralContext::class) { ctx ->
            DoubleLiteral(
                value = ctx.text.toDouble()
            )
        }
        registerNodeFactory(StringLiteralContext::class) { ctx ->
            StringLiteral(
                value = ctx.text
            )
        }
        registerNodeFactory(NullLiteralContext::class) { ctx ->
            NullLiteral()
        }
        registerNodeFactory(ReferenceExprContext::class) { ctx ->
            ReferenceExpression(
                ref = ReferenceByName(ctx.simpleName().text)
            )
        }
        registerNodeFactory(FunctionExprContext::class) { ctx ->
            FunctionExpression(
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateOptional(ctx.returnType),
                body = translateList(ctx.functionBody()?.statement())
            )
        }
        registerNodeFactory(BracketHandlerExprContext::class) { ctx ->
            BracketHandlerExpression(
                content = ctx.content().text
            )
        }
        registerNodeFactory(ArrayLiteralContext::class) { ctx ->
            ArrayLiteral(
                elements = translateList(ctx.expression())
            )
        }
        registerNodeFactory(MapLiteralContext::class) { ctx ->
            MapLiteral(
                entries = translateList(ctx.entries)
            )
        }
        registerNodeFactory(MapEntryContext::class) { ctx ->
            MapEntry(
                key = translateCasted(ctx.key),
                value = translateCasted(ctx.value)
            )
        }
        registerNodeFactory(ParensExprContext::class) { ctx ->
            ParensExpression(
                expression = translateCasted(ctx.expression())
            )
        }
        registerNodeFactory(InstanceOfExprContext::class) { ctx ->
            BinaryExpression(
                left = translateCasted(ctx.left),
                operator = BinaryOperator.INSTANCEOF,
                right = translateCasted(ctx.right)
            )
        }
        registerNodeFactory(TypeCastExprContext::class) { ctx ->
            CastExpression(
                expression = translateCasted(ctx.expression()),
                typeLiteral = translateCasted(ctx.typeLiteral())
            )
        }
        registerNodeFactory(CallExprContext::class) { ctx ->
            CallExpression(
                receiver = translateCasted(ctx.receiver),
                arguments = translateList(ctx.arguments)
            )
        }
        registerNodeFactory(MemberIndexExprContext::class) { ctx ->
            MemberIndexExpression(
                receiver = translateCasted(ctx.receiver),
                index = translateCasted(ctx.index)
            )
        }
        registerNodeFactory(MemberAccessExprContext::class) { ctx ->
            MemberAccessExpression(
                receiver = translateCasted(ctx.expression()),
                member = ReferenceByName((ctx.simpleName() ?: ctx.STRING_LITERAL()).text)
            )
        }
        registerNodeFactory(IntRangeExprContext::class) { ctx ->
            IntRangeExpression(
                from = translateCasted(ctx.from),
                to = translateCasted(ctx.to)
            )
        }
        registerNodeFactory(UnaryExprContext::class) { ctx ->
            UnaryExpression(
                operator = UnaryOperator.fromString(ctx.op.text),
                expression = translateCasted(ctx.expression())
            )
        }
        registerNodeFactory(BinaryExprContext::class) { ctx ->
            BinaryExpression(
                left = translateCasted(ctx.left),
                operator = BinaryOperator.fromString(ctx.op.text),
                right = translateCasted(ctx.right)
            )
        }
        registerNodeFactory(TernaryExprContext::class) { ctx ->
            TernaryExpression(
                condition = translateCasted(ctx.condition),
                truePart = translateCasted(ctx.truePart),
                falsePart = translateCasted(ctx.falsePart)
            )
        }
        registerNodeFactory(AssignmentExprContext::class) { ctx ->
            BinaryExpression(
                left = translateCasted(ctx.left),
                operator = BinaryOperator.fromString(ctx.op.text),
                right = translateCasted(ctx.right)
            )
        }
        //endregion

        //region Type Literal
        registerNodeFactory(ReferenceTypeContext::class) { ctx ->
            ReferenceTypeLiteral(
                qualifiedName = ctx.qualifiedName().text
            )
        }
        registerNodeFactory(ArrayTypeContext::class) { ctx ->
            ArrayTypeLiteral(
                baseType = translateCasted(ctx.typeLiteral())
            )
        }
        registerNodeFactory(ListTypeContext::class) { ctx ->
            ListTypeLiteral(
                baseType = translateCasted(ctx.typeLiteral())
            )
        }
        registerNodeFactory(MapTypeContext::class) { ctx ->
            MapTypeLiteral(
                keyType = translateCasted(ctx.keyType),
                valueType = translateCasted(ctx.valueType)
            )
        }
        registerNodeFactory(FunctionTypeContext::class) { ctx ->
            FunctionTypeLiteral(
                parameterTypes = translateList(ctx.parameterTypes),
                returnType = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(PrimitiveTypeContext::class) { ctx ->
            PrimitiveTypeLiteral(
                simpleName = ctx.text
            )
        }
        //endregion
    }
}
