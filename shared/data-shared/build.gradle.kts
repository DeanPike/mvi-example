plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}