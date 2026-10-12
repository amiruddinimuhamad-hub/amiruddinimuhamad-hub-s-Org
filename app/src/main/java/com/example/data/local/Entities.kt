package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pinned_notes")
data class PinnedNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val author: String, // "Maya" or "Leo"
    val timestamp: String,
    val noteType: String, // "PHOTO", "NUDGE", "PLANS", "STICKER", "ARCHIVE"
    val imageResName: String = "",
    val reply: String = "",
    val isFavorite: Boolean = false
)

@Entity(tableName = "spark_records")
data class SparkRecordEntity(
    @PrimaryKey val sparkNumber: Int,
    val dayStreak: Int,
    val question: String,
    val category: String, // "DAILY_RITUAL", "PLAYFUL_DEEP", "PAST"
    val mayaAnswer: String,
    val mayaTimestamp: String,
    val leoAnswer: String = "",
    val isLeoAnswered: Boolean = false,
    val dateLabel: String = ""
)

@Entity(tableName = "packing_items")
data class PackingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String, // "CLOTHES", "DOCUMENTS", "GIFTS", "TECH"
    val isChecked: Boolean = false,
    val packedBy: String = "Leo"
)

@Entity(tableName = "time_capsules")
data class TimeCapsuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subtitle: String,
    val description: String,
    val audioDuration: String = "",
    val isLocked: Boolean = false,
    val unlockDate: String = "",
    val capsuleType: String = "AUDIO" // "AUDIO", "NOTE", "MILESTONE"
)

@Entity(tableName = "watchlist_items")
data class WatchlistItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean = false,
    val rating: String = "",
    val scheduledNote: String = ""
)

@Entity(tableName = "itinerary_spots")
data class ItinerarySpotEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val spotName: String,
    val location: String,
    val notes: String,
    val category: String = "CAFE" // "CAFE", "PARK", "DINNER", "SIGHTSEEING"
)
