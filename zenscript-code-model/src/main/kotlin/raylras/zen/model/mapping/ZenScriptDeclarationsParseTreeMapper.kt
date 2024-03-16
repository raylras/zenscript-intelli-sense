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
        registerNodeFactory(QualifiedNameContext::class) { ctx ->
            Name(ctx.text)
        }
        registerNodeFactory(SimpleNameContext::class) { ctx ->
            Name(ctx.text)
        }

        //region Declaration
        registerNodeFactory(ImportDeclarationContext::class) { ctx ->
            ImportDeclaration(
                qualifiedName = translateCasted(ctx.qualifiedName()),
                alias = translateOptional(ctx.alias),
                simpleName = translateCasted(ctx.alias ?: ctx.qualifiedName().simpleName().last()),
            )
        }
        registerNodeFactory(ClassDeclarationContext::class) { ctx ->
            ClassDeclaration(
                simpleName = translateCasted(ctx.simpleClassName()),
                interfaces = translateList(ctx.interfaces),
                classBodyEntities = translateList(ctx.classBody().classBodyEntity())
            )
        }
        registerNodeFactory(SimpleClassNameContext::class) { ctx ->
            Name(
                name = ctx.text
            )
        }
        registerNodeFactory(ClassReferenceContext::class) { ctx ->
            ClassReference(
                className = translateCasted(ctx.qualifiedName())
            )
        }
        registerNodeFactory(ClassBodyEntityContext::class) { ctx ->
            translateOnlyChild(ctx)
        }
        registerNodeFactory(FieldDeclarationContext::class) { ctx ->
            FieldDeclaration(
                declaringKind = ctx.prefix.asDeclaringKind(),
                simpleName = translateCasted(ctx.simpleName()),
                typeLiteral = translateCasted(ctx.typeLiteral())
            )
        }
        registerNodeFactory(ConstructorDeclarationContext::class) { ctx ->
            ConstructorDeclaration(
                parameters = translateList(ctx.formalParameter())
            )
        }
        registerNodeFactory(MethodDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                declaringKind = ctx.prefix.asDeclaringKind(),
                simpleName = translateCasted(ctx.simpleName()),
                parameters = translateList(ctx.parameters),
                returnTypeLiteral = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(FunctionDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                declaringKind = ctx.prefix.asDeclaringKind(),
                simpleName = translateCasted(ctx.simpleName()),
                parameters = translateList(ctx.parameters),
                returnTypeLiteral = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(OperatorFunctionDeclarationContext::class) { ctx ->
            FunctionDeclaration(
                declaringKind = DeclaringKind.NONE,
                simpleName = translateCasted(ctx.operator()),
                parameters = translateList(ctx.parameters),
                returnTypeLiteral = translateCasted(ctx.returnType)
            )
        }
        registerNodeFactory(OperatorContext::class) { ctx ->
            Name(
                name = ctx.text
            )
        }
        registerNodeFactory(FormalParameterContext::class) { ctx ->
            ParameterDeclaration(
                isVararg = ctx.prefix?.type == DOT_DOT_DOT,
                simpleName = translateCasted(ctx.simpleName()),
                defaultValue = translateOptional(ctx.defaultValue)
            )
        }
        registerNodeFactory(VariableDeclarationContext::class) { ctx ->
            VariableDeclaration(
                declaringKind = ctx.prefix.asDeclaringKind(),
                simpleName = translateCasted(ctx.simpleName()),
                typeLiteral = translateCasted(ctx.typeLiteral())
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
                returnTypeLiteral = translateOptional(ctx.returnType),
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
            InstanceOfExpression(
                expression = translateCasted(ctx.expression()),
                typeLiteral = translateCasted(ctx.typeLiteral())
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
        registerNodeFactory(ArrayAccessExprContext::class) { ctx ->
            ArrayAccessExpression(
                receiver = translateCasted(ctx.receiver),
                index = translateCasted(ctx.index)
            )
        }
        registerNodeFactory(MemberAccessExprContext::class) { ctx ->
            MemberAccessExpression(
                receiver = translateCasted(ctx.expression()),
                ref = ReferenceByName((ctx.simpleName() ?: ctx.STRING_LITERAL()).text)
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
                typeName = ctx.qualifiedName().text
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
                typeName = ctx.text
            )
        }
        //endregion
    }
}

private fun Token?.asDeclaringKind(): DeclaringKind = when (this) {
    null -> DeclaringKind.NONE
    else -> when (this.type) {
        ZenScriptDeclarationsLexer.VAR -> DeclaringKind.VAR
        ZenScriptDeclarationsLexer.VAL -> DeclaringKind.VAL
        ZenScriptDeclarationsLexer.STATIC -> DeclaringKind.STATIC
        ZenScriptDeclarationsLexer.GLOBAL -> DeclaringKind.GLOBAL
        else -> throw NoSuchElementException(
            "Unknown declaring kind for token: $this." +
                    " Allowed values: ${DeclaringKind.entries.joinToString()}"
        )
    }
}
