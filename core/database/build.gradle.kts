plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.koin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("kews.convention.app-config")
}

kotlin {
    room {
        schemaDirectory("$projectDir/schemas")
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kermit.logger)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.kotlinx.serialization.json)
        }

        dependencies {
            ksp(libs.androidx.room.compiler)
        }
    }
}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.core.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.jvmVersion.get())
    }
}