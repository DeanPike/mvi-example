plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompileVersion.get())
}

dependencies {
    implementation(project(":network"))
    implementation(project(":shared:common-shared"))
    implementation(project(":detail:client"))
    implementation(project(":shared:data-shared"))

    // Hilt
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    // Retrofit
    api(libs.logging.interceptor)
    implementation(libs.retrofit)
    api(libs.retrofit.converter.gson)

    implementation(libs.store)

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