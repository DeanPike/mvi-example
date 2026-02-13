plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(libs.versions.jvmToolChainVersion.get().toInt())
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
}

dependencies {
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}