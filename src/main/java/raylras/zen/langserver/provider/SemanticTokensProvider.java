package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.*;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.langserver.LanguageServerContext;
import raylras.zen.project.ZenProjectManager;
import raylras.zen.semantic.AnnotatedTree;
import raylras.zen.semantic.symbol.*;
import raylras.zen.semantic.symbol.ClassSymbol;
import raylras.zen.semantic.type.FunctionType;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SemanticTokensProvider extends ZenScriptParserBaseListener {

    private final AnnotatedTree annotatedTree;
    private final TokenStream tokenStream;
    private final List<Integer> data;
    private int prevLine;
    private int prevColumn;

    public SemanticTokensProvider(AnnotatedTree annotatedTree, TokenStream tokenStream) {
        this.annotatedTree = annotatedTree;
        this.tokenStream = tokenStream;
        this.data = new ArrayList<>();
        this.prevLine = 1;
        this.prevColumn = 0;
    }

    public static SemanticTokens semanticTokensFull(LanguageServerContext serverContext, SemanticTokensParams params) {
        ZenProjectManager projectManager = serverContext.get(ZenProjectManager.class);
        return projectManager.getDocument(CommonUtils.toPath(params.getTextDocument().getUri()))
                .map(document -> {
                    SemanticTokensProvider provider = new SemanticTokensProvider(document.getAnnotatedTree(), document.getTokenStream());
                    ParseTreeWalker.DEFAULT.walk(provider, document.getAnnotatedTree().getParseTree());
                    return new SemanticTokens(provider.data);
                }).orElseGet(SemanticTokens::new);
    }

    private void push(TerminalNode node, int tokenType, int modifiers) {
        if (node != null) {
            push(node.getSymbol(), tokenType, modifiers);
        }
    }

    private void push(Token token, int tokenType, int modifiers) {
        int line = token.getLine() - prevLine;
        int column = token.getLine() == prevLine ? token.getCharPositionInLine() - prevColumn : token.getCharPositionInLine();
        int length = token.getText().length();
        prevLine = token.getLine();
        prevColumn = token.getCharPositionInLine();
        data.add(line);
        data.add(column);
        data.add(length);
        data.add(tokenType);
        data.add(modifiers);
    }

    @Override
    public void enterPackageName(ZenScriptParser.PackageNameContext ctx) {
        for (TerminalNode id : ctx.IDENTIFIER()) {
            push(id, TokenType.CLASS, 0);
        }
    }

    @Override
    public void enterForeachStatement(ZenScriptParser.ForeachStatementContext ctx) {
        for (TerminalNode id : ctx.IDENTIFIER()) {
            push(id, TokenType.VARIABLE, 0);
        }
    }

    @Override
    public void enterAlias(ZenScriptParser.AliasContext ctx) {
        push(ctx.IDENTIFIER(), TokenType.CLASS, 0);
    }

    @Override
    public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        push(ctx.IDENTIFIER(), TokenType.FUNCTION, 0);
    }

    @Override
    public void enterParameter(ZenScriptParser.ParameterContext ctx) {
        push(ctx.IDENTIFIER(), TokenType.PARAMETER, 0);
    }

    @Override
    public void enterClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        push(ctx.IDENTIFIER(), TokenType.CLASS, 0);
    }

    @Override
    public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        push(ctx.ZEN_CONSTRUCTOR(), TokenType.METHOD, 0);
    }

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        if (ctx.IDENTIFIER() == null) {
            return;
        }
        Symbol<?> symbol = annotatedTree.findSymbolOfNode(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText());
        if (symbol instanceof VariableSymbol) {
            int modifiers = 0;
            switch (((VariableSymbol) symbol).getModifier()) {
                case VariableSymbol.Modifier.GLOBAL:
                case VariableSymbol.Modifier.STATIC:
                    modifiers |= TokenModifier.STATIC;
                case VariableSymbol.Modifier.VAL:
                    modifiers |= TokenModifier.READONLY;
                case VariableSymbol.Modifier.VAR:
                    modifiers |= TokenModifier.DECLARATION;
            }
            int tokenType;
            if (symbol.getType() instanceof FunctionType) {
                tokenType = TokenType.FUNCTION;
            } else {
                tokenType = TokenType.VARIABLE;
            }
            push(ctx.IDENTIFIER(), tokenType, modifiers);
        }

    }

    @Override
    public void enterIDExpression(ZenScriptParser.IDExpressionContext ctx) {
        Symbol<?> symbol = annotatedTree.findSymbolOfNode(ctx, ctx.getText());
        if (symbol instanceof ImportSymbol) {
            push(ctx.IDENTIFIER(), TokenType.CLASS, 0);
        } else if (symbol instanceof ClassSymbol) {
            push(ctx.IDENTIFIER(), TokenType.CLASS, 0);
        } else if (symbol instanceof FunctionSymbol) {
            push(ctx.IDENTIFIER(), TokenType.FUNCTION, 0);
        } else if (symbol instanceof VariableSymbol) {
            int modifiers = 0;
            switch (((VariableSymbol) symbol).getModifier()) {
                case VariableSymbol.Modifier.GLOBAL:
                case VariableSymbol.Modifier.STATIC:
                    modifiers |= TokenModifier.STATIC;
                case VariableSymbol.Modifier.VAL:
                    modifiers |= TokenModifier.READONLY;
                case VariableSymbol.Modifier.VAR:
                    modifiers |= TokenModifier.DECLARATION;
            }
            int tokenType;
            if (symbol.getType() instanceof FunctionType) {
                tokenType = TokenType.FUNCTION;
            } else {
                tokenType = TokenType.VARIABLE;
            }
            push(ctx.IDENTIFIER(), tokenType, modifiers);
        }
    }

    @Override
    public void enterPrimitiveType(ZenScriptParser.PrimitiveTypeContext ctx) {
        push(ctx.start, TokenType.CLASS, 0);
    }

    @Override
    public void enterCallExpression(ZenScriptParser.CallExpressionContext ctx) {
        // dirty implementation
        // let something like mods.foo.Bar.baz() be marked with class
        // in fact, the class name could start with anything
        String text = ctx.start.getText();
        if (text.equals("mods") || text.equals("crafttweaker")) {
            List<Token> tokens = new ArrayList<>();
            Token token = ctx.start;
            while (token.getType() != ZenScriptLexer.PAREN_OPEN) {
                if (token.getType() == ZenScriptLexer.IDENTIFIER) {
                    tokens.add(token);
                }
                token = tokenStream.get(token.getTokenIndex() + 1);
            }

            Token funcID = tokens.remove(tokens.size() - 1);
            for (Token t : tokens) {
                push(t, TokenType.CLASS, 0);
            }
            push(funcID, TokenType.FUNCTION, 0);
        }
    }

    @Override
    public void enterMapEntry(ZenScriptParser.MapEntryContext ctx) {
        if (ctx.Key.getClass() == ZenScriptParser.IDExpressionContext.class) {
            TerminalNode id = ((ZenScriptParser.IDExpressionContext) ctx.Key).IDENTIFIER();
            Symbol symbol = annotatedTree.findSymbolOfNode(ctx, id.getText());
            if (symbol == null) {
                push(id, TokenType.STRING, 0);
            }
        }
    }

    public static final class TokenType {
        public static final int CLASS = 0;
        public static final int PARAMETER = 1;
        public static final int VARIABLE = 2;
        public static final int FUNCTION = 3;
        public static final int METHOD = 4;
        public static final int STRING = 5;
    }

    public static final class TokenModifier {
        public static final int DECLARATION = 1;
        public static final int READONLY = 2;
        public static final int STATIC = 4;
    }

    public static final SemanticTokensLegend SEMANTIC_TOKENS_LEGEND;

    static {
        Map<Integer, String> tokenTypes = new TreeMap<>();
        tokenTypes.put(TokenType.CLASS, SemanticTokenTypes.Class);
        tokenTypes.put(TokenType.PARAMETER, SemanticTokenTypes.Parameter);
        tokenTypes.put(TokenType.VARIABLE, SemanticTokenTypes.Variable);
        tokenTypes.put(TokenType.FUNCTION, SemanticTokenTypes.Function);
        tokenTypes.put(TokenType.METHOD, SemanticTokenTypes.Method);
        tokenTypes.put(TokenType.STRING, SemanticTokenTypes.String);

        Map<Integer, String> tokenModifiers = new TreeMap<>();
        tokenModifiers.put(TokenModifier.DECLARATION, SemanticTokenModifiers.Declaration);
        tokenModifiers.put(TokenModifier.READONLY, SemanticTokenModifiers.Readonly);
        tokenModifiers.put(TokenModifier.STATIC, SemanticTokenModifiers.Static);

        SEMANTIC_TOKENS_LEGEND = new SemanticTokensLegend(new ArrayList<>(tokenTypes.values()), new ArrayList<>(tokenModifiers.values()));
    }

}
