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
    implementation(project(":shared:common-shared"))
    implementation(project(":shared:data-shared"))
}