import org.gradle.api.initialization.resolve.RepositoriesMode.*

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
    @Suppress("UnstableApiUsage", "UnstableApiUsage")
    repositoriesMode.set(PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://www.jitpack.io")
        gradlePluginPortal()
    }
}

rootProject.name = "MyDatabaseApp"
include(":app")
