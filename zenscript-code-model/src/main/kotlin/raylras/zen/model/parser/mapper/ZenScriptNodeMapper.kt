package raylras.zen.model.parser.mapper

import com.strumenta.kolasu.mapping.*
import com.strumenta.kolasu.model.ReferenceByName
import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.Token
import raylras.zen.model.ast.*
import raylras.zen.model.ast.expr.*
import raylras.zen.model.ast.stmt.*
import raylras.zen.model.parser.ZenScriptLexer
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.transformation.nodeFor

class ZenScriptNodeMapper(
    issues: MutableList<Issue> = mutableListOf(),
    allowGenericNode: Boolean = true,
    source: Source? = null
) : ParseTreeToASTTransformer(issues, allowGenericNode, source) {
    init {
        nodeFor<CompilationUnitContext> {
            CompilationUnit(
                toplevelEntities = translateList(toplevelEntity())
            )
        }
        nodeFor<ToplevelEntityContext> {
            translateOnlyChild(this)
        }
        nodeFor<SimpleNameContext> {
            Name(text)
        }
        nodeFor<QualifiedNameContext> {
            Name(text)
        }

        //region Declaration
        nodeFor<ImportDeclarationContext> {
            ImportDeclaration(
                qualifiedName = translateCasted(qualifiedName()),
                alias = translateOptional(alias),
                simpleName = translateCasted(alias ?: qualifiedName().simpleName().last())
            )
        }
        nodeFor<ClassDeclarationContext> {
            ClassDeclaration(
                simpleName = translateCasted(simpleName()),
                classBodyEntities = translateList(classBody().classBodyEntity())
            )
        }
        nodeFor<ClassBodyEntityContext> {
            translateOnlyChild(this)
        }
        nodeFor<FieldDeclarationContext> {
            FieldDeclaration(
                declaringKind = prefix.asDeclaringKind(),
                simpleName = translateCasted(simpleName()),
                typeLiteral = translateOptional(typeLiteral()),
                initializer = translateOptional(initializer)
            )
        }
        nodeFor<ConstructorDeclarationContext> {
            ConstructorDeclaration(
                parameters = translateList(formalParameter()),
                body = translateList(constructorBody()?.statement())
            )
        }
        nodeFor<MethodDeclarationContext> {
            FunctionDeclaration(
                declaringKind = prefix.asDeclaringKind(),
                simpleName = translateCasted(simpleName()),
                parameters = translateList(parameters),
                returnTypeLiteral = translateOptional(returnType),
                body = translateList(methodBody()?.statement())
            )
        }
        nodeFor<FunctionDeclarationContext> {
            FunctionDeclaration(
                declaringKind = prefix.asDeclaringKind(),
                simpleName = translateCasted(simpleName()),
                parameters = translateList(parameters),
                returnTypeLiteral = translateOptional(returnType),
                body = translateList(functionBody()?.statement())
            )
        }
        nodeFor<ExpandFunctionDeclarationContext> {
            ExpandFunctionDeclaration(
                receiver = translateCasted(typeLiteral()),
                simpleName = translateCasted(simpleName()),
                parameters = translateList(parameters),
                returnTypeLiteral = translateOptional(returnType),
                body = translateList(functionBody()?.statement())
            )
        }
        nodeFor<FormalParameterContext> {
            ParameterDeclaration(
                simpleName = translateCasted(simpleName()),
                typeLiteral = translateOptional(typeLiteral()),
                defaultValue = translateOptional(defaultValue)
            )
        }
        nodeFor<VariableDeclarationContext> {
            VariableDeclaration(
                declaringKind = prefix.asDeclaringKind(),
                simpleName = translateCasted(simpleName()),
                typeLiteral = translateOptional(typeLiteral()),
                initializer = translateOptional(initializer)
            )
        }
        //endregion

        //region Statement
        nodeFor<StatementContext> {
            translateOnlyChild(this)
        }
        nodeFor<BlockStatementContext> {
            BlockStatement(
                statements = translateList(statement())
            )
        }
        nodeFor<ReturnStatementContext> {
            ReturnStatement(
                value = translateOptional(expression())
            )
        }
        nodeFor<BreakStatementContext> {
            BreakStatement()
        }
        nodeFor<ContinueStatementContext> {
            ContinueStatement()
        }
        nodeFor<IfStatementContext> {
            IfStatement(
                condition = translateCasted(expression()),
                thenPart = translateCasted(thenPart),
                elsePart = translateOptional(elsePart)
            )
        }
        nodeFor<ForeachStatementContext> {
            ForeachStatement(
                variables = translateList(variables),
                iterable = translateCasted(iterable),
                body = translateList(foreachBody()?.statement())
            )
        }
        nodeFor<ForeachVariableContext> {
            VariableDeclaration(
                declaringKind = DeclaringKind.NONE,
                simpleName = translateCasted(simpleName()),
            )
        }
        nodeFor<WhileStatementContext> {
            WhileStatement(
                condition = translateCasted(expression()),
                body = translateCasted(statement())
            )
        }
        nodeFor<ExpressionStatementContext> {
            ExpressionStatement(
                expression = translateCasted(expression())
            )
        }
        //endregion

        //region Expression
        nodeFor<ThisExprContext> {
            ThisExpression()
        }
        nodeFor<BoolLiteralContext> {
            BoolLiteral(
                value = text.toBoolean()
            )
        }
        nodeFor<IntLiteralContext> {
            val radix = text.toRadix()
            val digits = text.asDigits()
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
        nodeFor<LongLiteralContext> {
            val radix = text.toRadix()
            val digits = text.asDigits()
            LongLiteral(
                value = digits.toLong(radix),
                radix = radix
            )
        }
        nodeFor<FloatLiteralContext> {
            FloatLiteral(
                value = text.toFloat()
            )
        }
        nodeFor<DoubleLiteralContext> {
            DoubleLiteral(
                value = text.toDouble()
            )
        }
        nodeFor<StringLiteralContext> {
            StringLiteral(
                value = text
            )
        }
        nodeFor<NullLiteralContext> {
            NullLiteral()
        }
        nodeFor<ReferenceExprContext> {
            ReferenceExpression(
                ref = ReferenceByName(simpleName().text)
            )
        }
        nodeFor<FunctionExprContext> {
            FunctionExpression(
                parameters = translateList(parameters),
                returnTypeLiteral = translateOptional(returnType),
                body = translateList(functionBody()?.statement())
            )
        }
        nodeFor<BracketHandlerExprContext> {
            BracketHandlerExpression(
                content = content().text
            )
        }
        nodeFor<ArrayLiteralContext> {
            ArrayLiteral(
                elements = translateList(expression())
            )
        }
        nodeFor<MapLiteralContext> {
            MapLiteral(
                entries = translateList(entries)
            )
        }
        nodeFor<MapEntryContext> {
            MapEntry(
                key = translateCasted(key),
                value = translateCasted(value)
            )
        }
        nodeFor<ParensExprContext> {
            ParensExpression(
                expression = translateCasted(expression())
            )
        }
        nodeFor<InstanceOfExprContext> {
            InstanceOfExpression(
                expression = translateCasted(expression()),
                typeLiteral = translateCasted(typeLiteral())
            )
        }
        nodeFor<TypeCastExprContext> {
            CastExpression(
                expression = translateCasted(expression()),
                typeLiteral = translateCasted(typeLiteral())
            )
        }
        nodeFor<CallExprContext> {
            CallExpression(
                receiver = translateCasted(receiver),
                arguments = translateList(arguments)
            )
        }
        nodeFor<ArrayAccessExprContext> {
            ArrayAccessExpression(
                receiver = translateCasted(receiver),
                index = translateCasted(index)
            )
        }
        nodeFor<MemberAccessExprContext> {
            MemberAccessExpression(
                receiver = translateCasted(expression()),
                ref = ReferenceByName((simpleName() ?: STRING_LITERAL()).text)
            )
        }
        nodeFor<IntRangeExprContext> {
            IntRangeExpression(
                from = translateCasted(from),
                to = translateCasted(to)
            )
        }
        nodeFor<UnaryExprContext> {
            UnaryExpression(
                operator = UnaryOperator.fromString(op.text),
                expression = translateCasted(expression())
            )
        }
        nodeFor<BinaryExprContext> {
            BinaryExpression(
                left = translateCasted(left),
                operator = BinaryOperator.fromString(op.text),
                right = translateCasted(right)
            )
        }
        nodeFor<TernaryExprContext> {
            TernaryExpression(
                condition = translateCasted(condition),
                truePart = translateCasted(truePart),
                falsePart = translateCasted(falsePart)
            )
        }
        nodeFor<AssignmentExprContext> {
            BinaryExpression(
                left = translateCasted(left),
                operator = BinaryOperator.fromString(op.text),
                right = translateCasted(right)
            )
        }
        //endregion

        //region Type Literal
        nodeFor<ReferenceTypeContext> {
            ReferenceTypeLiteral(
                typeName = qualifiedName().text
            )
        }
        nodeFor<ArrayTypeContext> {
            ArrayTypeLiteral(
                baseType = translateCasted(typeLiteral())
            )
        }
        nodeFor<ListTypeContext> {
            ListTypeLiteral(
                baseType = translateCasted(typeLiteral())
            )
        }
        nodeFor<MapTypeContext> {
            MapTypeLiteral(
                keyType = translateCasted(keyType),
                valueType = translateCasted(valueType)
            )
        }
        nodeFor<FunctionTypeContext> {
            FunctionTypeLiteral(
                parameterTypes = translateList(parameterTypes),
                returnType = translateCasted(returnType)
            )
        }
        nodeFor<PrimitiveTypeContext> {
            PrimitiveTypeLiteral(
                typeName = text
            )
        }
        //endregion
    }
}

private fun Token?.asDeclaringKind(): DeclaringKind = when (this) {
    null -> DeclaringKind.NONE
    else -> when (this.type) {
        ZenScriptLexer.VAR -> DeclaringKind.VAR
        ZenScriptLexer.VAL -> DeclaringKind.VAL
        ZenScriptLexer.STATIC -> DeclaringKind.STATIC
        ZenScriptLexer.GLOBAL -> DeclaringKind.GLOBAL
        else -> throw NoSuchElementException(
            "Unknown declaring kind for token: $this." +
                    " Allowed values: ${DeclaringKind.entries.joinToString()}"
        )
    }
}
