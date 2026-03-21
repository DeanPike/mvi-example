plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(platform(libs.junit.bom))
    implementation(libs.junit.jupiter)
    implementation(libs.kotlinx.coroutines.test.jvm)
    implementation(libs.kotlinx.coroutines.core.jvm)
}