import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    antlr
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    antlr(libs.antlr4)
    implementation(project(":i18n"))
    implementation(libs.antlr4.runtime)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.gson)

    testImplementation(libs.junit.jupiter)
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

tasks.named<Delete>("clean") {
    finalizedBy(tasks.named("cleanGen"))
}

tasks.register<Delete>("cleanGen") {
    group = "build"
    delete("src/main/gen")
}
