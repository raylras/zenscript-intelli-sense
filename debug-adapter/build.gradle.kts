plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":core"))

    testImplementation(libs.junit.jupiter)
}
