package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;
import raylras.zen.l10n.L10N;
import raylras.zen.langserver.provider.data.Keywords;
import raylras.zen.util.Logger;
import raylras.zen.util.Nodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.util.*;

public class CompletionProvider extends Visitor<Void> {

    private static final Logger logger = Logger.getLogger("completion");

    private final CompilationUnit unit;
    private final Range cursor;
    private final ParseTree cursorNode;
    private final Range cursorNodeRange;
    private final String cursorString;
    private final TerminalNode prevCursorNode;
    private final Range prevCursorNodeRange;

    private final List<CompletionItem> data = new ArrayList<>();

    public CompletionProvider(CompilationUnit unit, Range cursor) {
        this.unit = unit;
        this.cursor = cursor;
        this.cursorNode = Nodes.getNodeAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
        this.cursorNodeRange = Ranges.of(cursorNode);
        this.cursorString = getTextBeforeCursor(cursorNode);
        this.prevCursorNode = Nodes.getPrevTerminal(unit.getTokenStream(), cursorNode);
        this.prevCursorNodeRange = Ranges.of(prevCursorNode);
    }

    public static CompletionList completion(CompilationUnit unit, CompletionParams params) {
        Range cursor = Ranges.of(params.getPosition());
        CompletionProvider provider = new CompletionProvider(unit, cursor);
        return new CompletionList(provider.complete());
    }

    private List<CompletionItem> complete() {
        unit.accept(this);
        return data;
    }

    private void completeImport(String completingString) {
        // TODO
    }

    private void completeLocalSymbols(String completingString, Scope scope) {
        for (Symbol symbol : scope.getSymbols()) {
            if (symbol.getDeclaredName().startsWith(completingString)) {
                CompletionItem item = new CompletionItem(symbol.getDeclaredName());
                item.setDetail(symbol.getType().toString());
                item.setKind(getCompletionItemKind(symbol.getKind()));
                data.add(item);
            }
        }
    }

    private void completeGlobalSymbols(String completingString) {
        for (Symbol member : unit.getEnv().getGlobalSymbols()) {
            if (member.getDeclaredName().startsWith(completingString)) {
                CompletionItem item = new CompletionItem(member.getDeclaredName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeStaticMembers(String completingString, Type type) {
        for (Symbol member : type.getMembers()) {
            if (member.isDeclaredBy(Declarator.STATIC)
                    && member.getDeclaredName().startsWith(completingString)) {
                CompletionItem item = new CompletionItem(member.getDeclaredName());
                item.setDetail("static " + member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeInstanceMembers(String completingString, Type type) {
        for (Symbol member : type.getMembers()) {
            if (!member.isDeclaredBy(Declarator.STATIC)
                    && member.getDeclaredName().startsWith(completingString)) {
                CompletionItem item = new CompletionItem(member.getDeclaredName());
                item.setDetail(member.getType().toString());
                item.setKind(getCompletionItemKind(member.getKind()));
                data.add(item);
            }
        }
    }

    private void completeKeywords(String completingString, String... keywords) {
        for (String keyword : keywords) {
            if (keyword.startsWith(completingString)) {
                CompletionItem item = new CompletionItem(keyword);
                item.setKind(CompletionItemKind.Keyword);
                item.setDetail(L10N.getString("l10n.keyword"));
                data.add(item);
            }
        }
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

    private String getTextBeforeCursor(ParseTree node) {
        Range nodeRange = Ranges.of(node);
        if (nodeRange.startLine != cursor.startLine || nodeRange.startLine != nodeRange.endLine) {
            return "";
        }
        int length = cursor.startColumn - nodeRange.startColumn;
        String text = node.getText();
        if (length > 0) {
            return text.substring(0, length);
        }
        return "";
    }

    private boolean isPrevCursor(ParseTree node) {
        return Ranges.contains(node, prevCursorNodeRange);
    }

    /* Visitor Overrides */

    @Override
    public Void visitChildren(RuleNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            if (isPrevCursor(child)) {
                child.accept(this);
                break;
            }
        }
        return null;
    }

    @Override
    public Void visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
        if (isPrevCursor(ctx.qualifiedName())) {
            if (Ranges.contains(ctx.qualifiedName(), cursorNodeRange)) {
                String completingString = getTextBeforeCursor(ctx.qualifiedName());
                completeImport(completingString);
            } else {
                completeKeywords(cursorString, Keywords.AS);
            }
        }
        return null;
    }

    @Override
    public Void visitParameter(ZenScriptParser.ParameterContext ctx) {
        return null;
    }

    @Override
    public Void visitVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        if (isPrevCursor(ctx.identifier())) {
            completeKeywords(cursorString, Keywords.AS);
        }
        if (isPrevCursor(ctx.SEMICOLON())) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
        }
        return null;
    }

    @Override
    public Void visitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        if (isPrevCursor(ctx.SEMICOLON())) {
            Scope scope = unit.lookupScope(cursorNode);
            completeLocalSymbols(cursorString, scope);
            completeGlobalSymbols(cursorString);
            completeKeywords(cursorString, Keywords.STATEMENT);
        }
        return null;
    }

    @Override
    public Void visitLocalAccessExpr(ZenScriptParser.LocalAccessExprContext ctx) {
        return null;
    }

    @Override
    public Void visitMemberAccessExpr(ZenScriptParser.MemberAccessExprContext ctx) {
        return null;
    }

    @Override
    public Void visitArgument(ZenScriptParser.ArgumentContext ctx) {
        return null;
    }

    /* End Visitor Overrides */

}
