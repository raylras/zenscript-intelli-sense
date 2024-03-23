package raylras.zen.model.semantics.type

import raylras.zen.model.ast.ClassDeclaration
import raylras.zen.model.ast.FunctionDeclaration
import raylras.zen.model.ast.ParameterDeclaration
import raylras.zen.model.ast.VariableDeclaration
import raylras.zen.model.ast.expr.*
import raylras.zen.model.semantics.type.provider.declarative.DeclarativeTypeProvider
import raylras.zen.model.semantics.type.provider.declarative.typeFor
import raylras.zen.model.semantics.type.provider.declarative.typesFor
import raylras.zen.model.semantics.type.provider.instanceTypeOf
import raylras.zen.model.semantics.type.provider.typeOf

object ZenScriptTypeProvider : DeclarativeTypeProvider(
    //region Literal
    typeFor(IntLiteral::class) { IntType },
    typeFor(LongLiteral::class) { LongType },
    typeFor(FloatLiteral::class) { FloatType },
    typeFor(DoubleLiteral::class) { DoubleType },
    typeFor(BoolLiteral::class) { BoolType },
    typeFor(StringLiteral::class) { StringType },
    typeFor(ArrayLiteral::class) {
        val firstElement = it.elements.firstOrNull()
        val baseType = typeOf(firstElement) ?: AnyType
        ArrayType(baseType)
    },
    typeFor(MapLiteral::class) {
        val firstEntry = it.entries.firstOrNull()
        val keyType = typeOf(firstEntry?.key) ?: AnyType
        val valueType = typeOf(firstEntry?.value) ?: AnyType
        MapType(keyType, valueType)
    },
    //endregion

    //region Expression
    typeFor(BinaryExpression::class) {
        val left = typeOf(it.left)
        val right = typeOf(it.right)
        // TODO
        // left.applyBinaryOperator(it.operator, right)
        null
    },
    typeFor(ReferenceExpression::class) {
        typeOf(it.ref.referred)
    },
    typeFor(CallExpression::class) {
        instanceTypeOf<FunctionType>(it.receiver)?.returnType
    },
    typesFor(MemberAccessExpression::class) { expr ->
        typeOf(expr.receiver)
            ?.members
            ?.filter { it.name == expr.ref.name }
            ?.map { typeOf(it) }
    },
    //endregion

    //region Declaration
    typeFor(ClassDeclaration::class) {
        ClassType(it)
    },
    typeFor(VariableDeclaration::class) {
        typeOf(it.typeLiteral ?: it.initializer) ?: AnyType
    },
    typeFor(FunctionDeclaration::class) {
        val paramTypes = it.parameters.map { typeOf(it) ?: AnyType }
        val returnType = typeOf(it.returnTypeLiteral) ?: AnyType
        FunctionType(paramTypes, returnType)
    },
    typeFor(ParameterDeclaration::class) {
        typeOf(it.typeLiteral ?: it.defaultValue) ?: AnyType
    },
    //endregion
)
