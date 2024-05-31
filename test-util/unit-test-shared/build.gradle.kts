plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

val compatibilityVersion = libs.versions.javaCompileVersion.get()

java {
    sourceCompatibility = JavaVersion.valueOf(compatibilityVersion)
    targetCompatibility = JavaVersion.valueOf(compatibilityVersion)
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(libs.junit.jupiter)
    implementation(libs.kotlinx.coroutines.test.jvm)
    implementation(libs.kotlinx.coroutines.core.jvm)
}