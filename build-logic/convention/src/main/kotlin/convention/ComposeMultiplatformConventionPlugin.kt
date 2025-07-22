package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.compose
import util.versionCatalog

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {


        with(target) {
            with(pluginManager) {
                // Convention Plugin applies plugin ID → Gradle checks build-logic classpath →
                // Finds version from build-logic's dependencies → Resolves via version catalog

                val composeMPPluginId = versionCatalog.findPlugin("jetbrains-compose")
                    .orElseThrow { IllegalArgumentException("composeMultiplatform plugin not found in the version catalog") }
                val composeCompilerPluginId = versionCatalog.findPlugin("kotlin-compose")
                    .orElseThrow { IllegalArgumentException("composeCompiler plugin not found in the version catalog") }

                apply(composeMPPluginId.get().pluginId)
                apply(composeCompilerPluginId.get().pluginId)

                logger.lifecycle("Applying ComposeMultiplatformConventionPlugin to ${target.name}")
            }

            extensions.configure<KotlinMultiplatformExtension> {

                sourceSets.androidMain.dependencies {
                    implementation(compose.preview)
                    implementation(compose.components.uiToolingPreview)
                    implementation(versionCatalog.findLibrary("compose-ui-tooling").get())
                    implementation(versionCatalog.findLibrary("androidx-activity-compose").get())
                }

                sourceSets.commonMain.dependencies {
                    implementation(compose.runtime)
                    implementation(compose.foundation)
                    implementation(compose.material3)
                    implementation(compose.ui)
                    implementation(compose.components.resources)
                    implementation(compose.materialIconsExtended)
                    implementation(versionCatalog.findLibrary("androidx-lifecycle-runtime-compose").get())
                    implementation(versionCatalog.findLibrary("calf-permissions").get())
                    implementation(versionCatalog.findLibrary("kmpNotifier").get())
                }

            }
        }
    }
}

