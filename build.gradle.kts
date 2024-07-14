import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

subprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = libs.versions.jvmTarget.get()
        targetCompatibility = libs.versions.jvmTarget.get()
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = libs.versions.jvmTarget.map { JvmTarget.fromTarget(it) }
        }
    }

    tasks.withType<Jar> {
        archiveBaseName = "intellizen-${project.name}"
    }

    tasks.register<Copy>("dist") {
        group = "dist"
        from(tasks.named("jar"))
        into("../vscode-extension/server")
        finalizedBy("distDeps")
    }

    tasks.register<Copy>("distDeps") {
        group = "dist"
        val runtimeClasspath by configurations
        from(runtimeClasspath)
        into("../vscode-extension/server")

        include("kotlin-stdlib-*.jar")
        include(libs.antlr4.runtime)
        include(libs.slf4j.api)
        include(libs.slf4j.simple)
        include(libs.lsp4j)
        include(libs.lsp4j.jsonrpc)
        include(libs.gson)
    }
}

tasks.register<Delete>("clean") {
    group = "build"
    delete("build")
    delete("out")
    finalizedBy(tasks.named("cleanDist"))
}

tasks.register<Delete>("cleanDist") {
    group = "dist"
    delete("vscode-extension/server")
}

fun Copy.include(dep: ProviderConvertible<MinimalExternalModuleDependency>) {
    include(dep.asProvider())
}

fun Copy.include(dep: Provider<MinimalExternalModuleDependency>) {
    val jarFile = dep.map { it.name + "-" + it.version + ".jar" }.get()
    include(jarFile)
}
