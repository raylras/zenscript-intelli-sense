package raylras.zen.bracket;

import raylras.zen.util.PackageTree;

public record BracketHandlerMirror(String typeQualifiedName, String regex, PackageTree<BracketHandlerEntry> entries) {
}
