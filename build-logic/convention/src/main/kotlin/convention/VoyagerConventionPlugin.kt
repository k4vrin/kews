package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class VoyagerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying VoyagerConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {
                // Configure dependencies for the commonMain source set
                sourceSets.commonMain.dependencies {

                    // Add core Ktor dependencies
                    api(
                        versionCatalog.findLibrary("voyager-navigator")
                            .orElseThrow { IllegalArgumentException("voyager-navigator not found in the version catalog") }
                    )
                    api(
                        versionCatalog.findLibrary("voyager-screenModel")
                            .orElseThrow { IllegalArgumentException("voyager-screenModel not found in the version catalog") }
                    )
                    api(
                        versionCatalog.findLibrary("voyager-bottomSheetNavigator")
                            .orElseThrow { IllegalArgumentException("voyager-bottomSheetNavigator not found in the version catalog") }
                    )
                    api(
                        versionCatalog.findLibrary("voyager-tabNavigator")
                            .orElseThrow { IllegalArgumentException("voyager-tabNavigator not found in the version catalog") }
                    )

                    api(
                        versionCatalog.findLibrary("voyager-transitions")
                            .orElseThrow { IllegalArgumentException("voyager-transitions not found in the version catalog") }
                    )

                    api(
                        versionCatalog.findLibrary("voyager-koin")
                            .orElseThrow { IllegalArgumentException("voyager-koin not found in the version catalog") }
                    )
                }

            }
        }
    }
}