plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(project(":shared:common-shared"))
}
