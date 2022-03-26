package raylras.zen.lsp.provider;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensParams;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.lsp.ZenTokenType;
import raylras.zen.lsp.ZenTokenTypeModifier;
import raylras.zen.util.PosUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SemanticTokensFullProvider extends ZenScriptParserBaseVisitor<SemanticTokensFullProvider.SemanticToken> {

    private final ParserRuleContext scriptContext;
    private final SemanticTokenBuilder builder;

    public SemanticTokensFullProvider(ParserRuleContext scriptContext) {
        this.scriptContext = scriptContext;
        this.builder = new SemanticTokenBuilder();
    }

    public CompletableFuture<SemanticTokens> provideSemanticTokensFull(SemanticTokensParams params) {
        visit(scriptContext);
        builder.build();
        return CompletableFuture.completedFuture(new SemanticTokens(builder.getSemanticTokensData()));
    }

    @Override
    public SemanticToken visitImportStatement(ZenScriptParser.ImportStatementContext ctx) {
        List<TerminalNode> nNames = ctx.className().IDENTIFIER();

        return null;
    }

    @Override
    public SemanticToken visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        builder.push(tName, ZenTokenType.Class);

        return super.visitZenClassDeclaration(ctx);
    }

    @Override
    public SemanticToken visitConstructor(ZenScriptParser.ConstructorContext ctx) {
        Token tConstructor = ctx.ZEN_CONSTRUCTOR().getSymbol();
        builder.push(tConstructor, ZenTokenType.Function);

        return super.visitConstructor(ctx);
    }

    @Override
    public SemanticToken visitMethod(ZenScriptParser.MethodContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        builder.push(tName, ZenTokenType.Function);

        return super.visitMethod(ctx);
    }

    @Override
    public SemanticToken visitField(ZenScriptParser.FieldContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        builder.push(tName, ZenTokenType.Variable);

        return super.visitField(ctx);
    }

    @Override
    public SemanticToken visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        builder.push(tName, ZenTokenType.Function);

        return super.visitFunctionDeclaration(ctx);
    }

    @Override
    public SemanticToken visitParameters(ZenScriptParser.ParametersContext ctx) {
        ctx.parameter().forEach(context -> {
            builder.push(context.IDENTIFIER().getSymbol(), ZenTokenType.Parameter);
        });

        return super.visitParameters(ctx);
    }

    @Override
    public SemanticToken visitAsType(ZenScriptParser.AsTypeContext ctx) {
        if (ctx.type().typePrimitive() != null) {
            Token tType = ctx.type().typePrimitive().start;
            builder.push(tType, tType.getType() == ZenScriptLexer.STRING ? ZenTokenType.Class : ZenTokenType.Keyword);
            return null;
        }

        if (ctx.type().typeClass() != null) {
            List<TerminalNode> nNames = ctx.type().typeClass().className().IDENTIFIER();
            nNames.forEach(node -> builder.push(node.getSymbol(), ZenTokenType.Class));
        }

        return null;
    }

    static class SemanticToken implements Comparable<SemanticToken> {
        Token token;
        ZenTokenType tokenType;
        ZenTokenTypeModifier[] tokenModifiers;

        public SemanticToken(Token token, ZenTokenType tokenType, ZenTokenTypeModifier[] tokenModifiers) {
            this.tokenType = tokenType;
            this.tokenModifiers = tokenModifiers;
            this.token = token;
        }

        @Override
        public int compareTo(SemanticToken o) {
            // for tokens in the same line, the token of smaller column is ranked first
            // if not in the same line, the token of smaller line is ranked first
            int t1Line = this.token.getLine();
            int t1Column = this.token.getCharPositionInLine();
            int t2Line = o.token.getLine();
            int t2Column = o.token.getCharPositionInLine();

            return t1Line == t2Line ? t1Column - t2Column : t1Line - t2Line;
        }

        @Override
        public String toString() {
            return token.toString();
        }
    }


    // Because LSP's token format uses relative positions, witch means a token' position
    // depends on the previous token, so the tokens must be in order.
    // We use TreeSet to ensure that, and SemanticToken must be Comparable.
    static class SemanticTokenBuilder {
        private final Set<SemanticToken> semanticTokens = new TreeSet<>();
        private final List<Integer> semanticTokensData = new LinkedList<>();
        Token prevToken;

        public SemanticTokenBuilder push(Token token, ZenTokenType tokenType, Collection<ZenTokenTypeModifier> tokenModifiers) {
            semanticTokens.add(new SemanticToken(token, tokenType, tokenModifiers.toArray(new ZenTokenTypeModifier[0])));
            return this;
        }

        public SemanticTokenBuilder push(Token token, ZenTokenType tokenType, ZenTokenTypeModifier... tokenModifiers) {
            semanticTokens.add(new SemanticToken(token, tokenType, tokenModifiers));
            return this;
        }

        public SemanticTokenBuilder build() {
            CommonToken first = new CommonToken(-1);
            first.setLine(1);
            first.setCharPositionInLine(0);
            prevToken = first;
            semanticTokens.forEach(this::semanticize);
            return this;
        }

        private void semanticize(SemanticToken semanticToken) {
            int prevLine = PosUtil.getPosition(prevToken).getLine();
            int prevColumn = PosUtil.getPosition(prevToken).getCharacter();
            int[] prevPos = new int[]{prevLine, prevColumn};

            int line = PosUtil.getPosition(semanticToken.token).getLine();
            int column = PosUtil.getPosition(semanticToken.token).getCharacter();
            int length = PosUtil.getLength(semanticToken.token);
            int tokenType = semanticToken.tokenType.getId();
            int tokenModifiers = ZenTokenTypeModifier.getInt(semanticToken.tokenModifiers);

            convertToRelativePosition(prevPos, line, column, length, tokenType, tokenModifiers);

            prevToken = semanticToken.token;
        }

        private void convertToRelativePosition(int[] prevPos, int line, int column, int length, int tokenType, int tokenModifiers) {

            // index:     0     1       2       3           4
            // tokenData: line  column  length  tokenType   tokenModifiers
            int[] newTokenData = new int[5];

            // a new token's line is always the relative line of the previous token
            newTokenData[0] = line - prevPos[0];

            // if a new token has the same line as the previous token
            if (prevPos[0] == line) {
                // use relative colum
                newTokenData[1] = column - prevPos[1];
            } else {
                // use absolute column
                newTokenData[1] = column;
            }

            newTokenData[2] = length;
            newTokenData[3] = tokenType;
            newTokenData[4] = tokenModifiers;

            semanticTokensData.addAll(Arrays.stream(newTokenData).collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
        }

        public List<Integer> getSemanticTokensData() {
            return semanticTokensData;
        }

    }

}
