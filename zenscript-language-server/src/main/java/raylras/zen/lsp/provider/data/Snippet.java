package raylras.zen.lsp.provider.data;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.parser.ZenScriptParser.MemberAccessExprContext;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.type.*;
import raylras.zen.util.Operators;
import raylras.zen.util.Ranges;

@FunctionalInterface
public interface Snippet {

    CompletionItem get();

    static Snippet dotFor(Type type, CompilationEnvironment env, MemberAccessExprContext ctx) {
        Type iteratorType = Operators.getUnaryResult(type, Operator.FOR_IN, env).orElse(null);
        if (iteratorType instanceof MapType) {
            return () -> createMemberAccessCompletion("for", "for key, value in map", "for ${1:key}, ${2:value} in %s {\n\t$0\n}", ctx);
        } else if (iteratorType instanceof ListType) {
            return () -> createMemberAccessCompletion("for", "for element in list", "for ${1:value} in %s {\n\t$0\n}", ctx);
        } else {
            return () -> null;
        }
    }

    static Snippet dotForI(Type type, CompilationEnvironment env, MemberAccessExprContext ctx) {
        Type iteratorType = Operators.getUnaryResult(type, Operator.FOR_IN, env).orElse(null);
        if (iteratorType instanceof ListType) {
            return () -> createMemberAccessCompletion("fori", "for index, element in list", "for ${1:i}, ${2:value} in %s {\n\t$0\n}", ctx);
        } else {
            return () -> null;
        }
    }

    static Snippet dotVal(MemberAccessExprContext ctx) {
        return () -> createMemberAccessCompletion("val", "val name = expr", "val ${1:value} = %s;", ctx);
    }

    static Snippet dotVar(MemberAccessExprContext ctx) {
        return () -> createMemberAccessCompletion("var", "var name = expr", "var ${1:value} = %s;", ctx);
    }

    static Snippet dotIfNull(Type type, MemberAccessExprContext ctx) {
        if (type instanceof NumberType || type == BoolType.INSTANCE || type == VoidType.INSTANCE) {
            return () -> null;
        } else {
            return () -> createMemberAccessCompletion("null", "if (isNull(expr))", "if (isNull(%s)) {\n\t$0\n}", ctx);
        }
    }

    static Snippet dotIfNotNull(Type type, MemberAccessExprContext ctx) {
        if (type instanceof NumberType || type == BoolType.INSTANCE || type == VoidType.INSTANCE) {
            return () -> null;
        } else {
            return () -> createMemberAccessCompletion("nn", "if (!isNull(expr))", "if (!isNull(%s)) {\n\t$0\n}", ctx);
        }
    }

    private static CompletionItem createMemberAccessCompletion(String name, String description, String snippet, MemberAccessExprContext ctx) {
        CompletionItem item = new CompletionItem(name);
        item.setInsertTextMode(InsertTextMode.AdjustIndentation);
        item.setInsertTextFormat(InsertTextFormat.Snippet);
        CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
        labelDetails.setDescription(description);
        item.setLabelDetails(labelDetails);
        TextEdit textEdit = new TextEdit(Ranges.toLspRange(ctx), snippet.formatted(ctx.expression().getText()));
        item.setTextEdit(Either.forLeft(textEdit));
        item.setSortText(name);
        item.setFilterText(ctx.expression().getText() + "." + name);
        return item;
    }

}
