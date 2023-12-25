package raylras.zen.model

data class Preprocessor(val raw: String) {
    val split: List<String> = raw.substring(1).split(" ".toRegex())

    val name: String
        get() = split[0]

    val rest: List<String>
        get() = split.subList(1, split.size)
}
