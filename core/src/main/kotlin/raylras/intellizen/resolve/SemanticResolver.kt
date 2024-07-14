package raylras.intellizen.resolve

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import raylras.intellizen.*
import raylras.intellizen.brackets.BracketHandlers
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.symbol.*
import raylras.intellizen.type.*

fun resolveSemantics(tree: ParseTree?, unit: CompilationUnit): Sequence<SemanticEntity> {
    return tree?.accept(SemanticVisitor(unit)).orEmpty()
}

fun resolveSymbol(tree: ParseTree?, unit: CompilationUnit): Symbol? {
    resolveSemantics(tree, unit).mapToSymbol().iterator().let { iter ->
        if (iter.hasNext()) {
            val single = iter.next()
            if (iter.hasNext().not()) {
                return single
            }
        }
        return null
    }
}

fun resolveType(tree: ParseTree?, unit: CompilationUnit): Type? {
    resolveSemantics(tree, unit).mapToType().iterator().let { iter ->
        if (iter.hasNext()) {
            val single = iter.next()
            if (iter.hasNext().not()) {
                return single
            }
        }
        return null
    }
}

private class SemanticVisitor(val unit: CompilationUnit) : Visitor<Sequence<SemanticEntity>>() {
    override fun visit(ctx: ParseTree?): Sequence<SemanticEntity> {
        return ctx?.accept(this).orEmpty()
    }

    override fun visitQualifiedName(ctx: QualifiedNameContext): Sequence<SemanticEntity> {
        return lookupSymbols(ctx, ctx.text, unit)
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext): Sequence<Symbol> {
        return lookupSymbols(ctx, ctx.simpleName().text, unit)
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext): Sequence<SemanticEntity> {
        val simpleName = ctx.simpleName()?.text
        return visit(ctx.expression())
            .flatMap { expr ->
                when {
                    expr is SymbolProvider -> {
                        expr.getSymbols(unit.env)
                    }

                    expr is Symbol && expr.type is SymbolProvider -> {
                        (expr.type as SymbolProvider).getSymbols(unit.env)
                    }

                    else -> emptySequence()
                }
            }
            .filter { it.simpleName == simpleName }
    }

    override fun visitThisExpr(ctx: ThisExprContext): Sequence<Symbol> {
        return lookupSymbols(ctx, "this", unit)
    }

    override fun visitParensExpr(ctx: ParensExprContext): Sequence<Type> {
        return visit(ctx.expression()).mapToType()
    }

    override fun visitTypeCastExpr(ctx: TypeCastExprContext): Sequence<Type> {
        return visit(ctx.typeLiteral()).mapToType()
    }

    override fun visitAssignmentExpr(ctx: AssignmentExprContext): Sequence<Type> {
        return visit(ctx.left).mapToType()
    }

    override fun visitBinaryExpr(ctx: BinaryExprContext): Sequence<Type> {
        val leftType = visit(ctx.left).mapToType().firstOrNull()
        val rightType = visit(ctx.right).mapToType().firstOrNull()
        val op = Operator.of(ctx.op.text, Operator.Kind.BINARY)
        return leftType?.applyBinaryOperator(op, rightType, unit.env)?.let { sequenceOf(it) }.orEmpty()
    }

    override fun visitFunctionExpr(ctx: FunctionExprContext): Sequence<Type> {
        when {
            ctx.parent is ArgumentContext && ctx.parent.parent is CallExprContext -> {
                val argCtx = ctx.parent as ArgumentContext
                val callCtx = ctx.parent.parent as CallExprContext
                return visit(callCtx.callee)
                    .mapToType()
                    .filterIsInstance<FunctionType>()
                    .map { caller ->
                        val index = callCtx.argument().indexOf(argCtx)
                        caller.parameterTypes.getOrNull(index)
                    }
                    .map { param ->
                        if (param is ClassType && param.isFunctionalInterface()) {
                            param.firstAnonymousFunctionOrNull()?.type
                        } else {
                            param
                        }
                    }
                    .filterNotNull()
            }

            ctx.parent is AssignmentExprContext -> {
                val assignCtx = ctx.parent as AssignmentExprContext
                return visit(assignCtx.left).mapToType().map {
                    if (it is ClassType && it.isFunctionalInterface()) {
                        it.firstAnonymousFunctionOrNull()?.type
                    } else {
                        it
                    }
                }.filterIsInstance<FunctionType>()
            }

            else -> return emptySequence()
        }
    }

