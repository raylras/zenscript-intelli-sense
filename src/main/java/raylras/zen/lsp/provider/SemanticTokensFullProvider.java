package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensParams;
import raylras.zen.lsp.ZenTokenType;
import raylras.zen.lsp.ZenTokenTypeModifier;
import raylras.zen.util.PosUtil;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.statements.StatementVar;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SemanticTokensFullProvider {

    private final String uri;
    private final ZenParsedFile parsedFile;

    public SemanticTokensFullProvider(String uri, ZenParsedFile parsedFile) {
        this.uri = uri;
        this.parsedFile = parsedFile;
    }

    public CompletableFuture<SemanticTokens> provideSemanticTokensFull(SemanticTokensParams params) {
        SemanticTokenBuilder builder = new SemanticTokenBuilder();

        // keywords
        parsedFile.getTokener().getTokens().stream()
                .filter(zenToken -> ZenTokener.getKEYWORDS().containsKey(zenToken.getValue()))
                .collect(Collectors.toList())
                .forEach(token -> builder.push(token, ZenTokenType.KEYWORD));

        // imports
        parsedFile.getImports()
                .forEach(anImport -> {
                    anImport.getName().forEach(zenToken -> builder.push(zenToken, ZenTokenType.CLASS));
                    builder.push(anImport.getRename(), ZenTokenType.CLASS);
                });

        // functions
        parsedFile.getFunctions().values()
                .forEach(parsedFunction -> {
                    builder.push(parsedFunction.getName(), ZenTokenType.FUNCTION);
                    parsedFunction.getArguments()
                            .forEach(parsedFunctionArgument -> builder.push(parsedFunctionArgument.getName(), ZenTokenType.PARAMETER));
                    Arrays.stream(parsedFunction.getStatements())
                            .forEach(statement -> {
                                if (statement instanceof StatementVar) {
                                    StatementVar s = (StatementVar) statement;
                                    builder.push((s.getName()), ZenTokenType.PARAMETER);
                                }
                            });
                });

        // globals
        parsedFile.getGlobals()
                .forEach((s, parsedGlobalValue) -> {
                    List<ZenTokenTypeModifier> modifiers = new LinkedList<>();
                    if (parsedGlobalValue.isGlobal()) modifiers.add(ZenTokenTypeModifier.Static);
                    builder.push(parsedGlobalValue.getName(), ZenTokenType.PARAMETER, modifiers);
                });

        builder.build();

        return CompletableFuture.completedFuture(new SemanticTokens(builder.getSemanticTokensData()));
    }

    static class SemanticToken implements Comparable<SemanticToken> {
        final Token zenToken;
        final ZenTokenType tokenType;
        final ZenTokenTypeModifier[] tokenModifiers;

        public SemanticToken(Token zenToken, ZenTokenType tokenType, ZenTokenTypeModifier[] tokenModifiers) {
            this.zenToken = zenToken;
            this.tokenType = tokenType;
            this.tokenModifiers = tokenModifiers;
        }

        @Override
        public int compareTo(SemanticToken o) {
            return this.zenToken.compareTo(o.zenToken);
        }
    }

    static class SemanticTokenBuilder {
        private final Set<SemanticToken> semanticTokens = new TreeSet<>();
        private final List<Integer> semanticTokensData = new LinkedList<>();
        Token prevToken;

        public SemanticTokenBuilder push(Token zenToken, ZenTokenType tokenType, ZenTokenTypeModifier... tokenModifiers) {
            semanticTokens.add(new SemanticToken(zenToken, tokenType, tokenModifiers));
            return this;
        }

        public SemanticTokenBuilder push(Token zenToken, ZenTokenType tokenType, Collection<ZenTokenTypeModifier> tokenModifiers) {
            semanticTokens.add(new SemanticToken(zenToken, tokenType, tokenModifiers.toArray(new ZenTokenTypeModifier[0])));
            return this;
        }

        public SemanticTokenBuilder build() {
            prevToken = new Token(null,0,new ZenPosition(null,1,1,null),null);
            for (SemanticToken semanticToken : semanticTokens) {
                semanticize(semanticToken);
            }
            return this;
        }

        private void semanticize(SemanticToken semanticToken) {
            int prevLine = PosUtil.convert(prevToken.getStart()).getLine();
            int prevColumn = PosUtil.convert(prevToken.getStart()).getCharacter();
            int[] prevPos = new int[]{prevLine, prevColumn};

            int line = PosUtil.convert(semanticToken.zenToken.getStart()).getLine();
            int column = PosUtil.convert(semanticToken.zenToken.getStart()).getCharacter();
            int length = semanticToken.zenToken.getLength();
            int tokenType = semanticToken.tokenType.getId();
            int tokenModifiers = ZenTokenTypeModifier.getInt(semanticToken.tokenModifiers);

            transformToRelativePosition(prevPos, line, column, length, tokenType, tokenModifiers);
            prevToken = semanticToken.zenToken;
        }

        private void transformToRelativePosition(int[] prevPos, int line, int column, int length, int tokenType, int tokenModifiers) {
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
