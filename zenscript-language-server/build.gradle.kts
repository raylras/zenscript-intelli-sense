group = "raylras.zen.lsp"

dependencies {
    implementation(project(":zenscript-code-model"))
    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j:0.21.2")
    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc:0.21.2")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.named<Copy>("distDeps") {
    include("org.eclipse.lsp4j-*.jar")
    include("org.eclipse.lsp4j.jsonrpc-*.jar")
    include("gson-*.jar")
}
