plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.ktor")
    id("kews.convention.multiplatform-settings")
    id("kews.convention.arrow")
    id("kews.convention.app-config")
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:network"))
            implementation(project(":core:database"))
            implementation(project(":domain:news"))
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.data.news"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
    }
}