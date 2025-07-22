package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.configureIosFrameworks
import util.versionCatalog

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {


        with(target) {
            with(pluginManager) {
                // Convention Plugin applies plugin ID → Gradle checks build-logic classpath →
                // Finds version from build-logic's dependencies → Resolves via version catalog

                val kmpPluginId = versionCatalog.findPlugin("kotlin-multiplatform")
                    .orElseThrow { IllegalArgumentException("kotlinMultiplatform plugin not found in the version catalog") }

                apply(kmpPluginId.get().pluginId)

                logger.lifecycle("Applying KotlinMultiplatformConventionPlugin to ${target.name}")
            }


            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()
                jvmToolchain(17)
                // com.android.library or com.android.application should already have benn added to target module
                androidTarget {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
                configureIosFrameworks()

                sourceSets.commonMain.dependencies {
                    implementation(versionCatalog.findLibrary("kermit-logger").get())
                    implementation(versionCatalog.findLibrary("kotlinx-coroutines-core").get())
                }

                sourceSets.all {
                    languageSettings.apply {
                        optIn("kotlin.Experimental")
                        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                        optIn("com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi")
                        optIn("com.slapps.cupertino.ExperimentalCupertinoApi")
                        optIn("com.russhwolf.settings.ExperimentalSettingsApi")
                        optIn("kotlinx.cinterop.ExperimentalForeignApi")
                        optIn("kotlinx.coroutines.FlowPreview")
                        optIn("androidx.compose.material3.ExperimentalMaterial3Api")

                    }
                }
            }
        }
    }
}

