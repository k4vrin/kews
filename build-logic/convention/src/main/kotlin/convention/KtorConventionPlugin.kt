package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class KtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying KtorConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {
                // Configure dependencies for the commonMain source set
                sourceSets.commonMain.dependencies {

                    // Add core Ktor dependencies
                    implementation(
                        versionCatalog.findLibrary("ktor-client-core")
                            .orElseThrow { IllegalArgumentException("ktor-client-core not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("ktor-client-logging")
                            .orElseThrow { IllegalArgumentException("ktor-client-logging not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("ktor-client-content-negotiation")
                            .orElseThrow { IllegalArgumentException("ktor-client-content-negotiation not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("ktor-client-serialization")
                            .orElseThrow { IllegalArgumentException("ktor-client-serialization not found in the version catalog") }
                    )
                }

                // Configure Android-specific dependencies in the androidMain source set
                sourceSets.androidMain.dependencies {
                    implementation(
                        versionCatalog.findLibrary("ktor-client-okhttp")
                            .orElseThrow { IllegalArgumentException("ktor-client-okhttp not found in the version catalog") }
                    )
                }

                // Configure iOS-specific dependencies in the iosMain source set
                sourceSets.iosMain.dependencies {
                    implementation(
                        versionCatalog.findLibrary("ktor-client-darwin")
                            .orElseThrow { IllegalArgumentException("ktor-client-darwin not found in the version catalog") }
                    )
                }
            }
        }
    }
}