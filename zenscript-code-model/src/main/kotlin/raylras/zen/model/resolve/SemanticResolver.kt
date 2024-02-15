package raylras.zen.model.resolve

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.SemanticEntity
import raylras.zen.model.Visitor
import raylras.zen.model.brackets.BracketHandlers
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.*
import raylras.zen.model.type.*

fun resolveSemantics(tree: ParseTree?, unit: CompilationUnit): Sequence<SemanticEntity> {
    return tree?.accept(SemanticVisitor(unit)).orEmpty()
}

inline fun <reified T : Symbol> resolveSymbols(tree: ParseTree?, unit: CompilationUnit): Sequence<T> {
    return resolveSemantics(tree, unit).filterIsInstance<T>()
}

inline fun <reified T : Type> resolveTypes(tree: ParseTree?, unit: CompilationUnit): Sequence<T> {
    return resolveSemantics(tree, unit).map {
        when (it) {
            is Type -> it
            is Symbol -> it.type
            else -> null
        }
    }.filterIsInstance<T>()
}

inline fun <reified T : Type> resolveType(tree: ParseTree?, unit: CompilationUnit): T? {
    return resolveTypes<T>(tree, unit).firstOrNull()
}

private class SemanticVisitor(val unit: CompilationUnit) : Visitor<Sequence<SemanticEntity>>() {
    private fun visitSemantics(ctx: ParseTree?): Sequence<SemanticEntity> {
        return ctx?.accept(this).orEmpty()
    }

    private inline fun <reified T : Symbol> visitSymbols(ctx: ParseTree?): Sequence<T> {
        return visitSemantics(ctx).filterIsInstance<T>()
    }

    private fun visitTypes(ctx: ParseTree?): Sequence<Type> {
        return visitSemantics(ctx)
            .map {
                when (it) {
                    is Type -> it
                    is Symbol -> it.type
                    else -> null
                }
            }
            .filterIsInstance<Type>()
    }

    override fun visitImportDeclaration(ctx: ImportDeclarationContext): Sequence<Symbol> {
        return unit.env.units.flatMap { it.lookupSymbols(ctx.qualifiedName().text) }
    }

    override fun visitQualifiedName(ctx: QualifiedNameContext): Sequence<SemanticEntity> {
        return lookupSymbols(ctx, ctx.text, unit)
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext): Sequence<Symbol> {
        return lookupSymbols(ctx, ctx.simpleName().text, unit)
    }

    override fun visitClassDeclaration(ctx: ClassDeclarationContext): Sequence<ClassSymbol> {
        return (unit.symbolMap[ctx] as? ClassSymbol)?.let { sequenceOf(it) }.orEmpty()
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext): Sequence<SemanticEntity> {
        val simpleName = ctx.simpleName()?.text
        return visitSemantics(ctx.expression())
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
        return visitTypes(ctx.expression())
    }

    override fun visitTypeCastExpr(ctx: TypeCastExprContext): Sequence<Type> {
        return visitTypes(ctx.typeLiteral())
    }

    override fun visitAssignmentExpr(ctx: AssignmentExprContext): Sequence<Type> {
        return visitTypes(ctx.left)
    }

    override fun visitBinaryExpr(ctx: BinaryExprContext): Sequence<Type> {
        val leftType = visitTypes(ctx.left).firstOrNull()
        val rightType = visitTypes(ctx.right).firstOrNull()
        val op = Operator.of(ctx.op.text, Operator.Kind.BINARY)
        return leftType?.applyBinaryOperator(op, rightType, unit.env)?.let { sequenceOf(it) }.orEmpty()
    }

