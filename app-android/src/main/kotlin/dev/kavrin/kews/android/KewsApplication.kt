package dev.kavrin.kews.android

import android.app.Application
import dev.kavrin.kews.umbrella.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class KewsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@KewsApplication)
            androidLogger(Level.DEBUG)
        }
    }
}