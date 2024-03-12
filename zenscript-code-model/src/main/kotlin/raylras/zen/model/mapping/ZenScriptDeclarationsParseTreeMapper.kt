package raylras.zen.model.mapping

import com.strumenta.kolasu.mapping.*
import com.strumenta.kolasu.model.ReferenceByName
import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.Token
import raylras.zen.model.ast.*
import raylras.zen.model.ast.expr.*
import raylras.zen.model.parser.ZenScriptDeclarationsLexer
import raylras.zen.model.parser.ZenScriptDeclarationsParser.*

class ZenScriptDeclarationsParseTreeMapper(
    issues: MutableList<Issue> = mutableListOf(),
    allowGenericNode: Boolean = true,
    source: Source? = null
) : ParseTreeToASTTransformer(issues, allowGenericNode, source) {
    init {
        registerNodeFactory(CompilationUnitContext::class) { ctx ->
            CompilationUnit(
                toplevelEntities = translateList(ctx.toplevelEntity())
            )
        }
        registerNodeFactory(ToplevelEntityContext::class) { ctx ->
            translateOnlyChild(ctx)
        }

        //region Declaration
        registerNodeFactory(ImportDeclarationContext::class) { ctx ->
            ImportDeclaration(
                qualifiedName = ctx.qualifiedName().text,
                alias = translateOptional(ctx.alias)
            )
        }
        registerNodeFactory(ClassDeclarationContext::class) { ctx ->
            ClassDeclaration(
                simpleName = ctx.simpleClassName().text,
                interfaces = ctx.interfaces.map { ReferenceByName(it.text) },
                classBodyEntities = translateList(ctx.classBody().classBodyEntity())
            )
        }
        registerNodeFactory(ClassBodyEntityContext::class) { ctx ->
            translateOnlyChild(ctx)
        }
        registerNodeFactory(FieldDeclarationContext::class) { ctx ->
            FieldDeclaration(
                declaringType = ctx.prefix.asDeclaringType(),
                simpleName = ctx.simpleName().text,
                typeAnnotation = translateCasted(ctx.typeLiteral())
            )
        }
        registerNodeFactory(ConstructorDeclarationContext::class) { ctx ->
            ConstructorDeclaration(
                parameters = translateList(ctx.formalParameter())
            )
        }
        registerNodeFactory(MethodDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                declaringType = ctx.prefix.asDeclaringType(),
                simpleName = ctx.simpleName().text,
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(FunctionDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                declaringType = ctx.prefix.asDeclaringType(),
                simpleName = ctx.simpleName().text,
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(OperatorFunctionDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                declaringType = DeclaringType.NONE,
                simpleName = ctx.operator().text,
                parameters = translateList(ctx.parameters),
                returnTypeAnnotation = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(FormalParameterContext::class) { ctx ->
            ParameterDeclaration(
                isVararg = ctx.prefix?.type == DOT_DOT_DOT,
                simpleName = ctx.simpleName().text,
                defaultValue = translateOptional(ctx.defaultValue)
            )
        }
        registerNodeFactory(VariableDeclarationContext::class) { ctx ->
            VariableDeclaration(
                declaringType = ctx.prefix.asDeclaringType(),
                simpleName = ctx.simpleName().text,
                typeAnnotation = translateCasted(ctx.typeLiteral())
            )
        }
        //endregion

        //region Expression
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
        registerNodeFactory(UnionTypeContext::class) { ctx ->
            UnionTypeLiteral(
                subTypes = translateList(ctx.typeLiteral())
            )
        }
        registerNodeFactory(IntersectionTypeContext::class) { ctx ->
            IntersectionTypeLiteral(
                subTypes = translateList(ctx.typeLiteral())
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

private fun Token?.asDeclaringType(): DeclaringType = when (this) {
    null -> DeclaringType.NONE
    else -> when (this.type) {
        ZenScriptDeclarationsLexer.VAR -> DeclaringType.VAR
        ZenScriptDeclarationsLexer.VAL -> DeclaringType.VAL
        ZenScriptDeclarationsLexer.STATIC -> DeclaringType.STATIC
        ZenScriptDeclarationsLexer.GLOBAL -> DeclaringType.GLOBAL
        else -> throw NoSuchElementException(
            "Unknown declaring type for token: $this." +
                    " Allowed values: ${DeclaringType.entries.joinToString()}"
        )
    }
}
