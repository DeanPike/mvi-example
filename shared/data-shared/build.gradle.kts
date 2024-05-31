plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kaptPlugin)
}

val compatibilityVersion = libs.versions.javaCompileVersion.get()

java {
    sourceCompatibility = JavaVersion.valueOf(compatibilityVersion)
    targetCompatibility = JavaVersion.valueOf(compatibilityVersion)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}