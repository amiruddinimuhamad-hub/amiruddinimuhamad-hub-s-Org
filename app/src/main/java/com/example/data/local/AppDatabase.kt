package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PinnedNoteEntity::class,
        SparkRecordEntity::class,
        PackingItemEntity::class,
        TimeCapsuleEntity::class,
        WatchlistItemEntity::class,
        ItinerarySpotEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinnedNoteDao(): PinnedNoteDao
    abstract fun sparkRecordDao(): SparkRecordDao
    abstract fun packingItemDao(): PackingItemDao
    abstract fun timeCapsuleDao(): TimeCapsuleDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun itineraryDao(): ItineraryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myly_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
