package raylras.zen.model.brackets

data class BracketHandlerMirror(val type: String, val regex: Regex, val entries: Map<String, BracketHandlerEntry>)
