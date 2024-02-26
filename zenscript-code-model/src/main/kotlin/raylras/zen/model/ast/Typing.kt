package raylras.zen.model.ast

import com.strumenta.kolasu.model.NodeType

/**
 * An entity that can have a type annotation.
 */
@NodeType
interface PossiblyAnnotatedType {
    val typeAnnotation: TypeLiteral?
}

/**
 * An entity that has a type annotation.
 */
@NodeType
interface AnnotatedType : PossiblyAnnotatedType {
    override val typeAnnotation: TypeLiteral
}

/**
 * An entity that can have a return type annotation.
 */
@NodeType
interface PossiblyAnnotatedReturnType {
    val returnTypeAnnotation: TypeLiteral?
}

/**
 * An entity that has a return type annotation.
 */
@NodeType
interface AnnotatedReturnType : PossiblyAnnotatedReturnType {
    override val returnTypeAnnotation: TypeLiteral
}
