plugins {
    id ("antlr")
}

group = "raylras.zen.model"

dependencies {
    antlr("org.antlr:antlr4:4.13.1")
    implementation ("org.antlr:antlr4-runtime:4.13.1")
    implementation ("org.eclipse.lsp4j:org.eclipse.lsp4j:0.21.1")
}

sourceSets.main {
    java.srcDirs("src/main/gen/java")
}

tasks.named<AntlrTask>("generateGrammarSource") {
    maxHeapSize = "64m"
    arguments = listOf("-visitor", "-listener", "-long-messages", "-package", "raylras.zen.model.parser")
    outputDirectory = file("src/main/gen/java/raylras/zen/model/parser")
}

tasks.named<Copy>("distDeps") {
    include("antlr4-runtime-*.jar")
    include("org.eclipse.lsp4j-*.jar")
}

tasks.register<Delete>("cleanGen") {
    group = "build"
    delete("src/main/gen")
}

tasks.named<Delete>("clean") {
    finalizedBy("cleanGen")
}
