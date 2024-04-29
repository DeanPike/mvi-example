plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kaptPlugin)
    alias(libs.plugins.daggerHiltPlugin)
}

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
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
    implementation(project(":data"))
    implementation(project(":data-shared"))
    implementation(project(":network"))
    implementation(project(":test-util:unit-test-shared"))
    implementation(project(":ui-shared"))

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