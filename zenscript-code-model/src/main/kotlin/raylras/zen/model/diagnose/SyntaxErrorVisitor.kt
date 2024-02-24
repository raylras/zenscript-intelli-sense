package raylras.zen.model.diagnose

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.isDzsUnit
import raylras.zen.model.parser.ZenScriptParser
import raylras.zen.util.TextRange
import raylras.zen.util.l10n.L10N
import raylras.zen.util.textRange


fun CompilationUnit.resolveSyntaxErrors() {
    this.accept(SyntaxErrorVisitor(this.diagnoseHandler, this.isDzsUnit))
}

class SyntaxErrorVisitor(private val diagnoseHandler: DiagnoseHandler, private val isDzs: Boolean) :
    Visitor<Boolean>() {

    private fun addMissingToken(range: TextRange, token: String) {

        val msg = L10N.localize("error_missing_token", token)
        diagnoseHandler.addError(msg, range)
    }

    private fun addMissingTokenAtLast(ctx: ParseTree, token: String) {
        val ctxEnd = ctx.textRange.end
        val missingRange = TextRange(ctxEnd.line, ctxEnd.column, ctxEnd.line, ctxEnd.column + 1)
        addMissingToken(missingRange, "'$token'")
    }


    private fun addExpectingRuleAtLast(ctx: ParseTree, expecting: String) {
        val ctxEnd = ctx.textRange.end
        val missingRange = TextRange(ctxEnd.line, ctxEnd.column, ctxEnd.line, ctxEnd.column + 1)
        val msg = L10N.localize("error_expecting_rule", expecting)
        diagnoseHandler.addError(msg, missingRange)
    }

    private fun addNotSupported(token: Token) {
        val msg = L10N.localize("error_not_supported_token", token.text)
        val range = token.textRange
        diagnoseHandler.addError(msg, range)
    }

    private fun addNotSupported(node: TerminalNode) {
        val msg = L10N.localize("error_not_supported_token", node.text)
        val range = node.textRange
        diagnoseHandler.addError(msg, range)
    }

    private fun addAdditionalToken(token: Token) {
        val msg = L10N.localize("error_unexpected_token", "\"${token.text}\"")
        val range = token.textRange
        diagnoseHandler.addError(msg, range)
    }


    private fun addInvalidInput(ctx: ParserRuleContext, expected: String) {
        val msg = L10N.localize("error_illegal_input_expecting", expected)
        val range = ctx.textRange
        diagnoseHandler.addError(msg, range)
    }

    override fun visitClassBody(ctx: ZenScriptParser.ClassBodyContext): Boolean {
        if (super.visitClassBody(ctx)) {
            return true
        }
        if(ctx.additionalBracket != null) {
            addAdditionalToken(ctx.additionalBracket)
        }
        return false
    }

    override fun visitInvaildStatementInClassBody(ctx: ZenScriptParser.InvaildStatementInClassBodyContext): Boolean {
        addInvalidInput(ctx, L10N.localize("error_detail_member_declaration"))
        return false
    }
    override fun visitExpressionStatement(ctx: ZenScriptParser.ExpressionStatementContext): Boolean {
        if (super.visitExpressionStatement(ctx)) {
            return true
        }
        if (ctx.tailingSemi == null) {
            addMissingTokenAtLast(ctx, ";")
        }
        return false
    }

    override fun visitImportDeclaration(ctx: ZenScriptParser.ImportDeclarationContext): Boolean {
        if (super.visitImportDeclaration(ctx)) {
            return true
        }
        if (ctx.tailingSemi == null) {
            addMissingTokenAtLast(ctx, ";")
        }
        return false
    }

    override fun visitReturnStatement(ctx: ZenScriptParser.ReturnStatementContext): Boolean {
        if (super.visitReturnStatement(ctx)) {
            return true
        }
        if (ctx.tailingSemi == null) {
            addMissingTokenAtLast(ctx, ";")
        }
        return false
    }

    override fun visitIntersectionType(ctx: ZenScriptParser.IntersectionTypeContext): Boolean {
        if (super.visitIntersectionType(ctx)) {
            return true
        }
        if (isDzs) {
            return false
        }
        for (andNode in ctx.AND()) {
            addNotSupported(andNode)
        }
        return false
    }

    override fun visitUnionType(ctx: ZenScriptParser.UnionTypeContext): Boolean {
        if (super.visitUnionType(ctx)) {
            return true
        }
        if (isDzs) {
            return false
        }
        for (orNode in ctx.OR()) {
            addNotSupported(orNode)
        }
        return false
    }

    override fun visitFunctionDeclaration(ctx: ZenScriptParser.FunctionDeclarationContext): Boolean {
        if (super.visitFunctionDeclaration(ctx)) {
            return true
        }
        if (isDzs) {
            return false
        }

        if(ctx.simpleName() == null) {
            addExpectingRuleAtLast(ctx.FUNCTION(), L10N.localize("error_detail_function_name"))
        }

        if (ctx.prefix?.type == ZenScriptParser.GLOBAL) {
            addNotSupported(ctx.prefix)
        }
        return false

    }

    override fun visitClassDeclaration(ctx: ZenScriptParser.ClassDeclarationContext): Boolean {
        if (super.visitClassDeclaration(ctx)) {
            return true
        }
        if (isDzs) {
            return false
        }
        val extendsSpecifier = ctx.extendsSpecifier()
            ?: return false

        addNotSupported(extendsSpecifier.EXTENDS())
        return false
    }

    override fun visitVariableDeclaration(ctx: ZenScriptParser.VariableDeclarationContext): Boolean {
        if(super.visitVariableDeclaration(ctx)) {
            return true
        }
        if(ctx.simpleName() == null) {
            addExpectingRuleAtLast(ctx, L10N.localize("error_detail_variable_name"))
        }
        if(ctx.tailingSemi == null) {
            addMissingTokenAtLast(ctx, ";")
        }

        return false
    }

    // if there are any error node here, do not report more errors in this node
    override fun visitErrorNode(node: ErrorNode?): Boolean {
        return true
    }

    override fun defaultResult(): Boolean {
        return false
    }


}