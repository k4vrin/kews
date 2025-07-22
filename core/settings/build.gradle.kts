plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.koin")
    id("kews.convention.app-config")
    id("kews.convention.multiplatform-settings")
}

kotlin {

    sourceSets {
        androidMain.dependencies {
            implementation(libs.datastore.preferences)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kermit.logger)
        }
    }
}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.core.settings"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
    }
}