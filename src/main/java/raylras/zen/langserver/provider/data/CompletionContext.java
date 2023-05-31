package raylras.zen.langserver.provider.data;

import org.antlr.v4.runtime.tree.ParseTree;

public class CompletionContext {

    public final ParseTree completingNode;
    public final String completingString;
    public final Kind kind;

    public CompletionContext(Kind kind, ParseTree completingNode, String completingString) {
        this.kind = kind;
        this.completingNode = completingNode;
        this.completingString = completingString;
    }

    public enum Kind {
        IMPORT,
        LOCAL_STATEMENT, TOPLEVEL_STATEMENT,
        LOCAL_ACCESS, MEMBER_ACCESS,
        CLASS_BODY,
        NONE;
    }

}
