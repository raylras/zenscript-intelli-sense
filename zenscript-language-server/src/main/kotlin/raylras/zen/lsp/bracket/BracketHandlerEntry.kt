package raylras.zen.lsp.bracket

data class BracketHandlerEntry(val properties: Map<String, Any>) {
    fun getStringOrNull(key: String): String? {
        return properties[key] as? String
    }

    @Suppress("UNCHECKED_CAST")
    fun getStringListOrNull(key: String): List<String>? {
        return properties[key] as? List<String>
    }
}
