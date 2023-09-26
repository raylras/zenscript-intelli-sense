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

    Snippet NONE = () -> null;

    CompletionItem get();

    static Snippet createFor(Type type, CompilationEnvironment env, MemberAccessExprContext memberAccessExprContext) {
        Type iteratorType = Operators.getUnaryOperatorResult(type, Operator.ITERATOR, env);
        if (iteratorType instanceof MapType) {
            return () -> createMemberCompletionItem(memberAccessExprContext, "for", "for key, value in map", "for $1, $2 in %s {\n\t$0\n}");
        } else if (iteratorType instanceof ListType) {
            return () -> createMemberCompletionItem(memberAccessExprContext, "for", "for element in list", "for $1 in %s {\n\t$0\n}");
        }
        return NONE;
    }

    static Snippet createForI(Type type, CompilationEnvironment env, MemberAccessExprContext memberAccessExprContext) {
        Type iteratorType = Operators.getUnaryOperatorResult(type, Operator.ITERATOR, env);
        if (iteratorType instanceof ListType) {
            return () -> createMemberCompletionItem(memberAccessExprContext, "fori", "for index, element in list", "for ${1:i}, $2 in %s {\n\t$0\n}");
        }
        return NONE;
    }

    static Snippet createVal(MemberAccessExprContext memberAccessExprContext) {
        return () -> createMemberCompletionItem(memberAccessExprContext, "val", "val name = expr", "val $1 = %s;");
    }

    static Snippet createVar(MemberAccessExprContext memberAccessExprContext) {
        return () -> createMemberCompletionItem(memberAccessExprContext, "var", "var name = expr", "var $1 = %s;");
    }

    static Snippet createIfNull(Type type, MemberAccessExprContext memberAccessExprContext) {
        if (!(type instanceof NumberType || type == BoolType.INSTANCE || type == VoidType.INSTANCE)) {
            return () -> createMemberCompletionItem(memberAccessExprContext, "null", "if (isNull(expr))", "if (isNull(%s)) {\n\t$0\n}");
        }
        return NONE;
    }

    static Snippet createIfNotNull(Type type, MemberAccessExprContext memberAccessExprContext) {
        if (!(type instanceof NumberType || type == BoolType.INSTANCE || type == VoidType.INSTANCE)) {
            return () -> createMemberCompletionItem(memberAccessExprContext, "nn", "if (!isNull(expr))", "if (!isNull(%s)) {\n\t$0\n}");
        }
        return NONE;
    }


    private static CompletionItem createMemberCompletionItem(MemberAccessExprContext memberAccessExprContext, String name, String description, String snippet) {
        CompletionItem item = new CompletionItem(name);
        item.setInsertTextMode(InsertTextMode.AdjustIndentation);
        item.setInsertTextFormat(InsertTextFormat.Snippet);
        CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
        labelDetails.setDescription(description);
        item.setLabelDetails(labelDetails);
        TextEdit textEdit = new TextEdit(Ranges.toLspRange(memberAccessExprContext), snippet.formatted(memberAccessExprContext.expression().getText()));
        item.setTextEdit(Either.forLeft(textEdit));
        item.setSortText(name);
        item.setFilterText(memberAccessExprContext.expression().getText() + "." + name);
        return item;
    }
}
