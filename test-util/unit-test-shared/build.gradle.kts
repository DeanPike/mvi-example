plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(platform(libs.junit.bom))
    implementation(libs.junit.jupiter)
    implementation(libs.kotlinx.coroutines.test.jvm)
    implementation(libs.kotlinx.coroutines.core.jvm)
}