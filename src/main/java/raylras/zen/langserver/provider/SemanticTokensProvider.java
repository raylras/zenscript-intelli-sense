package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.*;
import raylras.zen.langserver.LanguageServerContext;
import raylras.zen.project.ZenDocument;
import raylras.zen.project.ZenProjectManager;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SemanticTokensProvider {

    public enum TokenType {
        CLASS(0, SemanticTokenTypes.Class),
        PARAMETER(1, SemanticTokenTypes.Parameter),
        VARIABLE(2, SemanticTokenTypes.Variable),
        PROPERTY(3, SemanticTokenTypes.Property),
        FUNCTION(4, SemanticTokenTypes.Function),
        METHOD(5, SemanticTokenTypes.Method),
        STRING(6, SemanticTokenTypes.String),
        NUMBER(7, SemanticTokenTypes.Number);

        public final int ID;
        public final String NAME;
        TokenType(int ID, String NAME) {
            this.ID = ID;
            this.NAME = NAME;
        }
    }

    public enum TokenModifiers {
        Declaration(1<<0, SemanticTokenModifiers.Declaration),
        Definition(1<<1, SemanticTokenModifiers.Definition),
        Readonly(1<<2, SemanticTokenModifiers.Readonly),
        Static(1<<3, SemanticTokenModifiers.Static);

        public final int ID;
        public final String NAME;
        TokenModifiers(int ID, String NAME) {
            this.ID = ID;
            this.NAME = NAME;
        }
    }
    public static final SemanticTokensLegend Semantic_Tokens_Legend;

    static {
        List<String> tokenTypes = Arrays.stream(TokenType.values()).map(tokenType -> tokenType.NAME).collect(Collectors.toList());
        List<String> tokenModifiers = Arrays.stream(TokenModifiers.values()).map(modifier -> modifier.NAME).collect(Collectors.toList());
        Semantic_Tokens_Legend =  new SemanticTokensLegend(tokenTypes, tokenModifiers);
    }

    private final List<Integer> data;

    public SemanticTokensProvider() {
        this.data = new ArrayList<>();
    }

    public static SemanticTokens semanticTokensFull(LanguageServerContext context, SemanticTokensParams params) {
        ZenProjectManager projectManager = ZenProjectManager.getInstance(context);
        ZenDocument document = projectManager.getDocument(CommonUtils.toPath(params.getTextDocument().getUri()));
        SemanticTokensProvider provider = new SemanticTokensProvider();
        return new SemanticTokens(provider.data);
    }

}
