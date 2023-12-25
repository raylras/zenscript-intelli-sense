package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.lookupScope
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.*
import raylras.zen.model.type.*

fun getType(cst: ParseTree?, unit: CompilationUnit): Type {
    return cst?.accept(TypeVisitor(unit)) ?: ErrorType
}

private class TypeVisitor(val unit: CompilationUnit) : Visitor<Type>() {
    override fun visitImportDeclaration(ctx: ImportDeclarationContext): Type {
        return unit.symbolMap[ctx]?.type ?: AnyType
    }

    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext): Type {
        val paramTypes: List<Type> = mapToType(ctx.formalParameter())
        val returnType: Type = visit(ctx.returnType())
        return FunctionType(returnType, paramTypes)
    }

    override fun visitExpandFunctionDeclaration(ctx: ExpandFunctionDeclarationContext): Type {
        val paramTypes: List<Type> = mapToType(ctx.formalParameter())
        val returnType: Type = visit(ctx.returnType())
        return FunctionType(returnType, paramTypes)
    }

    override fun visitFormalParameter(ctx: FormalParameterContext): Type {
        when {
            ctx.typeLiteral() != null -> {
                return visit(ctx.typeLiteral())
            }

            ctx.defaultValue() != null -> {
                return visit(ctx.defaultValue())
            }

            else -> {
                val functionExpr = ctx.getParent() as? FunctionExprContext ?: return AnyType
                val index = functionExpr.formalParameter().indexOf(ctx)
                return when (val type = visit(functionExpr)) {
                    is FunctionType -> {
                        type.parameterTypes[index]
                    }

                    is ClassType -> {
                        type.firstAnonymousFunctionOrNull()
                            ?.parameters?.get(index)?.type
                            ?: AnyType
                    }

                    else -> AnyType
                }
            }
        }
    }

    override fun visitDefaultValue(ctx: DefaultValueContext): Type {
        return visit(ctx.expression())
    }

    override fun visitReturnType(ctx: ReturnTypeContext): Type {
        return visit(ctx.typeLiteral())
    }

    override fun visitClassDeclaration(ctx: ClassDeclarationContext): Type {
        return unit.symbolMap[ctx]?.type ?: AnyType
    }

    override fun visitConstructorDeclaration(ctx: ConstructorDeclarationContext): Type {
        val paramTypes: List<Type> = mapToType(ctx.formalParameter())
        val returnType: Type = when (val symbol = unit.symbolMap[ctx]) {
            is ConstructorSymbol -> symbol.declaringClass.type
            else -> AnyType
        }
        return FunctionType(returnType, paramTypes)
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext): Type {
        return when {
            ctx.typeLiteral() != null -> {
                visit(ctx.typeLiteral())
            }

            else -> {
                visit(ctx.initializer)
            }
        }
    }

    override fun visitOperatorFunctionDeclaration(ctx: OperatorFunctionDeclarationContext): Type {
        val paramTypes: List<Type> = mapToType(ctx.formalParameter())
        val returnType = visit(ctx.returnType())
        return FunctionType(returnType, paramTypes)
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext): Type {
        val statement = ctx.getParent() as? ForeachStatementContext ?: return ErrorType
        val variables = statement.foreachVariable()
        val expr = visit(statement.expression())
        return when (val type = expr.applyUnaryOperator(Operator.FOR_IN, unit.env)) {
            is ListType -> {
                when (variables.size) {
                    // for v in expr
                    1 -> type.elementType

                    // for i, v in expr
                    2 -> {
                        when (variables.indexOf(ctx)) {
                            // case i
                            0 -> IntType

                            // case v
                            1 -> type.elementType

                            else -> ErrorType
                        }
                    }

                    else -> ErrorType
                }
            }

            is MapType -> {
                when (variables.size) {
                    // for v in expr
                    1 -> type.valueType

                    // for k, v in expr
                    2 -> {
                        when (variables.indexOf(ctx)) {
                            // case k
                            0 -> type.keyType

                            // case v
                            1 -> type.valueType

                            else -> ErrorType
                        }
                    }

                    else -> ErrorType
                }
            }

            else -> AnyType
        }
    }

    override fun visitAssignmentExpr(ctx: AssignmentExprContext): Type {
        return visit(ctx.left)
    }

    override fun visitThisExpr(ctx: ThisExprContext): Type {
        return lookupLocalSymbol(ctx, "this")
            ?.type
            ?: AnyType
    }

    override fun visitMapLiteralExpr(ctx: MapLiteralExprContext): Type {
        val firstEntry = ctx.mapEntryList()?.mapEntry()?.firstOrNull()
        val keyType = firstEntry?.let { visit(it.key) } ?: AnyType
        val valueType = firstEntry?.let { visit(it.value) } ?: AnyType
        return MapType(keyType, valueType)
    }

    override fun visitIntRangeExpr(ctx: IntRangeExprContext): Type {
        val from = visit(ctx.from)
        val to = visit(ctx.to)
        return from.applyBinaryOperator(Operator.RANGE, to, unit.env) ?: AnyType
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext): Type {
        return lookupSymbol(ctx, ctx.getText())?.type ?: AnyType
    }

    override fun visitBinaryExpr(ctx: BinaryExprContext): Type {
        val leftType = visit(ctx.left)
        val rightType = visit(ctx.right)
        val op = Operator.of(ctx.op.text, Operator.Kind.BINARY)
        return leftType.applyBinaryOperator(op, rightType, unit.env) ?: leftType
    }

    override fun visitParensExpr(ctx: ParensExprContext): Type {
        return visit(ctx.expression())
    }

    override fun visitTypeCastExpr(ctx: TypeCastExprContext): Type {
        return visit(ctx.typeLiteral())
    }

    override fun visitFunctionExpr(ctx: FunctionExprContext): Type {
        return when {
            ctx.typeLiteral() != null -> {
                visit(ctx.typeLiteral())
            }

            else -> {
                // FIXME
                AnyType
            }
        }
    }

    override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext): Type {
        // FIXME
        return AnyType
    }

    override fun visitUnaryExpr(ctx: UnaryExprContext): Type {
        val type = visit(ctx.expression())
        val op = Operator.of(ctx.op.text, Operator.Kind.UNARY)
        return type.applyUnaryOperator(op, unit.env) ?: AnyType
    }

    override fun visitTernaryExpr(ctx: TernaryExprContext): Type {
        return visit(ctx.truePart)
    }

    override fun visitLiteralExpr(ctx: LiteralExprContext): Type {
        return when (ctx.literal?.type) {
            DECIMAL_LITERAL -> {
                when (ctx.literal.text.last()) {
                    'l', 'L' -> LongType
                    else -> IntType
                }
            }

            FLOAT_LITERAL -> {
                when (ctx.literal.text.last()) {
                    'f', 'F' -> FloatType
                    else -> DoubleType
                }
            }

            HEX_LITERAL -> IntType
            STRING_LITERAL -> StringType
            TRUE, FALSE -> BoolType
            NULL -> AnyType
            else -> AnyType
        }
    }

    override fun visitLogicExpr(ctx: LogicExprContext): Type {
        return visit(ctx.left)
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext): Type {
        val leftType = visit(ctx.expression())
        when {
            leftType !is SymbolProvider -> {
                return AnyType
            }

            ctx.simpleName() == null -> {
                return AnyType
            }

            else -> {
                leftType.getSymbols(unit.env)
                    .firstOrNull { it.simpleName == ctx.simpleName().text }
                    ?.let { return it.type }
                return leftType.applyBinaryOperator(Operator.MEMBER_GET, StringType, unit.env) ?: AnyType
            }
        }
    }

    override fun visitArrayLiteralExpr(ctx: ArrayLiteralExprContext): Type {
        val firstElementType: Type = visit(ctx.expressionList().expression().firstOrNull())
        return ArrayType(firstElementType)
    }

    override fun visitCallExpr(ctx: CallExprContext): Type {
        // FIXME
        return when (val caller = visit(ctx.expression())) {
            is Executable -> {
                AnyType
            }

            else -> {
                AnyType
            }
        }
    }

    override fun visitMemberIndexExpr(ctx: MemberIndexExprContext): Type {
        val leftType = visit(ctx.left)
        val rightType = visit(ctx.index)
        return leftType.applyBinaryOperator(Operator.INDEX_GET, rightType, unit.env) ?: AnyType
    }

    override fun visitCompareExpr(ctx: CompareExprContext): Type {
        val leftType = visit(ctx.left)
        val rightType = visit(ctx.right)
        val op = Operator.of(ctx.op.text, Operator.Kind.BINARY)
        return leftType.applyBinaryOperator(op, rightType, unit.env) ?: AnyType
    }

    override fun visitArrayType(ctx: ArrayTypeContext): Type {
        val elementType = visit(ctx.typeLiteral())
        return ArrayType(elementType)
    }

    override fun visitIntersectionType(ctx: IntersectionTypeContext): Type {
        val types = ctx.typeLiteral().map { visit(it) }
        return IntersectionType(types)
    }

    override fun visitMapType(ctx: MapTypeContext): Type {
        val keyType = visit(ctx.key)
        val valueType = visit(ctx.value)
        return MapType(keyType, valueType)
    }

    override fun visitFunctionType(ctx: FunctionTypeContext): Type {
        val paramTypes: List<Type> = mapToType(ctx.typeLiteral())
        val returnType = visitReturnType(ctx.returnType())
        return FunctionType(returnType, paramTypes)
    }

    override fun visitListType(ctx: ListTypeContext): Type {
        val elementType = visit(ctx.typeLiteral())
        return ListType(elementType)
    }

    override fun visitPrimitiveType(ctx: PrimitiveTypeContext): Type {
        return when (ctx.start?.type) {
            ANY -> AnyType
            BYTE -> ByteType
            SHORT -> ShortType
            INT -> IntType
            LONG -> LongType
            FLOAT -> FloatType
            DOUBLE -> DoubleType
            BOOL -> BoolType
            VOID -> VoidType
            STRING -> StringType
            else -> ErrorType
        }
    }

    override fun visitClassType(ctx: ClassTypeContext): Type {
        return lookupClass(ctx.qualifiedName(), unit)
            .firstOrNull()
            ?.type
            ?: AnyType
    }

    override fun visit(node: ParseTree?): Type {
        return node?.accept(this) ?: ErrorType
    }

    override fun visitChildren(node: RuleNode): Type? {
        return null
    }

    private fun lookupSymbol(cst: ParseTree?, simpleName: String): Symbol? {
        return lookupLocalSymbol(cst, simpleName) ?: lookupGlobalSymbol(simpleName)
    }

    private fun lookupLocalSymbol(cst: ParseTree?, simpleName: String): Symbol? {
        return lookupScope(cst, unit)?.firstOrNull { it.simpleName == simpleName }
    }

    private fun lookupGlobalSymbol(simpleName: String?): Symbol? {
        return unit.env.globals.firstOrNull { it.simpleName == simpleName }
    }

    private inline fun <reified T> mapToType(ctxList: List<ParseTree>): List<T> {
        return ctxList.map { visit(it) as T }
    }
}
