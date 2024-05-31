plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kaptPlugin)
    alias(libs.plugins.daggerHiltPlugin)
}

val compatibilityVersion = libs.versions.javaCompileVersion.get()

android {
    namespace = "au.com.deanpike.uitestshared"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

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
        sourceCompatibility = JavaVersion.valueOf(compatibilityVersion)
        targetCompatibility = JavaVersion.valueOf(compatibilityVersion)
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTargetVersion.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerVersion.get()
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

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android.testing)
    api(libs.hilt.android.testing)
    api(libs.androidx.espresso.core)
    api(libs.mockwebserver)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.coil.compose)
    api(libs.okhttp.tls)
}