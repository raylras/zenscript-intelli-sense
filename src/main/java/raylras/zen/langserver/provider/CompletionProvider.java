package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.resolve.ExpressionSymbolResolver;
import raylras.zen.code.resolve.ExpressionTypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;
import raylras.zen.l10n.L10N;
import raylras.zen.langserver.provider.data.CompletionContext;
import raylras.zen.langserver.provider.data.CompletionContextResolver;
import raylras.zen.langserver.provider.data.Keywords;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.util.ArrayList;
import java.util.List;

public class CompletionProvider {

    private final CompilationUnit unit;
    private final CompletionContext context;
    private final List<CompletionItem> data = new ArrayList<>();
    public CompletionProvider(CompilationUnit unit, CompletionContext context) {
        this.unit = unit;
        this.context = context;
    }

    public static CompletionList completion(CompilationUnit unit, CompletionParams params) {
        Range cursor = Ranges.from(params.getPosition());
        CompletionContext context = new CompletionContextResolver(unit, cursor).resolve();
        CompletionProvider provider = new CompletionProvider(unit, context);
        provider.complete();
        return new CompletionList(provider.data);
    }

    private void complete() {
        switch (context.kind) {
            case IMPORT:
                completeImport();
                break;

            case LOCAL_STATEMENT:
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.LOCAL_STATEMENT);
                break;

            case TOPLEVEL_STATEMENT:
                completeKeywords(Keywords.TOPLEVEL_STATEMENT);

            case LOCAL_ACCESS:
                completeLocalSymbols();
                completeGlobalSymbols();

            case MEMBER_ACCESS:
                // completeStaticMembers();
                // completeInstanceMembers();
                break;

            case CLASS_BODY:
                completeKeywords(Keywords.CLASS_BODY);
                break;

            case NONE:
            default:
        }
    }

    private void completeImport() {
        // TODO
    }

    private void completeLocalSymbols() {
        Scope scope = unit.lookupScope(context.completingNode);
        if (scope == null)
            return;
        for (Symbol symbol : scope.getSymbols()) {
            if (symbol.getDeclaredName().startsWith(context.completingString)) {
                CompletionItem item = new CompletionItem(symbol.getDeclaredName());
                item.setDetail(symbol.getType().toString());
                item.setKind(getCompletionItemKind(symbol.getKind()));
                data.add(item);
            }
        }
    }

    private void completeGlobalSymbols() {
        for (Symbol member : unit.getEnv().getGlobalSymbols()) {
            if (member.getDeclaredName().startsWith(context.completingString)) {
                CompletionItem item = new CompletionItem(member.getDeclaredName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeSymbolMembers(Symbol target) {
        switch (target.getKind()) {
            case CLASS:
                completeStaticMembers(target.getType().lookupSymbol(unit));
                break;
            case VARIABLE:
            default:
                completeInstanceMembers(target.getType().lookupSymbol(unit));
                break;
        }
    }

    private void completeStaticMembers(Symbol target) {
        if (target == null)
            return;
        for (Symbol member : target.getMembers()) {
            if (!member.isDeclaredBy(Declarator.STATIC))
                continue;
            if (member.getDeclaredName().startsWith(context.completingString)) {
                CompletionItem item = new CompletionItem(member.getDeclaredName());
                item.setDetail("static " + member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeInstanceMembers(Symbol target) {
        if (target == null)
            return;
        for (Symbol member : target.getMembers()) {
            if (member.isDeclaredBy(Declarator.STATIC))
                continue;
            if (member.getDeclaredName().startsWith(context.completingString)) {
                CompletionItem item = new CompletionItem(member.getDeclaredName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeKeywords(String[] keywords) {
        for (String keyword : keywords) {
            if (keyword.startsWith(context.completingString)) {
                CompletionItem item = new CompletionItem(keyword);
                item.setKind(CompletionItemKind.Keyword);
                item.setDetail(L10N.getString("l10n.keyword"));
                data.add(item);
            }
        }
    }

    private Type getTypeOfNode(ParseTree node) {
        return new ExpressionTypeResolver(unit).resolve(node);
    }

    private Symbol getSymbolOfNode(ParseTree node) {
        return new ExpressionSymbolResolver(unit).resolve(node);
    }

    private CompletionItemKind getCompletionItemKind(Symbol.Kind kind) {
        switch (kind) {
            case FUNCTION:
                return CompletionItemKind.Function;
            case CLASS:
                return CompletionItemKind.Class;
            case VARIABLE:
            case NONE:
                return CompletionItemKind.Variable;
            default:
                return null;
        }
    }

}
