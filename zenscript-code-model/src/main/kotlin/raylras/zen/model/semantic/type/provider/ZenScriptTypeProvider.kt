package raylras.zen.model.semantic.type.provider

import raylras.zen.model.ast.ClassDeclaration
import raylras.zen.model.ast.FunctionDeclaration
import raylras.zen.model.ast.ParameterDeclaration
import raylras.zen.model.ast.VariableDeclaration
import raylras.zen.model.ast.expr.*
import raylras.zen.model.semantic.type.*

object ZenScriptTypeProvider : DeclarativeTypeProvider() {
    init {
        //region Literal
        typeFor<IntLiteral> { IntType }
        typeFor<LongLiteral> { LongType }
        typeFor<FloatLiteral> { FloatType }
        typeFor<DoubleLiteral> { DoubleType }
        typeFor<BoolLiteral> { BoolType }
        typeFor<StringLiteral> { StringType }
        typeFor<ArrayLiteral> {
            val firstElement = elements.firstOrNull()
            val baseType = typeOf(firstElement) ?: AnyType
            ArrayType(baseType)
        }
        typeFor<MapLiteral> {
            val firstEntry = entries.firstOrNull()
            val keyType = typeOf(firstEntry?.key) ?: AnyType
            val valueType = typeOf(firstEntry?.value) ?: AnyType
            MapType(keyType, valueType)
        }
        //endregion

        //region Expression
        typeFor<BinaryExpression> {
            val leftType = typeOf(left)
            val rightType = typeOf(right)
            // TODO
            // left.applyBinaryOperator(it.operator, right)
            null
        }
        typeFor<ReferenceExpression> {
            typeOf(ref.referred)
        }
        typeFor<CallExpression> {
            reifiedTypeOf<FunctionType>(receiver)?.returnType
        }
        multipleTypeFor<MemberAccessExpression> {
            typeOf(receiver)
                ?.members
                ?.filter { it.name == ref.name }
                ?.map { typeOf(it) }
        }
        //endregion

        //region Declaration
        typeFor<ClassDeclaration> {
            ClassType(this)
        }
        typeFor<VariableDeclaration> {
            typeOf(typeLiteral ?: initializer) ?: AnyType
        }
        typeFor<FunctionDeclaration> {
            val paramTypes = parameters.map { typeOf(it) ?: AnyType }
            val returnType = typeOf(returnTypeLiteral) ?: AnyType
            FunctionType(paramTypes, returnType)
        }
        typeFor<ParameterDeclaration> {
            typeOf(typeLiteral ?: defaultValue) ?: AnyType
        }
        //endregion
    }
}
