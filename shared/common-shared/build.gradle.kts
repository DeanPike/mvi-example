plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.gson)

    // Hilt
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}