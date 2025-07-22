package util

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val Project.versionCatalog
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun KotlinMultiplatformExtension.configureIosFrameworks() {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

val org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension.compose: org.jetbrains.compose.ComposePlugin.Dependencies
    get() = (this as org.gradle.api.plugins.ExtensionAware)
        .extensions.getByName("compose") as org.jetbrains.compose.ComposePlugin.Dependencies