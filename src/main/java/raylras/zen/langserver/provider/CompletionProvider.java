package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.ExpressionContext;
import raylras.zen.code.parser.ZenScriptParser.MemberAccessExprContext;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Kind;
import raylras.zen.code.type.Type;
import raylras.zen.l10n.L10N;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CompletionProvider {

    private static final String[] KEYWORDS = makeKeywords();

    public static List<CompletionItem> completion(CompilationUnit unit, CompletionParams params) {
        List<CompletionItem> data = new ArrayList<>();
        ParseTree node = getNodeAtPosition(unit.parseTree, params.getPosition());
        String toBeCompleted = node.getText();

        // matches member access
        if (node.getParent() instanceof MemberAccessExprContext) {
            ExpressionContext left = ((MemberAccessExprContext) node.getParent()).Left;
            Type type = new TypeResolver(unit).visitExpression(left);
            Symbol symbol = type.lookupSymbol();
            if (symbol != null) {
                for (Symbol member : symbol.getMembers()) {
                    if (!member.getName().startsWith(toBeCompleted.replace(".", "")))
                        continue;
                    CompletionItem item = new CompletionItem(member.getName());
                    item.setDetail(member.getType().toString());
                    item.setKind(getCompletionItemKind(member.getKind()));
                    data.add(item);
                }
            }
            // when completing member access
            // it's stupid to match the following things
            // just returns it now
            return data;
        }

        // matches local variables
        Scope scope = unit.lookupScope(node);
        while (scope != null) {
            for (Symbol symbol : scope.getSymbols()) {
                if (!symbol.getName().startsWith(toBeCompleted))
                    continue;
                CompletionItem item = new CompletionItem(symbol.getName());
                item.setDetail(symbol.getType().toString());
                item.setKind(getCompletionItemKind(symbol.getKind()));
                data.add(item);
            }
            scope = scope.getParent();
        }

        // matches global variables
        for (CompilationUnit cu : unit.context.getCompilationUnits()) {
            for (Symbol symbol : cu.getTopLevelSymbols()) {
                if (!symbol.isDeclaredBy(Declarator.GLOBAL) || !symbol.getName().startsWith(toBeCompleted))
                    continue;
                CompletionItem item = new CompletionItem(symbol.getName());
                item.setDetail(symbol.getType().toString());
                item.setKind(getCompletionItemKind(symbol.getKind()));
                data.add(item);
            }
        }

        // matches keywords
        for (String keyword : KEYWORDS) {
            if (keyword.startsWith(toBeCompleted)) {
                CompletionItem item = new CompletionItem(keyword);
                item.setKind(CompletionItemKind.Keyword);
                item.setDetail(L10N.getString("l10n.keyword"));
                data.add(item);
            }
        }

        return data;
    }

    private static String[] makeKeywords() {
        try {
            Pattern pattern = Pattern.compile("^[a-zA-Z].*");
            Method method = ZenScriptLexer.class.getDeclaredMethod("makeLiteralNames");
            method.setAccessible(true);
            String[] literalNames = (String[]) method.invoke(null);
            List<String> keywordList = Arrays.stream(literalNames)
                    .filter(Objects::nonNull)
                    .map(literal -> literal.replaceAll("'", ""))
                    .filter(literal -> pattern.matcher(literal).matches())
                    .collect(Collectors.toList());
            return keywordList.toArray(new String[]{});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    private static ParseTree getNodeAtPosition(ParseTree parseTree, Position position) {
        Range range = Ranges.from(position);
        return Nodes.getNodeAtPosition(parseTree, range.startLine, range.startColumn);
    }

    private static CompletionItemKind getCompletionItemKind(Kind kind) {
        switch (kind) {
            case FUNCTION:
                return CompletionItemKind.Function;
            case BOOL:
            case NUMBER:
            case STRING:
                return CompletionItemKind.Constant;
            case CLASS:
            case PACKAGE:
                return CompletionItemKind.Class;
            case VARIABLE:
            case ANY:
            case NONE:
            case MAP:
            case LIST:
            case ARRAY:
            case VOID:
                return CompletionItemKind.Variable;
            default:
                return null;
        }
    }

}
