group = "raylras.intellizen.core"

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.aya-prover.upstream:ij-parsing-core:0.0.25")
}

sourceSets.main {
    java.srcDirs("src/main/gen")
}

tasks.named<Copy>("distDeps") {
    include("gson-*.jar")
    include("ij-parsing-core-*.jar")
}
