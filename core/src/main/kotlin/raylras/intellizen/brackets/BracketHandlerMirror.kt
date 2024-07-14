package raylras.intellizen.brackets

data class BracketHandlerMirror(val type: String, val regex: Regex, val entries: Map<String, BracketHandlerEntry>)