    override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext): Sequence<Type> {
        val typeName = BracketHandlers.getTypeNameLocal(ctx.raw().text, unit.env) ?: return sequenceOf(AnyType)
        val classType = unit.env.lookupClass(typeName)?.type
        return sequenceOf(classType ?: AnyType)
    }

    override fun visitUnaryExpr(ctx: UnaryExprContext): Sequence<Type> {
        return visit(ctx.expression()).mapToType()
    }

    override fun visitTernaryExpr(ctx: TernaryExprContext): Sequence<Type> {
        return visit(ctx.truePart).mapToType() + visit(ctx.falsePart).mapToType()
    }

    override fun visitLiteralExpr(ctx: LiteralExprContext): Sequence<Type> {
        return when (ctx.literal.type) {
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
            else -> ErrorType
        }.let { sequenceOf(it) }
    }

    override fun visitArrayLiteralExpr(ctx: ArrayLiteralExprContext): Sequence<Type> {
        val firstElement = ctx.expression().firstOrNull()
        val firstElementType = visit(firstElement).mapToType().firstOrNull() ?: AnyType
        return sequenceOf(ArrayType(firstElementType))
    }

    override fun visitMapLiteralExpr(ctx: MapLiteralExprContext): Sequence<Type> {
        val firstEntry = ctx.mapEntry().firstOrNull()
        val keyType = visit(firstEntry?.key).mapToType().firstOrNull() ?: AnyType
        val valueType = visit(firstEntry?.value).mapToType().firstOrNull() ?: AnyType
        return sequenceOf(MapType(keyType, valueType))
    }

    override fun visitIntRangeExpr(ctx: IntRangeExprContext): Sequence<Type> {
        return sequenceOf(IntRangeType)
    }

    override fun visitCallExpr(ctx: CallExprContext): Sequence<Type> {
        // FIXME: overloaded functions
        val leftType = visit(ctx.expression()).mapToType().filterIsInstance<FunctionType>().firstOrNull()
        return leftType?.let { sequenceOf(it.returnType) } ?: sequenceOf(AnyType)
    }

    override fun visitPrimitiveType(ctx: PrimitiveTypeContext): Sequence<Type> {
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
        }.let { sequenceOf(it) }
    }

    override fun visitClassType(ctx: ClassTypeContext): Sequence<Type> {
        return visit(ctx.qualifiedName()).map {
            when (it) {
                is ImportSymbol -> unit.env.lookupClass(it.qualifiedName)?.type
                is ClassSymbol -> it.type
                else -> null
            }
        }.filterNotNull()
    }

    override fun visitListType(ctx: ListTypeContext): Sequence<Type> {
        val elementType = visit(ctx.typeLiteral()).mapToType().firstOrNull() ?: AnyType
        return sequenceOf(ListType(elementType))
    }

    override fun visitFunctionType(ctx: FunctionTypeContext): Sequence<Type> {
        val paramTypes: List<Type> = ctx.typeLiteral().map { visit(it).mapToType().firstOrNull() ?: AnyType }
        val returnType = visit(ctx.returnType()).mapToType().firstOrNull() ?: AnyType
        return sequenceOf(FunctionType(returnType, paramTypes))
    }

    override fun visitReturnType(ctx: ReturnTypeContext): Sequence<SemanticEntity> {
        return visit(ctx.typeLiteral()).mapToType()
    }

    override fun visitMapType(ctx: MapTypeContext): Sequence<Type> {
        val keyType = visit(ctx.key).mapToType().firstOrNull() ?: AnyType
        val valueType = visit(ctx.value).mapToType().firstOrNull() ?: AnyType
        return sequenceOf(MapType(keyType, valueType))
    }

    override fun visitIntersectionType(ctx: IntersectionTypeContext): Sequence<Type> {
        val types = ctx.typeLiteral().map { visit(it).mapToType().firstOrNull() ?: AnyType }
        return sequenceOf(IntersectionType(types))
    }

    override fun visitArrayType(ctx: ArrayTypeContext): Sequence<Type> {
        val elementType = visit(ctx.typeLiteral()).mapToType().firstOrNull() ?: AnyType
        return sequenceOf(ArrayType(elementType))
    }

    override fun visitErrorType(ctx: ErrorTypeContext?): Sequence<SemanticEntity> {
        return sequenceOf(ErrorType)
    }

    override fun visitMemberIndexExpr(ctx: MemberIndexExprContext): Sequence<Type> {
        val leftType = visit(ctx.left).mapToType().firstOrNull()
        val rightType = visit(ctx.index).mapToType().firstOrNull()
        return leftType?.applyBinaryOperator(Operator.INDEX_GET, rightType, unit.env)
            ?.let { sequenceOf(it) }.orEmpty()
    }

    override fun visitChildren(node: RuleNode): Sequence<SemanticEntity> {
        return emptySequence()
    }
}

private fun lookupSymbols(cst: ParseTree, name: String, unit: CompilationUnit): Sequence<Symbol> {
    // is a qualified name
    if (name.contains('.')) {
        return unit.env.units.flatMap { it.lookupSymbols(name) }
    }

    // is a simple name
    lookupLocalSymbols(cst, name, unit).let {
        if (it.iterator().hasNext()) return it
    }

    lookupStaticSymbols(name, unit).let {
        if (it.iterator().hasNext()) return it
    }

    lookupImportSymbols(name, unit).let {
        if (it.iterator().hasNext()) return it
    }

    unit.env.lookupGlobal(name)?.let {
        return sequenceOf(it)
    }

    lookupPackageSymbols(name, unit.env).let {
        if (it.iterator().hasNext()) return it
    }

    return emptySequence()
}

private fun lookupLocalSymbols(cst: ParseTree, name: String, unit: CompilationUnit): Sequence<Symbol> {
    return lookupScope(cst, unit)
        ?.filter { it !is ImportSymbol }
        ?.filter { it.simpleName == name }
        .orEmpty()
}

private fun lookupStaticSymbols(name: String, unit: CompilationUnit): Sequence<Symbol> {
    return unit.staticSymbolMap[name]?.asSequence().orEmpty()
}

private fun lookupImportSymbols(simpleName: String, unit: CompilationUnit): Sequence<Symbol> {
    val importSymbol = unit.importMap[simpleName]?.firstOrNull()
    importSymbol?.getSymbols(unit.env)?.let { targets ->
        return if (targets.iterator().hasNext()) {
            targets
        } else {
            sequenceOf(importSymbol)
        }
    }
        ?: return emptySequence()
}

private fun lookupPackageSymbols(name: String, env: CompilationEnvironment): Sequence<PackageSymbol> {
    return env.rootPackage.subpackages.filter { it.simpleName == name }
}
