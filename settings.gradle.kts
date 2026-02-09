pluginManagement {
    includeBuild("build-logic")

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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

rootProject.name = "IngredientFarming"
include(":app")
include(":feature")
include(":feature:ingredient")
include(":core")
include(":core:designsystem")
include(":core:network")
include(":domain")
include(":domain:ingredient")
include(":core:model")
include(":data")
include(":data:ingredient")
include(":feature:main")
include(":core:navigation")
include(":core:database")
