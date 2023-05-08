package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;

public class HoverProvider extends Visitor<Hover> {

    public static Hover hover(CompilationUnit unit, HoverParams params) {
        return new Hover();
    }

    private static Hover code(String code) {
        return new Hover(new MarkupContent(MarkupKind.MARKDOWN, warp(code)));
    }

    private static String warp(String code) {
        return String.format("```zenscript\n%s\n```\n", code);
    }

}