    override fun visitFunctionExpr(ctx: FunctionExprContext): Sequence<Type> {
        when {
            ctx.typeLiteral() != null -> {
                return visitTypes(ctx.typeLiteral())
            }

            ctx.parent is ArgumentContext && ctx.parent.parent is CallExprContext -> {
                val callExprCtx = ctx.parent.parent as CallExprContext
                return visitTypes(callExprCtx.caller)
                    .filterIsInstance<FunctionType>()
                    .map {
                        val index = callExprCtx.argument().indexOf(ctx.parent)
                        it.parameterTypes.getOrNull(index)
                    }
                    .map {
                        if (it is ClassType && it.isFunctionalInterface()) {
                            it.firstAnonymousFunctionOrNull()?.type
                        } else {
                            it
                        }
                    }
                    .filterNotNull()
            }

            ctx.parent is AssignmentExprContext -> {
                val assignExpr = ctx.parent as AssignmentExprContext
                return visitTypes(assignExpr.left).map {
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
        return visitTypes(ctx.expression())
    }

    override fun visitTernaryExpr(ctx: TernaryExprContext): Sequence<Type> {
        return visitTypes(ctx.truePart) + visitTypes(ctx.falsePart)
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
        val firstElementType = visitTypes(firstElement).firstOrNull() ?: AnyType
        return sequenceOf(ArrayType(firstElementType))
    }

    override fun visitMapLiteralExpr(ctx: MapLiteralExprContext): Sequence<Type> {
        val firstEntry = ctx.mapEntry().firstOrNull()
        val keyType = visitTypes(firstEntry?.key).firstOrNull() ?: AnyType
        val valueType = visitTypes(firstEntry?.value).firstOrNull() ?: AnyType
        return sequenceOf(MapType(keyType, valueType))
    }

    override fun visitIntRangeExpr(ctx: IntRangeExprContext): Sequence<Type> {
        return sequenceOf(IntRangeType)
    }

    override fun visitCallExpr(ctx: CallExprContext): Sequence<Type> {
        // FIXME: overloaded functions
        val leftType = visitTypes(ctx.expression()).filterIsInstance<FunctionType>().firstOrNull()
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
        return visitSymbols<Symbol>(ctx.qualifiedName()).map { it.type }
    }

    override fun visitListType(ctx: ListTypeContext): Sequence<Type> {
        val elementType = visitTypes(ctx.typeLiteral()).firstOrNull() ?: AnyType
        return sequenceOf(ListType(elementType))
    }

    override fun visitFunctionType(ctx: FunctionTypeContext): Sequence<Type> {
        val paramTypes: List<Type> = ctx.typeLiteral().map { visitTypes(it).firstOrNull() ?: AnyType }
        val returnType = visitTypes(ctx.returnType()).firstOrNull() ?: AnyType
        return sequenceOf(FunctionType(returnType, paramTypes))
    }

    override fun visitReturnType(ctx: ReturnTypeContext): Sequence<SemanticEntity> {
        return visitTypes(ctx.typeLiteral())
    }

    override fun visitMapType(ctx: MapTypeContext): Sequence<Type> {
        val keyType = visitTypes(ctx.key).firstOrNull() ?: AnyType
        val valueType = visitTypes(ctx.value).firstOrNull() ?: AnyType
        return sequenceOf(MapType(keyType, valueType))
    }

    override fun visitIntersectionType(ctx: IntersectionTypeContext): Sequence<Type> {
        val types = ctx.typeLiteral().map { visitTypes(it).firstOrNull() ?: AnyType }
        return sequenceOf(IntersectionType(types))
    }

    override fun visitArrayType(ctx: ArrayTypeContext): Sequence<Type> {
        val elementType = visitTypes(ctx.typeLiteral()).firstOrNull() ?: AnyType
        return sequenceOf(ArrayType(elementType))
    }

    override fun visitErrorType(ctx: ErrorTypeContext?): Sequence<SemanticEntity> {
        return sequenceOf(ErrorType)
    }

    override fun visitMemberIndexExpr(ctx: MemberIndexExprContext): Sequence<Type> {
        val leftType = visitTypes(ctx.left).firstOrNull()
        val rightType = visitTypes(ctx.index).firstOrNull()
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
