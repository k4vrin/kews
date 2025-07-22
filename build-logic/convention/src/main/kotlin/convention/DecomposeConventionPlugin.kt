package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class DecomposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying VoyagerConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {
                // Configure dependencies for the commonMain source set
                sourceSets.commonMain.dependencies {

                    api(
                        versionCatalog.findLibrary("decompose-decompose")
                            .orElseThrow { IllegalArgumentException("decompose-decompose not found in the version catalog") }
                    )
                    api(
                        versionCatalog.findLibrary("decompose-extensionsComposeJetbrains")
                            .orElseThrow { IllegalArgumentException("decompose-extensionsComposeJetbrains not found in the version catalog") }
                    )
                    api(
                        versionCatalog.findLibrary("essenty-lifecycle")
                            .orElseThrow { IllegalArgumentException("essenty-lifecycle not found in the version catalog") }
                    )
                }

            }
        }
    }
}