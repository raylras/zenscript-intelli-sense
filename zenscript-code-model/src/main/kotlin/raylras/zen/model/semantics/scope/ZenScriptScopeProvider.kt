package raylras.zen.model.semantics.scope

import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Statement
import com.strumenta.kolasu.model.previousSibling
import com.strumenta.kolasu.semantics.scope.provider.declarative.DeclarativeScopeProvider
import com.strumenta.kolasu.semantics.scope.provider.declarative.scopeFor
import com.strumenta.kolasu.traversing.findAncestorOfType
import raylras.zen.model.ast.ClassDeclaration
import raylras.zen.model.ast.CompilationUnit
import raylras.zen.model.ast.ExpandFunctionDeclaration
import raylras.zen.model.ast.FunctionDeclaration
import raylras.zen.model.ast.expr.ReferenceExpression

object ZenScriptScopeProvider : DeclarativeScopeProvider(
    scopeFor(ReferenceExpression::ref) {
        it.node.findAncestorOfType(Statement::class.java)
            ?.let { statement ->
                statement.previousStatements()
                    .filterIsInstance<Named>()
                    .forEach { define(it) }
            }
        it.node.findAncestorOfType(FunctionDeclaration::class.java)
            ?.let { functionDeclaration ->
                define(functionDeclaration)
                functionDeclaration.parameters.forEach { define(it) }
            }
        it.node.findAncestorOfType(ExpandFunctionDeclaration::class.java)
            ?.let { expandFunctionDeclaration ->
                define(expandFunctionDeclaration)
                expandFunctionDeclaration.parameters.forEach { define(it) }
            }
        it.node.findAncestorOfType(ClassDeclaration::class.java)
            ?.let { classDeclaration ->
                classDeclaration.declaredFields.forEach { define(it) }
                classDeclaration.declaredMethods.forEach { define(it) }
            }
        it.node.findAncestorOfType(CompilationUnit::class.java)
            ?.let { compilationUnit ->
                compilationUnit.toplevelFunctions.forEach { define(it) }
                compilationUnit.toplevelClasses.forEach { define(it) }
            }
    }
)

internal fun Statement.previousStatements(): Sequence<Statement> = (this as? Node)
    ?.previousNodes()
    ?.filterIsInstance<Statement>()
    .orEmpty()

internal fun Node.previousNodes(): Sequence<Node> = generateSequence(this) { it.previousSibling }
