plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.valueOf(config.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(config.versions.javaCompileVersion.get())
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(libs.junit.jupiter)
    implementation(libs.kotlinx.coroutines.test.jvm)
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.junit.vintage.engine)
}