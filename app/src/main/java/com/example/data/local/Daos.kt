package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PinnedNoteDao {
    @Query("SELECT * FROM pinned_notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<PinnedNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<PinnedNoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: PinnedNoteEntity)

    @Update
    suspend fun updateNote(note: PinnedNoteEntity)

    @Query("DELETE FROM pinned_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Int)
}

@Dao
interface SparkRecordDao {
    @Query("SELECT * FROM spark_records ORDER BY sparkNumber DESC")
    fun getAllSparks(): Flow<List<SparkRecordEntity>>

    @Query("SELECT * FROM spark_records WHERE sparkNumber = :number LIMIT 1")
    suspend fun getSparkByNumber(number: Int): SparkRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSparks(sparks: List<SparkRecordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpark(spark: SparkRecordEntity)

    @Update
    suspend fun updateSpark(spark: SparkRecordEntity)
}

@Dao
interface PackingItemDao {
    @Query("SELECT * FROM packing_items ORDER BY id ASC")
    fun getAllItems(): Flow<List<PackingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<PackingItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PackingItemEntity)

    @Update
    suspend fun updateItem(item: PackingItemEntity)

    @Query("DELETE FROM packing_items WHERE id = :id")
    suspend fun deleteItemById(id: Int)
}

@Dao
interface TimeCapsuleDao {
    @Query("SELECT * FROM time_capsules ORDER BY id ASC")
    fun getAllCapsules(): Flow<List<TimeCapsuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsules(capsules: List<TimeCapsuleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsule(capsule: TimeCapsuleEntity)
}

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist_items ORDER BY id ASC")
    fun getAllWatchlistItems(): Flow<List<WatchlistItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<WatchlistItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WatchlistItemEntity)

    @Update
    suspend fun updateItem(item: WatchlistItemEntity)
}

@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itinerary_spots ORDER BY id DESC")
    fun getAllSpots(): Flow<List<ItinerarySpotEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpots(spots: List<ItinerarySpotEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpot(spot: ItinerarySpotEntity)
}
