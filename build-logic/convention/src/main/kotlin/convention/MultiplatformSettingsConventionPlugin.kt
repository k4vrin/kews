package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class MultiplatformSettingsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying MultiplatformSettingsConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {
                // Configure dependencies for the commonMain source set
                sourceSets.commonMain.dependencies {
                    // Add core multiplatform-settings dependencies
                    implementation(
                        versionCatalog.findLibrary("multiplatform-settings")
                            .orElseThrow { IllegalArgumentException("multiplatform-settings not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("multiplatform-settings-coroutines")
                            .orElseThrow { IllegalArgumentException("multiplatform-settings-coroutines not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("multiplatform-settings-serialization")
                            .orElseThrow { IllegalArgumentException("multiplatform-settings-serialization not found in the version catalog") }
                    )
                }

                // Configure Android-specific dependencies in the androidMain source set
                sourceSets.androidMain.dependencies {
                    implementation(
                        versionCatalog.findLibrary("multiplatform-settings-datastore")
                            .orElseThrow { IllegalArgumentException("multiplatform-settings-datastore not found in the version catalog") }
                    )
                }
            }
        }
    }
}