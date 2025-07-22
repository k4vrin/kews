@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        includeBuild("build-logic")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven(url = "https://maven.myket.ir")
        google()
        mavenCentral()
    }
}

private fun subprojects(path: String): List<String> {
    return file(path)
        .listFiles()
        ?.filter {
            it.isDirectory && !it.listFiles().isNullOrEmpty() && it.listFiles().any { file -> file.name == "build.gradle.kts" }
        }?.map {
            "${path.replace('/', ':')}:${it.name}"
        } ?: emptyList()
}

rootProject.name = "kews"
include(":umbrella")
include(":util")
include(":compose-ui")
include(":app-android")
include(":app-desktop")
include(":presentation-mappers")
include(subprojects("core"))
include(subprojects("data"))
include(subprojects("domain"))
include(subprojects("presentation-voyager"))
include(subprojects("presentation-decompose"))
