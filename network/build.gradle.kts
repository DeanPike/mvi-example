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
    implementation(project(":shared:common-shared"))
    // Retrofit
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.serialization.converter)

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}