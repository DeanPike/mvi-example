plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kaptPlugin)
}

val compatibilityVersion = libs.versions.javaCompileVersion.get()

java {
    sourceCompatibility = JavaVersion.valueOf(compatibilityVersion)
    targetCompatibility = JavaVersion.valueOf(compatibilityVersion)
}

dependencies {
    implementation(project(":network"))
    implementation(project(":shared:common-shared"))
    implementation(project(":detail:client"))
    implementation(project(":shared:data-shared"))

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    // Retrofit
    api(libs.logging.interceptor)
    implementation(libs.retrofit)
    api(libs.retrofit.converter.gson)
    implementation(libs.retrofit.serialization.converter)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.store)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.joda.time)
}

tasks.test {
    useJUnitPlatform()
}