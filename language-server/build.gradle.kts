group = "raylras.intellizen.lsp"

dependencies {
    implementation(project(":core"))
    implementation(project(":i18n"))

    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j:0.21.2")
    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc:0.21.2")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.named<Copy>("distDeps") {
    include("org.eclipse.lsp4j-*.jar")
    include("org.eclipse.lsp4j.jsonrpc-*.jar")
    include("gson-*.jar")
}
