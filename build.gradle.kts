import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0-Beta2" apply false
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    /*
        Additional information:

        The last version supporting Java 1.8 is lsp4j 0.19.1.
        Starting from lsp4j 0.21.0, Java 11 is required.

        Consider setting compatibility to 11.

        @see ::zenscript-language-server/build.gradle.kts
        @see ::vscode-extension/client/src/extension.js
    */

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        val implementation by configurations
        implementation("org.slf4j:slf4j-api:2.0.11")
        implementation("org.slf4j:slf4j-simple:2.0.10")

        val testImplementation by configurations
        testImplementation(platform("org.junit:junit-bom:5.10.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
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
        include("slf4j-api-*.jar")
        include("slf4j-simple-*.jar")
    }
}

// root project
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
