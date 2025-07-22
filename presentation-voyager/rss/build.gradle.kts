import util.compose

plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.compose")
    id("kews.convention.koin")
    id("kews.convention.arrow")
    id("kews.convention.voyager")
    id("kews.convention.app-config")
}

kotlin {

    sourceSets {
        androidMain.dependencies {
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        nativeMain.dependencies {
        }
        dependencies {
        }
    }
}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.presentationvoyager.rss"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
    }
}