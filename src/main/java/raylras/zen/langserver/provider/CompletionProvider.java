package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.l10n.L10N;
import raylras.zen.util.Nodes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CompletionProvider {

    private static final String[] KEYWORDS = makeKeywords();

    public static List<CompletionItem> completion(CompilationUnit unit, CompletionParams params) {
        List<CompletionItem> data = new ArrayList<>();
        ParseTree node = getNodeAtPosition(unit.parseTree, params.getPosition());
        String toBeCompleted = node.getText();

        // match variables
        Scope scope = unit.lookupScope(node);
        while (scope != null) {
            scope.getSymbols().stream()
                    .filter(symbol -> symbol.getName().startsWith(toBeCompleted))
                    .forEach(symbol -> {
                        CompletionItem item = new CompletionItem(symbol.getName());
                        item.setDetail(getText(symbol.owner));
                        if (symbol instanceof FunctionSymbol) {
                            item.setKind(CompletionItemKind.Function);
                        } else if (symbol instanceof VariableSymbol) {
                            item.setKind(CompletionItemKind.Variable);
                        }
                        data.add(item);
                    });
            scope = scope.parent;
        }

        // match keywords
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
        int line = position.getLine() + 1;
        int column = position.getCharacter() - 1;
        return Nodes.getNodeAtPosition(parseTree, line, column);
    }

    private static String getText(ParseTree node) {
        if (node.getChildCount() == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < node.getChildCount(); i++) {
            builder.append(node.getChild(i).getText());
            builder.append(" ");
        }

        return builder.toString();
    }

}
