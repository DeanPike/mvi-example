plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    id("java-test-fixtures")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testFixturesImplementation(project(":data-shared"))
    testFixturesImplementation(libs.junit.jupiter)
    testFixturesImplementation(libs.kotlinx.coroutines.test.jvm)
    testFixturesImplementation(libs.kotlinx.coroutines.core.jvm)
    testFixturesImplementation(libs.junit.vintage.engine)
}