import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHiltPlugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "au.com.deanpike.uitestshared"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.valueOf(libs.versions.jvmTargetVersion.get()))
        }
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(project(":listings:data"))
    implementation(project(":shared:data-shared"))
    implementation(project(":network"))
    implementation(project(":test-util:unit-test-shared"))
    implementation(project(":shared:ui-shared"))
    implementation(project(":shared:common-shared"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android.testing)
    api(libs.androidx.espresso.core)
    api(libs.mockwebserver)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.coil.compose)
    api(libs.okhttp.tls)
    implementation(libs.assertj)

    implementation(libs.coil.test)
}