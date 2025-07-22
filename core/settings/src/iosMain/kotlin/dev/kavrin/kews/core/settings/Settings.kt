@file:OptIn(ExperimentalSettingsApi::class)

package dev.kavrin.kews.core.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val settingsModule: Module = module {
    single { provideSuspendSettings() }
}

private fun provideSuspendSettings(): SuspendSettings {
    return NSUserDefaults.standardUserDefaults
        .let { userDefaults ->
            NSUserDefaultsSettings(userDefaults).toSuspendSettings()
        }
}