package raylras.zen.bracket;

import java.util.List;

public record BracketHandlerMirror(String type, String regex, List<BracketHandlerEntry> entries) {
}
