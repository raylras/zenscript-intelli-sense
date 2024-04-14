package raylras.zen.model.semantic.scope.provider

import com.strumenta.kolasu.model.Named
import com.strumenta.kolasu.model.Statement
import com.strumenta.kolasu.semantics.scope.provider.declarative.DeclarativeScopeProvider
import com.strumenta.kolasu.semantics.scope.provider.declarative.scopeFor
import raylras.zen.model.ast.*
import raylras.zen.model.ast.expr.MemberAccessExpression
import raylras.zen.model.ast.expr.ReferenceExpression
import raylras.zen.model.semantic.type.provider.ZenScriptTypeProvider
import raylras.zen.model.semantic.type.provider.typeOf
import raylras.zen.model.traversing.findAncestorOfType
import raylras.zen.model.traversing.previousStatements

object ZenScriptScopeProvider : DeclarativeScopeProvider(
    scopeFor(ReferenceExpression::ref) { (node, _) ->
        node.findAncestorOfType<Statement>()?.apply {
            previousStatements()
                .filterIsInstance<Named>()
                .forEach(::define)
        }
        node.findAncestorOfType<FunctionDeclaration>()?.apply {
            define(this)
            parameters.forEach(::define)
        }
        node.findAncestorOfType<ExpandFunctionDeclaration>()?.apply {
            define(this)
            parameters.forEach(::define)
        }
        node.findAncestorOfType<ClassDeclaration>()?.apply {
            declaredFields.forEach(::define)
            declaredMethods.forEach(::define)
        }
        node.findAncestorOfType<CompilationUnit>()?.apply {
            toplevelFunctions.forEach(::define)
            toplevelClasses.forEach(::define)
            toplevelImports.forEach(::define)
        }
    },
    scopeFor(ClassReference::ref) { (node, _) ->
        node.findAncestorOfType<CompilationUnit>()?.apply {
            toplevelClasses.forEach(::define)
        }
    },
    scopeFor(MemberAccessExpression::ref) { (node, _) ->
        ZenScriptTypeProvider.typeOf(node.receiver)
            ?.members
            ?.filterIsInstance<Named>()
            ?.forEach(::define)
    }
)
