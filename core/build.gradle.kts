import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("antlr")
}

group = "raylras.intellizen.core"

dependencies {
    antlr("org.antlr:antlr4:4.13.1")
    implementation(project(":i18n"))
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("com.google.code.gson:gson:2.10.1")
}

sourceSets.main {
    java.srcDirs("src/main/gen/java")
}

tasks.named<AntlrTask>("generateGrammarSource") {
    maxHeapSize = "64m"
    arguments = listOf("-visitor", "-listener", "-long-messages", "-package", "raylras.intellizen.parser")
    outputDirectory = file("src/main/gen/java/raylras/intellizen/parser")
}

tasks.named<KotlinCompile>("compileKotlin") {
    dependsOn("generateGrammarSource")
}

tasks.named<KotlinCompile>("compileTestKotlin") {
    dependsOn("generateTestGrammarSource")
}

tasks.named<Copy>("distDeps") {
    include("antlr4-runtime-*.jar")
    include("gson-*.jar")
}

tasks.named<Delete>("clean") {
    finalizedBy(tasks.named("cleanGen"))
}

tasks.register<Delete>("cleanGen") {
    group = "build"
    delete("src/main/gen")
}
