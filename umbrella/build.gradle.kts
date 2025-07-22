@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.android.library)
    id("kews.convention.kmp")
    alias(libs.plugins.kotlin.serialization)
    id("kews.convention.compose")
    alias(libs.plugins.buildKonfig)
    id("kews.convention.app-config")
}

kotlin {
    jvmToolchain(17)
    applyDefaultHierarchyTemplate()
    
    androidTarget {
        compilations.all {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    targets
        .filterIsInstance<KotlinNativeTarget>()
        .filter { it.konanTarget.family == Family.IOS }
        .forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "shared"
                isStatic = true
                export(project(":presentation-decompose:root"))
                export(libs.decompose.decompose)
                export(libs.essenty.lifecycle)
            }
        }

    sourceSets {
        commonMain.dependencies {
            api(project(":core:network"))
            api(project(":data:news"))
            api(project(":util"))
            api(project(":presentation-decompose:root"))


            // Decompose
            api(libs.decompose.decompose)
            api(libs.essenty.lifecycle)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.logging)

            // Koin
            implementation(libs.koin.core)

            // Kotlinx
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }

    }

}

android {
    val appId = project.extra["appId"] as String
    namespace = "${appId}.umbrella"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

buildkonfig {
    packageName = "dev.kavrin.kews"

    defaultConfigs {
        @Suppress("UNCHECKED_CAST")
        val configs = rootProject.extra["configs"] as Map<String, String>
        configs.forEach { entry ->
            buildConfigField(FieldSpec.Type.STRING, entry.key.uppercase(), entry.value)
        }
    }
}
