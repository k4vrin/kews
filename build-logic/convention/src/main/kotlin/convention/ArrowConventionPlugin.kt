package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class ArrowConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying ArrowConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    // Add Arrow libraries
                    implementation(
                        versionCatalog.findLibrary("arrow-core")
                            .orElseThrow { IllegalArgumentException("arrow-core not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("arrow-fx-coroutines")
                            .orElseThrow { IllegalArgumentException("arrow-fx-coroutines not found in the version catalog") }
                    )
                }
            }
        }
    }
}