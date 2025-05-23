plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHiltPlugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

val compatibilityVersion = libs.versions.javaCompileVersion.get()

android {
    namespace = "au.com.deanpike.mviexample"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "au.com.deanpike.mviexample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 211
        versionName = "2.0.1"

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "au.com.deanpike.uitestshared.MainTestRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
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

    sourceSets {
        getByName("androidTest") {
            java.srcDir("${project.rootDir}/listings/ui/src/androidTest/java")
            java.srcDir("${project.rootDir}/detail/ui/src/androidTest/java")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }

    tasks.withType<Test>() {
        useJUnitPlatform()
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {

    implementation(project(":listings:ui"))
    implementation(project(":listings:client"))
    implementation(project(":listings:data"))
    implementation(project(":detail:ui"))
    implementation(project(":detail:client"))
    implementation(project(":detail:data"))
    implementation(project(":shared:data-shared"))
    implementation(project(":network"))
    implementation(project(":shared:ui-shared"))
    androidTestImplementation(project(":test-util:ui-test-shared"))
    androidTestImplementation(project(":shared:common-shared"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.window.core.android)
    implementation(libs.adaptive.layout)
    implementation(libs.adaptive.navigation)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.core.jvm)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    androidTestImplementation(libs.hilt.android)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.assertj)
}