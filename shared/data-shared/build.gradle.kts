plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kaptPlugin)
}

java {
    sourceCompatibility = JavaVersion.valueOf(config.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(config.versions.javaCompileVersion.get())
}

dependencies {
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}