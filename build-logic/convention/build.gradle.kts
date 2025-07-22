import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)

}

gradlePlugin {
    plugins {
        register("androidLibraryConvention") {
            id = "kews.convention.android.library"
            implementationClass = "convention.AndroidLibraryConventionPlugin"
        }

        register("ktorConvention") {
            id = "kews.convention.ktor"
            implementationClass = "convention.KtorConventionPlugin"
        }

        register("voyagerConvention") {
            id = "kews.convention.voyager"
            implementationClass = "convention.VoyagerConventionPlugin"
        }

        register("decomposeConvention") {
            id = "kews.convention.decompose"
            implementationClass = "convention.DecomposeConventionPlugin"
        }

        register("kotlinMultiplatformConvention") {
            id = "kews.convention.kmp"
            implementationClass = "convention.KotlinMultiplatformConventionPlugin"
        }

        register("composeMultiplatformConvention") {
            id = "kews.convention.compose"
            implementationClass = "convention.ComposeMultiplatformConventionPlugin"
        }

        register("koinConvention") {
            id = "kews.convention.koin"
            implementationClass = "convention.KoinConventionPlugin"
        }

        register("coilConvention") {
            id = "kews.convention.coil"
            implementationClass = "convention.CoilConventionPlugin"
        }

        register("multiplatformSettingsConvention") {
            id = "kews.convention.multiplatform-settings"
            implementationClass = "convention.MultiplatformSettingsConventionPlugin"
        }

        register("arrowConvention") {
            id = "kews.convention.arrow"
            implementationClass = "convention.ArrowConventionPlugin"
        }

        register("gitLiveFirebaseConvention") {
            id = "kews.convention.gitlive-firebase"
            implementationClass = "convention.GitLiveFirebaseConventionPlugin"
        }

        register("appConfigConvention") {
            id = "kews.convention.app-config"
            implementationClass = "convention.AppConfigConventionPlugin"
        }
    }
}