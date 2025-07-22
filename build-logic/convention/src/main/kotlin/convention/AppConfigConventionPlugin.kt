package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import util.AppConfig

class AppConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.extraProperties.apply {
            set("appId", AppConfig.APPLICATION_ID)
            set("compileSdk", AppConfig.COMPILE_SDK)
            set("minSdk", AppConfig.MIN_SDK)
            set("targetSdk", AppConfig.TARGET_SDK)
            set("versionCode", AppConfig.VERSION_CODE)
            set("versionName", AppConfig.VERSION_NAME)
        }
    }
}