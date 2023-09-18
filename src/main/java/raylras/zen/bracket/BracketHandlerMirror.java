package raylras.zen.bracket;

import java.util.List;

public record BracketHandlerMirror(String qualifiedName, String regex, List<BracketHandlerEntry> entries) {
}
