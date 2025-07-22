package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying KoinConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {

                    // Add shared Koin libraries.
                    api(
                        versionCatalog.findLibrary("koin-core")
                            .orElseThrow { IllegalArgumentException("koin-core not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("koin-compose")
                            .orElseThrow { IllegalArgumentException("koin-compose not found in the version catalog") }
                    )
                }
                sourceSets.androidMain.dependencies {
                    // On Android, add the Koin Android module.
                    implementation(
                        versionCatalog.findLibrary("koin-android")
                            .orElseThrow { IllegalArgumentException("koin-android not found in the version catalog") }
                    )
                }
            }
        }
    }
}