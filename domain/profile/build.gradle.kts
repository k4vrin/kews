plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.koin")
    id("kews.convention.arrow")
    id("kews.convention.app-config")
}

kotlin {

    sourceSets {
        commonMain.dependencies {
        }
    }
}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.domain.rss"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
    }
}