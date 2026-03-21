// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.daggerHiltPlugin) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
}

subprojects {
    afterEvaluate {
        extensions.findByType<com.android.build.gradle.BaseExtension>()?.compileOptions {
            sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
            targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
        }
        extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension>()
            ?.jvmToolchain(libs.versions.jvmToolChainVersion.get().toInt())
    }
}
