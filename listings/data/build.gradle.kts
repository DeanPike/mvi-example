plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
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
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.joda.time)
}

tasks.test {
    useJUnitPlatform()
}