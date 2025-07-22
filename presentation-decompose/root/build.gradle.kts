plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.compose")
    id("kews.convention.koin")
    id("kews.convention.arrow")
    id("kews.convention.decompose")
    id("kews.convention.app-config")
}

kotlin {

    sourceSets {
        androidMain.dependencies {
        }

        val commonMain by getting {
            dependencies {
                implementation(project(":presentation-decompose:news"))
                implementation(project(":presentation-decompose:welcome"))
                implementation(project(":presentation-mappers"))
                implementation(project(":domain:news"))
                implementation(project(":compose-ui"))
                implementation(project(":util"))
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
    namespace = "${appId}.presentationdecompose.root"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
    }
}