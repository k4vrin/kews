@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.compose")
    id("kews.convention.coil")
    id("kews.convention.app-config")
    id("kews.convention.koin")
}

kotlin {

    sourceSets {
        commonMain.dependencies {

            implementation(project(":util"))

            implementation(libs.decompose.decompose)
            implementation(libs.decompose.extensionsComposeJetbrains)

        }
    }
}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.composeui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
