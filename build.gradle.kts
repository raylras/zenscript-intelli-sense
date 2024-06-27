import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0" apply false
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // LSP4J no longer supports Java 8 starting with v0.21.0
    tasks.withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    tasks.withType<Jar> {
        archiveBaseName = "intellizen-${project.name}"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        val implementation by configurations
        implementation("org.slf4j:slf4j-api:2.0.13")
        implementation("org.slf4j:slf4j-simple:2.0.13")

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
