package raylras.zen.model.semantic.type

import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.PossiblyNamed
import raylras.zen.model.ast.*

sealed interface PrimitiveType : Type
sealed interface NumberType : PrimitiveType

data object AnyType : PrimitiveType {
    override val typeName = "any"
}

data object BoolType : PrimitiveType {
    override val typeName = "bool"
}

data object ByteType : NumberType {
    override val typeName = "byte"
}

data object ShortType : NumberType {
    override val typeName = "short"
}

data object IntType : NumberType {
    override val typeName = "int"
}

data object LongType : NumberType {
    override val typeName = "long"
}

data object FloatType : NumberType {
    override val typeName = "float"
}

data object DoubleType : NumberType {
    override val typeName = "double"
}

data object StringType : PrimitiveType {
    override val typeName = "string"
}

data class ArrayType(val baseType: Type) : PrimitiveType {
    override val typeName: String
        get() = "${baseType.typeName}[]"
}

data class ListType(val baseType: Type) : PrimitiveType {
    override val typeName: String
        get() = "[${baseType.typeName}]"
}

data class MapType(val keyType: Type, val valueType: Type) : PrimitiveType {
    override val typeName: String
        get() = "${valueType.typeName}[${keyType.typeName}]"
}

data class FunctionType(val paramTypes: List<Type>, val returnType: Type) : PrimitiveType {
    override val typeName: String
        get() = paramTypes.joinToString(
            separator = ",",
            prefix = "function(",
            postfix = ")${returnType.typeName}"
        ) { param -> param.typeName }
}

data class ReferenceType(val classDecl: ClassDeclaration) : Type {
    override val typeName: String
        get() = classDecl.simpleName.name
}

data class ClassType(val classDecl: ClassDeclaration) : Type {
    override val typeName: String
        get() = "(class) ${classDecl.simpleName.name}"

    override val denotable: Boolean
        get() = false

    override val members: Iterable<Named>
        get() = classDecl.members.filter {
            when {
                it is FunctionDeclaration -> it.declaringKind == DeclaringKind.STATIC
                it is FieldDeclaration -> it.declaringKind == DeclaringKind.STATIC
                else -> false
            }
        }.filterIsInstance<Named>().asIterable()
}

data class PackageType(val pkg: String) : Type {
    override val denotable: Boolean
        get() = false

    override val typeName: String
        get() = "(package) $pkg"

    override val members: Iterable<PossiblyNamed>
        // TODO
        get() = emptyList()
}

data class CompilationUnitType(val unit: CompilationUnit) : Type {
    override val denotable: Boolean
        get() = false

    override val typeName: String
        get() = "(file) ${unit.source ?: "Unknown"}"

    override val members: Iterable<PossiblyNamed>
        get() = unit.toplevelEntities.filter {
            when (it) {
                is FunctionDeclaration -> true
                is ClassDeclaration -> true
                is VariableDeclaration -> when (it.declaringKind) {
                    DeclaringKind.STATIC -> true
                    DeclaringKind.GLOBAL -> true
                    else -> false
                }

                else -> false
            }
        }.filterIsInstance<PossiblyNamed>()
}
