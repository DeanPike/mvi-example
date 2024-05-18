plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kaptPlugin)
    alias(libs.plugins.daggerHiltPlugin)
}

android {
    namespace = "au.com.deanpike.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "au.com.deanpike.uitestshared.MainTestApplication"
        consumerProguardFiles("consumer-rules.pro")
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
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
    kapt {
        correctErrorTypes = true
    }

    tasks.withType<Test>() {
        useJUnitPlatform()
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(":listings:client"))
    implementation(project(":shared:ui-shared"))
    implementation(project(":shared:data-shared"))
    implementation(project(":shared:common-shared"))
    androidTestImplementation(project(":listings:client"))
    androidTestImplementation(project(":listings:data"))
    androidTestImplementation(project(":test-util:ui-test-shared"))
    androidTestImplementation(project(":shared:ui-shared"))
    androidTestImplementation(project(":network"))
    androidTestImplementation(project(":test-util:unit-test-shared"))
    testImplementation(project(":test-util:unit-test-shared"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.lifecycle.viewmodel.compose)

    androidTestImplementation(libs.hilt.android)
    kaptAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)

    implementation(libs.coil.compose)
    implementation(libs.constraintlayout.compose)

    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.assertj)
    androidTestImplementation(libs.lifecycle.viewmodel.compose)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.assertj)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)

}