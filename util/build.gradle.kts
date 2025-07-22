@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.compose")
    id("kews.convention.koin")
    id("kews.convention.app-config")
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            // Decompose
            api(libs.decompose.decompose)
            api(libs.essenty.lifecycle)

        }

    }

}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.util"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
