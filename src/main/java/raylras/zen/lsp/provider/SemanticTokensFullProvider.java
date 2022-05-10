package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensParams;
import raylras.zen.ast.*;
import raylras.zen.ast.expr.ArgumentsExpression;
import raylras.zen.ast.stmt.VarStatement;
import raylras.zen.lsp.TokenModifier;
import raylras.zen.lsp.TokenType;
import raylras.zen.lsp.ZenScriptVisitor;
import raylras.zen.util.ASTUtils;
import raylras.zen.util.PosUtils;
import raylras.zen.util.URIUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;


// TODO: SemanticTokensFullProvider
public class SemanticTokensFullProvider {

    private final ZenScriptVisitor visitor;
    private final SemanticTokenDataBuilder builder = new SemanticTokenDataBuilder();

    public SemanticTokensFullProvider(ZenScriptVisitor visitor) {
        this.visitor = visitor;
    }

    public CompletableFuture<SemanticTokens> provideSemanticTokensFull(SemanticTokensParams params) {
         List<ASTNode> nodes = visitor.getAstNodeListByURI().get(URIUtils.decode(params.getTextDocument().getUri()));

        if (nodes == null) return CompletableFuture.completedFuture(null);

        builder.clear();

        nodes.forEach(node -> {
            if (node instanceof ImportNode) {
                builder.push(((ImportNode) node).getReferenceNode(), TokenType.Class);
                builder.push(((ImportNode) node).getAliasNode(), TokenType.Class);
            } else if (node instanceof FunctionNode) {
                builder.push(((FunctionNode) node).getIdNode(), TokenType.Function, TokenModifier.Declaration);
            } else if (node instanceof ParameterNode) {
                builder.push(((ParameterNode) node).getIdNode(), TokenType.Parameter);
            } else if (node instanceof VariableNode) {
                VariableNode variable = (VariableNode) node;
                int modifiers = TokenModifier.Declaration.getId() | ASTUtils.getModifiers(variable);
                builder.push(variable.getIdNode(), TokenType.Variable, modifiers);
            } else if (node instanceof ZenClassNode) {
                builder.push(((ZenClassNode) node).getIdNode(), TokenType.Class, TokenModifier.Declaration);
            } else if (node instanceof FieldNode) {
                builder.push(((FieldNode) node).getIdNode(), TokenType.Property, TokenModifier.Declaration);
            } else if (node instanceof VarStatement) {
                VariableNode variable = ((VarStatement) node).getVariableNode();
                int modifiers = TokenModifier.Declaration.getId() | ASTUtils.getModifiers(variable);
                builder.push(((VarStatement) node).getVariableNode(), TokenType.Variable, modifiers);
            } else if (node instanceof ArgumentsExpression) {

            }
        });

        builder.build();

        return CompletableFuture.completedFuture(new SemanticTokens(builder.getSemanticTokenData()));
    }

    private static class SemanticToken implements Comparable<SemanticToken> {
        private final int line;
        private final int column;
        private final int length;
        private final int modifiers;
        private final TokenType tokenType;
        private final ASTNode node;

        public SemanticToken(ASTNode node, TokenType type, int modifiers) {
            this.node = node;
            this.tokenType = type;
            this.modifiers = modifiers;
            Position pos = PosUtils.toLSPPos(node);
            this.line = pos.getLine();
            this.column = pos.getCharacter();
            this.length = PosUtils.getLength(node);
        }

        public SemanticToken(int line, int column, int length, TokenType tokenType, int modifiers) {
            this.line = line;
            this.column = column;
            this.length = length;
            this.tokenType = tokenType;
            this.modifiers = modifiers;
            this.node = null;
        }

        @Override
        public int compareTo(SemanticToken o) {
            return this.line == o.line ? this.column - o.column : this.line - o.line;
        }

        @Override
        public String toString() {
            return node.toString();
        }
    }

    private static class SemanticTokenDataBuilder {
        private final List<Integer> semanticTokenData = new ArrayList<>();
        private final Set<SemanticToken> semanticTokenSet = new TreeSet<>();
        private SemanticToken prevToken;

        public List<Integer> getSemanticTokenData() {
            return semanticTokenData;
        }

        public void push(ASTNode node, TokenType tokenType, TokenModifier... tokenModifiers) {
            if (node == null) return;
            int modifiers = 0;
            for (TokenModifier m : tokenModifiers) {
                modifiers |= m.getId();
            }
            semanticTokenSet.add(new SemanticToken(node, tokenType, modifiers));
        }

        public void push(ASTNode node, TokenType tokenType, int tokenModifiers) {
            if (node == null) return;
            semanticTokenSet.add(new SemanticToken(node, tokenType, tokenModifiers));
        }

        private void build() {
            prevToken = new SemanticToken(0, 0, 0, null, 0);;
            semanticTokenSet.forEach(this::collectData);
        }

        private void collectData(SemanticToken token) {
            int[] prevPos = new int[]{prevToken.line, prevToken.column};
            int tokenType = token.tokenType.getId();

            convertToRelativePosition(prevPos, token.line, token.column, token.length, tokenType, token.modifiers);
            prevToken = token;
        }

        private void convertToRelativePosition(int[] prevPos, int line, int column, int length, int tokenType, int tokenModifier) {
            // index:     0     1       2       3           4
            // tokenData: line  column  length  tokenType   tokenModifier
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
            newTokenData[4] = tokenModifier;

            semanticTokenData.addAll(Arrays.stream(newTokenData).collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
        }

        private void clear() {
            semanticTokenData.clear();
            semanticTokenSet.clear();
        }

    }

}
