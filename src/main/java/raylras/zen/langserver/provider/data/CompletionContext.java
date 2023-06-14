package raylras.zen.langserver.provider.data;

public class CompletionContext {

    public final Kind kind;
    public final String completingString;
    public final Object payload;

    public CompletionContext(Kind kind, String completingString, Object payload) {
        this.kind = kind;
        this.completingString = completingString;
        this.payload = payload;
    }

    public enum Kind {
        IMPORT,
        LOCAL_STATEMENT, TOPLEVEL_STATEMENT,
        LOCAL_ACCESS, MEMBER_ACCESS,
        CLASS_BODY,
        KEYWORD,
        NONE;
    }

}
