plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

val compatibilityVersion = libs.versions.javaCompileVersion.get()

java {
    sourceCompatibility = JavaVersion.valueOf(compatibilityVersion)
    targetCompatibility = JavaVersion.valueOf(compatibilityVersion)
}

dependencies {
    implementation(project(":listings:client"))
    implementation(project(":shared:data-shared"))
    implementation(project(":network"))
    implementation(project(":shared:common-shared"))
    testImplementation(project(":listings:client"))

    // Hilt
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    // Retrofit
    api(libs.logging.interceptor)
    implementation(libs.retrofit)
    api(libs.retrofit.converter.gson)
    implementation(libs.retrofit.serialization.converter)

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