plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(libs.versions.jvmToolChainVersion.get().toInt())
}

dependencies {
    implementation(project(":shared:common-shared"))
    // Retrofit
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Hilt
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}