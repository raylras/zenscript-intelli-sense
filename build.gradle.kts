subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        val implementation by configurations
        implementation("org.slf4j:slf4j-api:2.0.9")
        implementation("org.slf4j:slf4j-simple:2.0.9")

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
        from(configurations.named("runtimeClasspath").get())
        into("../vscode-extension/server")

        include("slf4j-api-*.jar")
        include("slf4j-simple-*.jar")
    }
}

// root project
tasks.register<Delete>("cleanDist") {
    group = "dist"
    delete("vscode-extension/server")
}
