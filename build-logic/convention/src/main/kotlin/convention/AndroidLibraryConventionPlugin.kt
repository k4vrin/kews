package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.configureIosFrameworks
import util.versionCatalog

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {


        with(target) {
            with(pluginManager) {
                // Convention Plugin applies plugin ID → Gradle checks build-logic classpath →
                // Finds version from build-logic's dependencies → Resolves via version catalog

                val androidLibraryPluginId = versionCatalog.findPlugin("android-library")
                    .orElseThrow { IllegalArgumentException("android-library plugin not found in the version catalog") }

                apply(androidLibraryPluginId.get().pluginId)

                logger.lifecycle("Applying AndroidLibraryConventionPlugin to ${target.name}")
            }


            extensions.configure<KotlinMultiplatformExtension> {

                androidTarget {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
            }
        }
    }
}

