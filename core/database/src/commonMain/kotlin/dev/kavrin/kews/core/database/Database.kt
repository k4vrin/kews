package dev.kavrin.kews.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect val databasePlatformModule: Module

val databaseModule = module {
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

}

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<KewsDatabase>
}

@Database(
    entities = [
        DummyEntity::class
    ],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(DatabaseConstructor::class)
abstract class KewsDatabase : RoomDatabase() {

    companion object Companion {
        const val DATABASE_NAME = "kews_database"
    }
}

object StringListTypeConverter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        return Json.encodeToString(value)
    }
}

@Suppress("KotlinNoActualForExpect", "NO_ACTUAL_FOR_EXPECT")
expect object DatabaseConstructor : RoomDatabaseConstructor<KewsDatabase> {
    override fun initialize(): KewsDatabase
}

@Entity
data class DummyEntity(
    @PrimaryKey
    val id: Int
)