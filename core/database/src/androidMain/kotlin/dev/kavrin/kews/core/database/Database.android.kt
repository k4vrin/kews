package dev.kavrin.kews.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.jvm.java

actual class DatabaseFactory(
    private val context: Context,
) {
    actual fun create(): RoomDatabase.Builder<KewsDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(KewsDatabase.DATABASE_NAME)
        return Room.databaseBuilder(
            context = appContext,
            klass = KewsDatabase::class.java,
            name = dbFile.absolutePath
        )
    }
}

actual val databasePlatformModule: Module = module {
    single {
        DatabaseFactory(
            context = get()
        )
    }
}