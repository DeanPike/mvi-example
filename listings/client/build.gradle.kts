plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {
    implementation(project(":shared:common-shared"))
    implementation(project(":shared:data-shared"))
    implementation(libs.kotlinx.serialization.core)
}