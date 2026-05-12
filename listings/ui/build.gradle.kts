plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.daggerHiltPlugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "au.com.deanpike.listings.ui"
    compileSdk {
        version = release(libs.versions.compileSdk.get().toInt())
    }

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

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            all { it.useJUnitPlatform() }
        }
    }
}

dependencies {
    implementation(project(":listings:client"))
    implementation(project(":shared:ui-shared"))
    implementation(project(":shared:data-shared"))
    implementation(project(":shared:common-shared"))
    implementation(project(":shared:navigation"))
    testImplementation(project(":test-util:unit-test-shared"))
    testImplementation(project(":test-util:ui-test-shared"))

    implementation(libs.androidx.window.core.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.androidx.navigation3.runtime)

    implementation(libs.coil.compose)
    implementation(libs.constraintlayout.compose)

    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.assertj)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)

    testImplementation(libs.bundles.robolectric.tests)
    implementation(libs.tracing)

}