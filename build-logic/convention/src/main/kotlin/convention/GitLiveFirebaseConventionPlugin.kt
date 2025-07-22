package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import util.versionCatalog

class GitLiveFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("Applying GitLiveFirebaseConventionPlugin to ${target.name}")

            extensions.configure<KotlinMultiplatformExtension> {

                sourceSets.commonMain.dependencies {
                    // Add GitLive Firebase Auth
                    implementation(
                        versionCatalog.findLibrary("gitlive-firebase-auth")
                            .orElseThrow { IllegalArgumentException("gitlive-firebase-auth not found in the version catalog") }
                    )

                    // Add GitLive Firebase Database
                    implementation(
                        versionCatalog.findLibrary("gitlive-firebase-database")
                            .orElseThrow { IllegalArgumentException("gitlive-firebase-database not found in the version catalog") }
                    )

                    // Add GitLive Firebase Firestore
                    implementation(
                        versionCatalog.findLibrary("gitlive-firebase-firestore")
                            .orElseThrow { IllegalArgumentException("gitlive-firebase-firestore not found in the version catalog") }
                    )

                    // Add GitLive Firebase Functions
                    implementation(
                        versionCatalog.findLibrary("gitlive-firebase-functions")
                            .orElseThrow { IllegalArgumentException("gitlive-firebase-functions not found in the version catalog") }
                    )
                }
            }
        }
    }
}