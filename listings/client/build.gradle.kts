plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
}

dependencies {
    implementation(project(":shared:common-shared"))
    implementation(project(":shared:data-shared"))
}