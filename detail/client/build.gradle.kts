plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

kotlin {
    jvmToolchain(libs.versions.jvmToolChainVersion.get().toInt())
}

dependencies {
    implementation(project(":shared:data-shared"))
    implementation(project(":shared:common-shared"))
}
