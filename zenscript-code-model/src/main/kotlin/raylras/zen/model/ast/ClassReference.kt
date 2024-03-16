package raylras.zen.model.ast

import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.ReferenceByName

data class ClassReference(
    val className: Name,
    val ref: ReferenceByName<ClassDeclaration> = ReferenceByName(className.name)
): Node(), Named by className
