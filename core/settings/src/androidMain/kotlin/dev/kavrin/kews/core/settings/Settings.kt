@file:OptIn(ExperimentalSettingsApi::class, ExperimentalSettingsImplementation::class)

package dev.kavrin.kews.core.settings

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.let

actual val settingsModule: Module = module {
    single {
        provideSuspendSettings(
            get()
        )
    }
}


private fun provideSuspendSettings(context: Context): SuspendSettings {
    return PreferenceDataStoreFactory.create(
        produceFile = {
            context.applicationContext.preferencesDataStoreFile("lovestory_settings")
        }
    ).let {
        DataStoreSettings(it)
    }
}
