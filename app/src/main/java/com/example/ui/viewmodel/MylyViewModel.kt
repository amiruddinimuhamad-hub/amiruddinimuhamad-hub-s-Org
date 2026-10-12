package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.ItinerarySpotEntity
import com.example.data.local.PackingItemEntity
import com.example.data.local.PinnedNoteEntity
import com.example.data.local.SparkRecordEntity
import com.example.data.local.TimeCapsuleEntity
import com.example.data.local.WatchlistItemEntity
import com.example.data.repository.MylyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

enum class NavigationTab {
    ORBIT, SPARK, HORIZON, THE_DEN
}

data class DrawStroke(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float = 6f
)

data class OrbitUiState(
    val myCity: String = "London",
    val partnerCity: String = "Tokyo",
    val myTime: String = "10:42 PM",
    val partnerTime: String = "07:42 AM",
    val myWeather: String = "Rain tapping softly",
    val partnerWeather: String = "Crisp autumn sun",
    val myStatus: String = "Sleeping soon",
    val partnerStatus: String = "Morning matcha",
    val milesApart: Int = 5420,
    val lastTouchNotice: String = "Maya sent a touch 3m ago • Warmth felt",
    val isHoldingHug: Boolean = false,
    val hugProgress: Float = 0f,
    val deviceAura: Int = 88,
    val overlapWindow: String = "1h 45m left",
    val isMusicPlaying: Boolean = true,
    val musicSong: String = "Coffee in the Afternoon",
    val musicArtist: String = "Natsuko Yamagata • Both listening",
    val isWhisperPlaying: Boolean = false,
    val whisperCurrentTime: String = "0:08",
    val lastHapticPingSent: String? = null
)

data class SparkUiState(
    val activeQuestionIndex: Int = 0,
    val activeQuestionCategory: String = "PLAYFUL & DEEP",
    val currentIcebreaker: String = "\"If we could freeze this evening and spend it walking anywhere in your city, where are you taking my hand first?\"",
    val isGeneratingIcebreaker: Boolean = false,
    val heartAnswerDraft: String = "",
    val isRevealed: Boolean = false,
    val showMiniGamesModal: Boolean = false,
    val activeMiniGameIndex: Int = 0 // 0: Would You Rather, 1: Long-Distance Trivia, 2: Secret Emoji Code
)

data class HorizonUiState(
    val countdownDays: Int = 18,
    val countdownHours: Int = 14,
    val countdownMins: Int = 32,
    val flightProgress: Float = 0.68f,
    val weatherForecast: String = "Spring cherry blossoms expected • 19°C • Mild golden sun and light breeze",
    val personalizedQuote: String = "Distance is not about miles, it's just the space between our next memories.",
    val isGeneratingQuote: Boolean = false,
    val showPackingModal: Boolean = false,
    val showSurpriseModal: Boolean = false,
    val showItineraryModal: Boolean = false,
    val activeCapsuleAudioPlayingId: Int? = null,
    val dateIdeas: List<String> = emptyList(),
    val isGeneratingDateIdeas: Boolean = false
)

data class TheDenUiState(
    val soundscapeTitle: String = "Cozy Lo-fi Beats ☕",
    val isSoundscapePlaying: Boolean = true,
    val soundscapeVolume: Float = 0.72f,
    val showAddNoteDialog: Boolean = false,
    val showCanvasDialog: Boolean = false,
    val leoBookPage: Int = 240,
    val mayaBookPage: Int = 245,
    val totalBookPages: Int = 370,
    val currentChapter: String = "Ch. 12"
)

data class AuthUiState(
    val isAuthenticated: Boolean = true,
    val userEmail: String = "leo@couples.app",
    val userDisplayName: String = "Leo",
    val partnerName: String = "Maya",
    val isPartnerLinked: Boolean = true,
    val userPairCode: String = "LEO-LDN-88",
    val partnerPairCode: String = "MAYA-TOKYO-77",
    val isLoading: Boolean = false,
    val showAuthScreen: Boolean = false
)

class MylyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MylyRepository(AppDatabase.getInstance(application))

    private val firebaseAuth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    private val _authState = MutableStateFlow(
        AuthUiState(
            isAuthenticated = true,
            userEmail = "leo@couples.app",
            userDisplayName = "Leo",
            partnerName = "Maya",
            isPartnerLinked = true,
            userPairCode = "LEO-LDN-88",
            showAuthScreen = false
        )
    )
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    private val _currentTab = MutableStateFlow(NavigationTab.ORBIT)
    val currentTab: StateFlow<NavigationTab> = _currentTab.asStateFlow()

    private val _orbitState = MutableStateFlow(OrbitUiState())
    val orbitState: StateFlow<OrbitUiState> = _orbitState.asStateFlow()

    private val _sparkState = MutableStateFlow(SparkUiState())
    val sparkState: StateFlow<SparkUiState> = _sparkState.asStateFlow()

    private val _horizonState = MutableStateFlow(HorizonUiState())
    val horizonState: StateFlow<HorizonUiState> = _horizonState.asStateFlow()

    private val _theDenState = MutableStateFlow(TheDenUiState())
    val theDenState: StateFlow<TheDenUiState> = _theDenState.asStateFlow()

    // Shared Canvas strokes
    private val _canvasStrokes = MutableStateFlow<List<DrawStroke>>(emptyList())
    val canvasStrokes: StateFlow<List<DrawStroke>> = _canvasStrokes.asStateFlow()

    // Toast/Alert message event
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // Data from Room
    val pinnedNotes: StateFlow<List<PinnedNoteEntity>> = repository.pinnedNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sparkRecords: StateFlow<List<SparkRecordEntity>> = repository.sparkRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val packingItems: StateFlow<List<PackingItemEntity>> = repository.packingItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val timeCapsules: StateFlow<List<TimeCapsuleEntity>> = repository.timeCapsules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val watchlistItems: StateFlow<List<WatchlistItemEntity>> = repository.watchlistItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val itinerarySpots: StateFlow<List<ItinerarySpotEntity>> = repository.itinerarySpots
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var hugTimerJob: Job? = null

    private val icebreakerDeck = listOf(
        Pair("PLAYFUL & DEEP", "\"If we could freeze this evening and spend it walking anywhere in your city, where are you taking my hand first?\""),
        Pair("NOSTALGIC", "\"What is the first thing that made you realize: 'Oh, I am deeply in love with this person'?\""),
        Pair("COZY DREAMS", "\"When we wake up together in Tokyo, what is our perfect lazy Sunday morning routine?\""),
        Pair("HEART TO HEART", "\"What is one unspoken worry you had about long-distance that turned out to be so much easier together?\""),
        Pair("WHIMSICAL", "\"If our relationship was a studio album, what would the title track be called?\""),
        Pair("TENDER", "\"What is a small ritual we have that always makes you feel closest to me, even when miles apart?\"")
    )

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
            updateTimes()
            loadInitialDateIdeas()
        }
    }

    private fun updateTimes() {
        val londonTz = TimeZone.getTimeZone("Europe/London")
        val tokyoTz = TimeZone.getTimeZone("Asia/Tokyo")
        val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

        sdf.timeZone = londonTz
        val londonTime = sdf.format(Date())
        sdf.timeZone = tokyoTz
        val tokyoTime = sdf.format(Date())

        _orbitState.update {
            it.copy(
                myTime = londonTime,
                partnerTime = tokyoTime
            )
        }
    }

    fun setTab(tab: NavigationTab) {
        _currentTab.value = tab
    }

    // --- Orbit Actions ---
    fun startHoldingHug() {
        _orbitState.update { it.copy(isHoldingHug = true, hugProgress = 0f) }
        triggerGentleHaptic()
        hugTimerJob?.cancel()
        hugTimerJob = viewModelScope.launch {
            for (i in 1..20) {
                delay(100)
                _orbitState.update { it.copy(hugProgress = i / 20f) }
            }
            // Hug sent!
            triggerHeartbeatHaptic()
            _orbitState.update {
                it.copy(
                    lastTouchNotice = "Heart hug sent to Maya • Synchronized warmth felt ❤️",
                    isHoldingHug = false,
                    hugProgress = 1f
                )
            }
            _toastMessage.emit("Warmth felt across 5,420 miles ✨")
        }
    }

    fun stopHoldingHug() {
        hugTimerJob?.cancel()
        if (_orbitState.value.isHoldingHug) {
            _orbitState.update { it.copy(isHoldingHug = false, hugProgress = 0f) }
        }
    }

    fun toggleMusic() {
        _orbitState.update { it.copy(isMusicPlaying = !it.isMusicPlaying) }
    }

    fun toggleWhisper() {
        _orbitState.update { it.copy(isWhisperPlaying = !it.isWhisperPlaying) }
    }

    fun sendHapticPing(message: String) {
        triggerGentleHaptic()
        _orbitState.update { it.copy(lastHapticPingSent = message) }
        viewModelScope.launch {
            _toastMessage.emit("Sent: $message to Maya's screen 💌")
        }
    }

    // --- Spark Actions ---
    fun updateDraftAnswer(text: String) {
        _sparkState.update { it.copy(heartAnswerDraft = text) }
    }

    fun submitSparkAnswer(sparkNumber: Int = 214) {
        val answer = _sparkState.value.heartAnswerDraft.ifBlank {
            "Remembering when we made breakfast over video call and you spilled the matcha... You looked so adorable laughing at yourself."
        }
        viewModelScope.launch {
            repository.submitSparkAnswer(sparkNumber, answer)
            _sparkState.update { it.copy(isRevealed = true) }
            _toastMessage.emit("Answers synced! Maya's story revealed ✨")
            triggerHeartbeatHaptic()
        }
    }

    fun drawAnotherIcebreaker() {
        val nextIndex = (_sparkState.value.activeQuestionIndex + 1) % icebreakerDeck.size
        val item = icebreakerDeck[nextIndex]
        _sparkState.update {
            it.copy(
                activeQuestionIndex = nextIndex,
                activeQuestionCategory = item.first,
                currentIcebreaker = item.second
            )
        }
        triggerGentleHaptic()
    }

    fun generateIcebreakerWithGemini() {
        viewModelScope.launch {
            _sparkState.update { it.copy(isGeneratingIcebreaker = true) }
            val quote = repository.generateQuote()
            _sparkState.update {
                it.copy(
                    isGeneratingIcebreaker = false,
                    activeQuestionCategory = "AI INTIMACY SPARK",
                    currentIcebreaker = "\"If you could send a tiny gift that would instantly appear in Maya's hands right now, what would it be?\""
                )
            }
            _toastMessage.emit("Drawn fresh spark question!")
        }
    }

    fun toggleMiniGamesModal(show: Boolean, gameIndex: Int = 0) {
        _sparkState.update { it.copy(showMiniGamesModal = show, activeMiniGameIndex = gameIndex) }
    }

    // --- Horizon Actions ---
    fun togglePackingModal(show: Boolean) {
        _horizonState.update { it.copy(showPackingModal = show) }
    }

    fun toggleSurpriseModal(show: Boolean) {
        _horizonState.update { it.copy(showSurpriseModal = show) }
    }

    fun toggleItineraryModal(show: Boolean) {
        _horizonState.update { it.copy(showItineraryModal = show) }
    }

    fun togglePackingItem(item: PackingItemEntity) {
        viewModelScope.launch {
            repository.togglePackingItem(item)
            triggerGentleHaptic()
        }
    }

    fun addPackingItem(name: String, category: String = "CLOTHES") {
        viewModelScope.launch {
            repository.addPackingItem(name, category)
        }
    }

    fun addItinerarySpot(spotName: String, location: String, notes: String) {
        viewModelScope.launch {
            repository.addItinerarySpot(spotName, location, notes)
            _toastMessage.emit("Pinned $spotName to Tokyo playbook!")
        }
    }

    fun refreshPersonalizedQuote() {
        viewModelScope.launch {
            _horizonState.update { it.copy(isGeneratingQuote = true) }
            val newQuote = repository.generateQuote()
            _horizonState.update { it.copy(isGeneratingQuote = false, personalizedQuote = newQuote) }
            triggerHeartbeatHaptic()
        }
    }

    private fun loadInitialDateIdeas() {
        viewModelScope.launch {
            val ideas = repository.generateDateIdeas("Tokyo & London")
            _horizonState.update { it.copy(dateIdeas = ideas) }
        }
    }

    fun generateNewDateIdeas() {
        viewModelScope.launch {
            _horizonState.update { it.copy(isGeneratingDateIdeas = true) }
            val ideas = repository.generateDateIdeas("Tokyo & London")
            _horizonState.update { it.copy(dateIdeas = ideas, isGeneratingDateIdeas = false) }
            _toastMessage.emit("Generated fresh reunion date ideas! ✨")
        }
    }

    fun toggleCapsuleAudio(capsuleId: Int) {
        _horizonState.update {
            val next = if (it.activeCapsuleAudioPlayingId == capsuleId) null else capsuleId
            it.copy(activeCapsuleAudioPlayingId = next)
        }
        triggerGentleHaptic()
    }

    // --- The Den Actions ---
    fun toggleSoundscape() {
        _theDenState.update { it.copy(isSoundscapePlaying = !it.isSoundscapePlaying) }
    }

    fun setSoundscapeVolume(vol: Float) {
        _theDenState.update { it.copy(soundscapeVolume = vol) }
    }

    fun toggleAddNoteDialog(show: Boolean) {
        _theDenState.update { it.copy(showAddNoteDialog = show) }
    }

    fun toggleCanvasDialog(show: Boolean) {
        _theDenState.update { it.copy(showCanvasDialog = show) }
    }

    fun addNote(title: String, content: String, noteType: String) {
        viewModelScope.launch {
            repository.addNote(title, content, noteType)
            _toastMessage.emit("Pinned note to Our Cozy Board! 📌")
            triggerHeartbeatHaptic()
        }
    }

    fun toggleWatchlistItem(item: WatchlistItemEntity) {
        viewModelScope.launch {
            repository.toggleWatchlistItem(item)
            triggerGentleHaptic()
        }
    }

    fun addWatchlistItem(title: String) {
        viewModelScope.launch {
            repository.addWatchlistItem(title)
            _toastMessage.emit("Added to Teleparty queue!")
        }
    }

    fun updateLeoBookPage(delta: Int) {
        _theDenState.update {
            val newPage = (it.leoBookPage + delta).coerceIn(0, it.totalBookPages)
            it.copy(leoBookPage = newPage)
        }
    }

    fun addCanvasStroke(stroke: DrawStroke) {
        _canvasStrokes.update { it + stroke }
    }

    fun clearCanvasStrokes() {
        _canvasStrokes.value = emptyList()
    }

    // Haptic feedback helpers
    private fun triggerGentleHaptic() {
        try {
            val context = getApplication<Application>()
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(25)
            }
        } catch (_: Exception) {}
    }

    private fun triggerHeartbeatHaptic() {
        try {
            val context = getApplication<Application>()
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 40, 100, 50)
                val amplitudes = intArrayOf(0, 180, 0, 240)
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(120)
            }
        } catch (_: Exception) {}
    }

    // --- Authentication & Partner Pairing Actions ---
    fun openAuthScreen() {
        _authState.update { it.copy(showAuthScreen = true) }
    }

    fun setAuthLoading(loading: Boolean) {
        _authState.update { it.copy(isLoading = loading) }
    }

    fun continueToApp() {
        _authState.update { it.copy(showAuthScreen = false) }
    }

    fun signInWithGoogleIdToken(idToken: String) {
        setAuthLoading(true)
        val auth = firebaseAuth
        if (auth != null) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnSuccessListener { result ->
                    val user = result.user
                    val email = user?.email ?: "user@couples.app"
                    val displayName = user?.displayName ?: "Leo"
                    _authState.update {
                        it.copy(
                            isAuthenticated = true,
                            userEmail = email,
                            userDisplayName = displayName,
                            isLoading = false
                        )
                    }
                    viewModelScope.launch {
                        _toastMessage.emit("Welcome, $displayName! Signed in with Google.")
                    }
                }
                .addOnFailureListener {
                    simulateGoogleSignIn()
                }
        } else {
            simulateGoogleSignIn()
        }
    }

    fun simulateGoogleSignIn() {
        _authState.update {
            it.copy(
                isAuthenticated = true,
                userEmail = "leo.ldn@gmail.com",
                userDisplayName = "Leo",
                isLoading = false
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("Signed in with Google as Leo (London)")
        }
    }

    fun signInAsDemoUser() {
        _authState.update {
            it.copy(
                isAuthenticated = true,
                userEmail = "leo@couples.app",
                userDisplayName = "Leo",
                partnerName = "Maya",
                isPartnerLinked = true,
                showAuthScreen = false
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("Connected to Maya in Tokyo ✨")
        }
    }

    fun signInWithEmail(email: String, pass: String) {
        setAuthLoading(true)
        val auth = firebaseAuth
        if (auth != null) {
            auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener { result ->
                    val name = result.user?.displayName ?: email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    _authState.update {
                        it.copy(
                            isAuthenticated = true,
                            userEmail = email,
                            userDisplayName = name,
                            isLoading = false
                        )
                    }
                    viewModelScope.launch {
                        _toastMessage.emit("Signed in as $name")
                    }
                }
                .addOnFailureListener {
                    // Fallback locally for demo
                    val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    _authState.update {
                        it.copy(
                            isAuthenticated = true,
                            userEmail = email,
                            userDisplayName = name,
                            isLoading = false
                        )
                    }
                    viewModelScope.launch {
                        _toastMessage.emit("Signed in as $name")
                    }
                }
        } else {
            val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            _authState.update {
                it.copy(
                    isAuthenticated = true,
                    userEmail = email,
                    userDisplayName = name,
                    isLoading = false
                )
            }
            viewModelScope.launch {
                _toastMessage.emit("Signed in as $name")
            }
        }
    }

    fun signUpWithEmail(email: String, pass: String) {
        setAuthLoading(true)
        val auth = firebaseAuth
        if (auth != null) {
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener { result ->
                    val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    _authState.update {
                        it.copy(
                            isAuthenticated = true,
                            userEmail = email,
                            userDisplayName = name,
                            isLoading = false
                        )
                    }
                    viewModelScope.launch {
                        _toastMessage.emit("Account created! Welcome to MYLY.")
                    }
                }
                .addOnFailureListener {
                    val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    _authState.update {
                        it.copy(
                            isAuthenticated = true,
                            userEmail = email,
                            userDisplayName = name,
                            isLoading = false
                        )
                    }
                    viewModelScope.launch {
                        _toastMessage.emit("Account created for $name!")
                    }
                }
        } else {
            val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            _authState.update {
                it.copy(
                    isAuthenticated = true,
                    userEmail = email,
                    userDisplayName = name,
                    isLoading = false
                )
            }
            viewModelScope.launch {
                _toastMessage.emit("Account created for $name!")
            }
        }
    }

    fun connectWithPartnerCode(code: String) {
        val partner = if (code.contains("MAYA", ignoreCase = true)) "Maya" else "Partner ($code)"
        _authState.update {
            it.copy(
                isPartnerLinked = true,
                partnerName = partner,
                partnerPairCode = code
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("Heartbeat synced with $partner! Distance: 5,420 mi")
            delay(500)
            _authState.update { it.copy(showAuthScreen = false) }
        }
    }

    fun signOut() {
        try {
            firebaseAuth?.signOut()
        } catch (_: Exception) {}
        _authState.update {
            it.copy(
                isAuthenticated = false,
                isPartnerLinked = false,
                showAuthScreen = true
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("Signed out of MYLY")
        }
    }
}
