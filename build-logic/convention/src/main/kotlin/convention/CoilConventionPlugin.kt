package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class CoilConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying CoilConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {

                sourceSets.commonMain.dependencies {
                    implementation(
                        versionCatalog.findLibrary("coil-network-ktor3")
                            .orElseThrow { IllegalArgumentException("coil-network-ktor3 not found in the version catalog") }
                    )

                    // Add core Coil dependencies
                    implementation(
                        versionCatalog.findLibrary("coil-mp")
                            .orElseThrow { IllegalArgumentException("coil-mp not found in the version catalog") }
                    )

                    implementation(
                        versionCatalog.findLibrary("coil-compose-core")
                            .orElseThrow { IllegalArgumentException("coil-compose-core not found in the version catalog") }
                    )
                    implementation(
                        versionCatalog.findLibrary("coil-compose")
                            .orElseThrow { IllegalArgumentException("coil-compose not found in the version catalog") }
                    )
                }

            }
        }
    }
}