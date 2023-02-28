package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import raylras.zen.code.SourceUnit;
import raylras.zen.code.parser.ZenScriptParserBaseVisitor;

public class HoverProvider extends ZenScriptParserBaseVisitor<Hover> {

    public static Hover hover(SourceUnit sourceUnit, HoverParams params) {
        if (sourceUnit == null)
            return null;
        if (sourceUnit.ast == null)
            sourceUnit.updateAll(null);

        // TODO
        return null;
    }

    private static Hover code(String code) {
        return new Hover(new MarkupContent(MarkupKind.MARKDOWN, warp(code)));
    }

    private static String warp(String code) {
        return String.format("```zenscript\n%s\n```\n", code);
    }

}
