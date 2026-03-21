plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

dependencies {
    implementation(project(":shared:common-shared"))
    implementation(project(":shared:data-shared"))
}