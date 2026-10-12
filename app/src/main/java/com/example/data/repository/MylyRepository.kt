package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.ItinerarySpotEntity
import com.example.data.local.PackingItemEntity
import com.example.data.local.PinnedNoteEntity
import com.example.data.local.SparkRecordEntity
import com.example.data.local.TimeCapsuleEntity
import com.example.data.local.WatchlistItemEntity
import com.example.data.remote.GeminiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class MylyRepository(
    private val database: AppDatabase,
    private val geminiService: GeminiService = GeminiService()
) {
    val pinnedNotes: Flow<List<PinnedNoteEntity>> = database.pinnedNoteDao().getAllNotes()
    val sparkRecords: Flow<List<SparkRecordEntity>> = database.sparkRecordDao().getAllSparks()
    val packingItems: Flow<List<PackingItemEntity>> = database.packingItemDao().getAllItems()
    val timeCapsules: Flow<List<TimeCapsuleEntity>> = database.timeCapsuleDao().getAllCapsules()
    val watchlistItems: Flow<List<WatchlistItemEntity>> = database.watchlistDao().getAllWatchlistItems()
    val itinerarySpots: Flow<List<ItinerarySpotEntity>> = database.itineraryDao().getAllSpots()

    suspend fun seedInitialDataIfEmpty() = withContext(Dispatchers.IO) {
        val notes = database.pinnedNoteDao().getAllNotes().firstOrNull()
        if (notes.isNullOrEmpty()) {
            database.pinnedNoteDao().insertNotes(
                listOf(
                    PinnedNoteEntity(
                        title = "Sunset matcha date",
                        content = "Oct 14 • London",
                        author = "Maya",
                        timestamp = "Oct 14",
                        noteType = "PHOTO",
                        imageResName = "sunset_matcha"
                    ),
                    PinnedNoteEntity(
                        title = "MAYA'S NUDGE",
                        content = "Remember to drink water dummy! ❤️ 🥛",
                        author = "Maya",
                        timestamp = "Today 11:40 AM",
                        noteType = "NUDGE"
                    ),
                    PinnedNoteEntity(
                        title = "FRIDAY PLANS?",
                        content = "Movie night this Friday 9pm your time? Popcorn ready! 🍿",
                        author = "Maya",
                        timestamp = "Today 9:15 AM",
                        noteType = "PLANS",
                        reply = "Leo replied: \"Yes please!!\""
                    ),
                    PinnedNoteEntity(
                        title = "STICKER",
                        content = "Always You",
                        author = "Maya",
                        timestamp = "Yesterday",
                        noteType = "STICKER",
                        imageResName = "sticker_bunny_dino"
                    ),
                    PinnedNoteEntity(
                        title = "PINNED FROM ARCHIVE",
                        content = "Dual time zone wat... \"Tick tock together even...\"",
                        author = "Leo",
                        timestamp = "Oct 1",
                        noteType = "ARCHIVE"
                    )
                )
            )
        }

        val sparks = database.sparkRecordDao().getAllSparks().firstOrNull()
        if (sparks.isNullOrEmpty()) {
            database.sparkRecordDao().insertSparks(
                listOf(
                    SparkRecordEntity(
                        sparkNumber = 214,
                        dayStreak = 214,
                        question = "What is a small, silly moment with me that made you smile this past month?",
                        category = "DAILY_RITUAL",
                        mayaAnswer = "When you tried making iced matcha with oat milk on FaceTime and ended up spraying matcha all over your white sweater! You looked so adorably stunned.",
                        mayaTimestamp = "8:42 AM • Tokyo time",
                        leoAnswer = "",
                        isLeoAnswered = false,
                        dateLabel = "Today"
                    ),
                    SparkRecordEntity(
                        sparkNumber = 213,
                        dayStreak = 213,
                        question = "What scent or sound instantly takes you straight back to our Lisbon trip?",
                        category = "PAST",
                        mayaAnswer = "Fresh cinnamon pastries at Pastéis de Belém on that rainy morning.",
                        mayaTimestamp = "Yesterday",
                        leoAnswer = "The ocean breeze by the Miradouro and hearing street guitarists playing fado.",
                        isLeoAnswered = true,
                        dateLabel = "Yesterday"
                    ),
                    SparkRecordEntity(
                        sparkNumber = 212,
                        dayStreak = 212,
                        question = "What is your comfort snack that you crave most when we are apart?",
                        category = "PAST",
                        mayaAnswer = "Warm taiyaki with sweet red bean paste from the shop near your flat.",
                        mayaTimestamp = "2 days ago",
                        leoAnswer = "Crunchy sea salt chocolate chip cookies right out of your oven.",
                        isLeoAnswered = true,
                        dateLabel = "2 days ago"
                    )
                )
            )
        }

        val packing = database.packingItemDao().getAllItems().firstOrNull()
        if (packing.isNullOrEmpty()) {
            database.packingItemDao().insertItems(
                listOf(
                    PackingItemEntity(name = "Passport & Japan Rail Pass", category = "DOCUMENTS", isChecked = true),
                    PackingItemEntity(name = "Universal Travel Power Adapters (2x)", category = "TECH", isChecked = true),
                    PackingItemEntity(name = "Fujifilm Instax Camera + Film packs", category = "TECH", isChecked = true),
                    PackingItemEntity(name = "Maya's favorite London lavender tea", category = "GIFTS", isChecked = true),
                    PackingItemEntity(name = "Matching oversized knit sweaters", category = "CLOTHES", isChecked = true),
                    PackingItemEntity(name = "Noise cancelling headphones for flight", category = "TECH", isChecked = true),
                    PackingItemEntity(name = "Prescriptions & travel skincare", category = "CLOTHES", isChecked = true),
                    PackingItemEntity(name = "Surprise anniversary gift box", category = "GIFTS", isChecked = false),
                    PackingItemEntity(name = "Compact umbrella for Tokyo drizzle", category = "CLOTHES", isChecked = false),
                    PackingItemEntity(name = "Walking shoes broken in for 20k steps", category = "CLOTHES", isChecked = false),
                    PackingItemEntity(name = "Suica card pre-loaded", category = "DOCUMENTS", isChecked = false),
                    PackingItemEntity(name = "Handwritten letters for the flight", category = "GIFTS", isChecked = false)
                )
            )
        }

        val capsules = database.timeCapsuleDao().getAllCapsules().firstOrNull()
        if (capsules.isNullOrEmpty()) {
            database.timeCapsuleDao().insertCapsules(
                listOf(
                    TimeCapsuleEntity(
                        title = "Open When: You're missing me on a rainy evening",
                        subtitle = "Available Now • 4 min listen",
                        description = "\"Recorded this while listening to the raindrops hitting the window sill in Seattle. Playing our favorite acoustic track and reminding you how much I adore you...\"",
                        audioDuration = "03:42",
                        isLocked = false,
                        capsuleType = "AUDIO"
                    ),
                    TimeCapsuleEntity(
                        title = "Open When: You board flight to Tokyo",
                        subtitle = "Locked until April 14, 2026",
                        description = "Sealed note + curated in-flight playlist for floating above the clouds.",
                        isLocked = true,
                        unlockDate = "April 14, 2026",
                        capsuleType = "NOTE"
                    ),
                    TimeCapsuleEntity(
                        title = "Open When: We celebrate 2 years together",
                        subtitle = "Locked Milestone",
                        description = "Video montage + handwritten letter locked until our anniversary celebration.",
                        isLocked = true,
                        unlockDate = "Anniversary Day",
                        capsuleType = "MILESTONE"
                    )
                )
            )
        }

        val watchlist = database.watchlistDao().getAllWatchlistItems().firstOrNull()
        if (watchlist.isNullOrEmpty()) {
            database.watchlistDao().insertItems(
                listOf(
                    WatchlistItemEntity(
                        title = "Spirited Away",
                        subtitle = "Studio Ghibli co-watch",
                        isCompleted = true,
                        rating = "10/10"
                    ),
                    WatchlistItemEntity(
                        title = "Everything Everywhere All At Once",
                        subtitle = "Movie Night",
                        isCompleted = false,
                        scheduledNote = "Scheduled this Friday 🍿"
                    ),
                    WatchlistItemEntity(
                        title = "Past Lives",
                        subtitle = "Recommended by Maya",
                        isCompleted = false,
                        scheduledNote = "Next in queue"
                    )
                )
            )
        }

        val itinerary = database.itineraryDao().getAllSpots().firstOrNull()
        if (itinerary.isNullOrEmpty()) {
            database.itineraryDao().insertSpots(
                listOf(
                    ItinerarySpotEntity(spotName = "Fuglen Coffee Asakusa", location = "Tokyo", notes = "Morning cinnamon buns and pour-overs together", category = "CAFE"),
                    ItinerarySpotEntity(spotName = "Shinjuku Gyoen Cherry Blossoms", location = "Tokyo", notes = "Picnic under Sakura trees at golden hour", category = "PARK"),
                    ItinerarySpotEntity(spotName = "TeamLab Planets", location = "Toyosu", notes = "Walking barefoot in the water and crystal lights", category = "SIGHTSEEING"),
                    ItinerarySpotEntity(spotName = "Gion Sunset Lantern Walk", location = "Kyoto", notes = "Exploring quiet alleys and tea houses", category = "SIGHTSEEING")
                )
            )
        }
    }

    // Pinned notes actions
    suspend fun addNote(title: String, content: String, noteType: String = "NUDGE") {
        database.pinnedNoteDao().insertNote(
            PinnedNoteEntity(
                title = title,
                content = content,
                author = "Leo",
                timestamp = "Just now",
                noteType = noteType
            )
        )
    }

    // Spark actions
    suspend fun submitSparkAnswer(sparkNumber: Int, answer: String) {
        val current = database.sparkRecordDao().getSparkByNumber(sparkNumber)
        if (current != null) {
            database.sparkRecordDao().updateSpark(
                current.copy(
                    leoAnswer = answer,
                    isLeoAnswered = true
                )
            )
        }
    }

    // Packing list actions
    suspend fun togglePackingItem(item: PackingItemEntity) {
        database.packingItemDao().updateItem(
            item.copy(isChecked = !item.isChecked)
        )
    }

    suspend fun addPackingItem(name: String, category: String = "CLOTHES") {
        database.packingItemDao().insertItem(
            PackingItemEntity(name = name, category = category, isChecked = false)
        )
    }

    // Watchlist actions
    suspend fun toggleWatchlistItem(item: WatchlistItemEntity) {
        database.watchlistDao().updateItem(
            item.copy(isCompleted = !item.isCompleted)
        )
    }

    suspend fun addWatchlistItem(title: String, scheduledNote: String = "Queue") {
        database.watchlistDao().insertItem(
            WatchlistItemEntity(title = title, subtitle = "Added by Leo", isCompleted = false, scheduledNote = scheduledNote)
        )
    }

    // Itinerary spot
    suspend fun addItinerarySpot(spotName: String, location: String, notes: String) {
        database.itineraryDao().insertSpot(
            ItinerarySpotEntity(spotName = spotName, location = location, notes = notes)
        )
    }

    // AI Helpers
    suspend fun generateDateIdeas(location: String): List<String> =
        geminiService.generateDateIdeas(location)

    suspend fun generateQuote(): String =
        geminiService.generatePersonalizedQuote()
}
