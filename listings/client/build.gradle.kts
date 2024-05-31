plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.valueOf(config.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(config.versions.javaCompileVersion.get())
}

dependencies {
    implementation(project(":shared:common-shared"))
    implementation(project(":shared:data-shared"))
}