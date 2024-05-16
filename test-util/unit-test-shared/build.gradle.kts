plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(libs.junit.jupiter)
    implementation(libs.kotlinx.coroutines.test.jvm)
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.junit.vintage.engine)
}