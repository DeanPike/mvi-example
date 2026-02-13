plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.daggerHiltPlugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "au.com.deanpike.listings.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "au.com.deanpike.uitestshared.MainTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    tasks.withType<Test>() {
        useJUnitPlatform()
    }

    testOptions {
        animationsDisabled = true
    }
}

kotlin {
    jvmToolchain(libs.versions.jvmToolChainVersion.get().toInt())
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

    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.window.core.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    androidTestImplementation(libs.hilt.android)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)

    implementation(libs.coil.compose)
    implementation(libs.constraintlayout.compose)

    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.assertj)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.assertj)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)

    implementation(libs.tracing)

}