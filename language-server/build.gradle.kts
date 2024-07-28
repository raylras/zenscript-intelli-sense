plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.graalvm.buildtools.native)
}

dependencies {
    implementation(project(":core"))
    implementation(project(":i18n"))
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.lsp4j)
    implementation(libs.lsp4j.jsonrpc)
    implementation(libs.gson)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("intellizen-language-server")
            mainClass.set("raylras.intellizen.languageserver.StandardIOLauncher")
            buildArgs.add("-O4")
        }
    }
}

tasks.register<Copy>("distNative") {
    group = "dist"
    from("build/native/nativeCompile")
    into("../vscode-extension/server")
    include("*.exe", "*.dll")
    dependsOn("nativeCompile")
}

tasks.test {
    useJUnitPlatform()
}
