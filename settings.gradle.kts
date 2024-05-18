pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mvi-example"
include(":app")
include(":listings:data")
include(":property:client")
include(":property:ui")
include(":shared:ui-shared")
include(":test-util:unit-test-shared")
include(":shared:data-shared")
include(":test-util:ui-test-shared")
include(":network")
