group = "raylras.intellizen.lsp"

dependencies {
    implementation(project(":core"))
    implementation(project(":i18n"))

    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j:0.23.1")
    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc:0.23.1")
    implementation("com.google.code.gson:gson:2.11.0")
}

tasks.named<Copy>("distDeps") {
    include("org.eclipse.lsp4j-*.jar")
    include("org.eclipse.lsp4j.jsonrpc-*.jar")
    include("gson-*.jar")
}
