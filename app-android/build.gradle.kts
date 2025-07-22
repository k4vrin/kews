@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    id("kews.convention.app-config")
}
kotlin {
    jvmToolchain(17)
}

android {
    namespace = "dev.kavrin.kews.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.kavrin.kews.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":umbrella"))
    implementation(project(":util"))
    implementation(project(":compose-ui"))
    implementation(project(":presentation-voyager:root"))
    implementation(project(":presentation-decompose:root"))
    implementation(libs.androidx.activity.compose)
    implementation(compose.foundation)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
