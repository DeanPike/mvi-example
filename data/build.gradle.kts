plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kaptPlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":client"))
    implementation(project(":data-shared"))
    implementation(project(":network"))
    testImplementation(project(":client"))

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    // Retrofit
    api(libs.logging.interceptor)
    implementation(libs.retrofit)
    api(libs.retrofit.converter.gson)
    implementation(libs.retrofit.serialization.converter)

    implementation(libs.kotlinx.serialization.json)

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