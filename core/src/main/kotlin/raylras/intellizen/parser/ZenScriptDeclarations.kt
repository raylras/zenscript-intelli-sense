package raylras.intellizen.parser

fun main() {
    val code = """
        zenClass Foo extends Bar {
            val foo as Foo;
        }

        zenClass Bar {
            val bar as Bar;
        }
    """.trimIndent()


    println()
}
