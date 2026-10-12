package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.GlassCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.DeepBurgundy
import com.example.ui.theme.FrostedGlassCard
import com.example.ui.theme.LuminousCream
import com.example.ui.theme.SunsetOrange
import com.example.ui.theme.WarmEarthBrown
import com.example.ui.viewmodel.MylyViewModel

@Composable
fun OrbitScreen(
    viewModel: MylyViewModel,
    modifier: Modifier = Modifier
) {
    val orbitState by viewModel.orbitState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopHeader(onAvatarClick = { viewModel.openAuthScreen() })
        }

        // Dual Time Zone Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // London (You)
                TimeZoneCard(
                    modifier = Modifier.weight(1f),
                    city = orbitState.myCity,
                    time = orbitState.myTime,
                    role = "You",
                    weather = orbitState.myWeather,
                    weatherIcon = "💧",
                    status = orbitState.myStatus,
                    statusIcon = "🌙",
                    tagColor = Color(0xFF6C63FF).copy(alpha = 0.12f),
                    tagTextColor = Color(0xFF4A3DBB)
                )

                // Tokyo (Maya)
                TimeZoneCard(
                    modifier = Modifier.weight(1f),
                    city = orbitState.partnerCity,
                    time = orbitState.partnerTime,
                    role = "Maya",
                    weather = orbitState.partnerWeather,
                    weatherIcon = "☀️",
                    status = orbitState.partnerStatus,
                    statusIcon = "🍵",
                    tagColor = Color(0xFFE9C46A).copy(alpha = 0.22f),
                    tagTextColor = Color(0xFF9A6E00)
                )
            }
        }

        // Real-time Touch Hug Pad
        item {
            TouchHugCard(
                notice = orbitState.lastTouchNotice,
                isHolding = orbitState.isHoldingHug,
                progress = orbitState.hugProgress,
                milesApart = orbitState.milesApart,
                onStartHug = { viewModel.startHoldingHug() },
                onStopHug = { viewModel.stopHoldingHug() }
            )
        }

        // Stats Row (Device Aura + Overlap Window)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Device Aura
                GlassCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFF1D6)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = "Device Aura",
                                    tint = SunsetOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Device Aura",
                                    fontSize = 11.sp,
                                    color = WarmEarthBrown.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "${orbitState.deviceAura}% Synced",
                                    fontSize = 14.sp,
                                    color = WarmEarthBrown,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { orbitState.deviceAura / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = SunsetOrange,
                            trackColor = Color(0x33E76F51),
                        )
                    }
                }

                // Overlap Window
                GlassCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE3EFFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SyncAlt,
                                    contentDescription = "Overlap Window",
                                    tint = Color(0xFF4A6077),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Overlap Window",
                                    fontSize = 11.sp,
                                    color = WarmEarthBrown.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = orbitState.overlapWindow,
                                    fontSize = 14.sp,
                                    color = WarmEarthBrown,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Maya heads out soon...",
                            fontSize = 11.sp,
                            color = Color(0xFF6C63FF),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Listening Together - Live Broadcast
        item {
            ListeningTogetherCard(
                isPlaying = orbitState.isMusicPlaying,
                song = orbitState.musicSong,
                artist = orbitState.musicArtist,
                onTogglePlay = { viewModel.toggleMusic() }
            )
        }

        // Quick Whispers
        item {
            QuickWhispersCard(
                isPlaying = orbitState.isWhisperPlaying,
                whisperTime = orbitState.whisperCurrentTime,
                onTogglePlay = { viewModel.toggleWhisper() },
                onSendPing = { viewModel.sendHapticPing(it) }
            )
        }

        // Bottom space so content is never cut off by the floating nav bar
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun TimeZoneCard(
    modifier: Modifier = Modifier,
    city: String,
    time: String,
    role: String,
    weather: String,
    weatherIcon: String,
    status: String,
    statusIcon: String,
    tagColor: Color,
    tagTextColor: Color
) {
    GlassCard(
        modifier = modifier,
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(tagColor)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "• $city",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = tagTextColor
                    )
                }

                Text(
                    text = role,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarmEarthBrown.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = time,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = weatherIcon, fontSize = 11.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = weather,
                    fontSize = 11.sp,
                    color = WarmEarthBrown.copy(alpha = 0.75f),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(Color(0xFFF7F2EE))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$statusIcon $status",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarmEarthBrown
                )
            }
        }
    }
}

@Composable
private fun TouchHugCard(
    notice: String,
    isHolding: Boolean,
    progress: Float,
    milesApart: Int,
    onStartHug: () -> Unit,
    onStopHug: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hug_pulse"
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (isHolding) 0.94f else 1f,
        label = "hold_scale"
    )

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Notice pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(Color(0x99FFFFFF))
                    .border(1.dp, Color(0x33FF9A98), RoundedCornerShape(9999.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(SunsetOrange)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = notice,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmEarthBrown
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pulse Ring & Hold Button
            Box(
                modifier = Modifier.size(190.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outermost ambient glow ring
                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .scale(if (isHolding) 1.15f else pulseScale)
                        .clip(CircleShape)
                        .background(Color(0x22FF9A98))
                )

                // Middle soft glow ring
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .scale(if (isHolding) 1.08f else 1f)
                        .clip(CircleShape)
                        .background(Color(0x44FF9A98))
                )

                // Central Heart Hug Button
                Box(
                    modifier = Modifier
                        .size(118.dp)
                        .scale(animatedScale)
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            ambientColor = Color(0x334D0E12),
                            spotColor = Color(0x4DFF5A5A)
                        )
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFFF7A6E), Color(0xFFE75A5A))
                            )
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    onStartHug()
                                    tryAwaitRelease()
                                    onStopHug()
                                }
                            )
                        }
                        .testTag("hold_hug_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Heart",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHolding) "HOLDING..." else "HOLD HUG",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Orbit line between London & Tokyo
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "London",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarmEarthBrown.copy(alpha = 0.7f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$milesApart miles apart",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF964646)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(2.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0x334A2E27),
                                        SunsetOrange,
                                        Color(0x334A2E27)
                                    )
                                )
                            )
                    )
                }

                Text(
                    text = "Tokyo",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarmEarthBrown.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Connected under the same waxing gibbous moon 🌔",
                fontSize = 11.sp,
                color = WarmEarthBrown.copy(alpha = 0.65f),
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun ListeningTogetherCard(
    isPlaying: Boolean,
    song: String,
    artist: String,
    onTogglePlay: () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(SunsetOrange)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LISTENING TOGETHER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SunsetOrange,
                        letterSpacing = 0.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(Color(0xFFF7F2EE))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Live Broadcast",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmEarthBrown.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Album Art
                Image(
                    painter = painterResource(id = R.drawable.album_coffee),
                    contentDescription = "Album Art",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown,
                        maxLines = 1
                    )
                    Text(
                        text = artist,
                        fontSize = 12.sp,
                        color = WarmEarthBrown.copy(alpha = 0.65f),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // Animated Equalizer lines
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.height(10.dp)
                    ) {
                        EqualizerBar(heightFraction = if (isPlaying) 0.8f else 0.2f, color = SunsetOrange)
                        EqualizerBar(heightFraction = if (isPlaying) 0.5f else 0.2f, color = Color(0xFFE75A5A))
                        EqualizerBar(heightFraction = if (isPlaying) 1.0f else 0.2f, color = SunsetOrange)
                        EqualizerBar(heightFraction = if (isPlaying) 0.6f else 0.2f, color = Color(0xFFE75A5A))
                    }
                }

                // Play / Pause Button
                IconButton(
                    onClick = onTogglePlay,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF7F2EE))
                        .testTag("toggle_music_button")
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = WarmEarthBrown
                    )
                }
            }
        }
    }
}

@Composable
private fun EqualizerBar(heightFraction: Float, color: Color) {
    Box(
        modifier = Modifier
            .width(3.dp)
            .height((10 * heightFraction).dp)
            .clip(RoundedCornerShape(1.dp))
            .background(color)
    )
}

@Composable
private fun QuickWhispersCard(
    isPlaying: Boolean,
    whisperTime: String,
    onTogglePlay: () -> Unit,
    onSendPing: (String) -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✨", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Quick Whispers",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )
                }

                Text(
                    text = "Maya's lockscreen active",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = SunsetOrange
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Voice note player pill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(9999.dp))
                    .background(Color(0xFFF7F2EE))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onTogglePlay,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SunsetOrange)
                            .testTag("play_whisper_button")
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play note",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Maya's sleepy goodnight",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmEarthBrown
                        )
                        Text(
                            text = "0:14 • \"Can't wait to see you soon\"",
                            fontSize = 11.sp,
                            color = WarmEarthBrown.copy(alpha = 0.65f),
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Yesterday",
                            fontSize = 10.sp,
                            color = WarmEarthBrown.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Send instant haptic ping:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = WarmEarthBrown.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Instant pings row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PingChip(
                    label = "☕ Made your coffee",
                    onClick = { onSendPing("☕ Made your morning coffee") },
                    modifier = Modifier.weight(1f)
                )
                PingChip(
                    label = "🫂 Squeeze tight",
                    onClick = { onSendPing("🫂 Tight squeeze across the miles") },
                    modifier = Modifier.weight(1f)
                )
                PingChip(
                    label = "💋 Kiss",
                    onClick = { onSendPing("💋 Sweet kiss on your cheek") },
                    modifier = Modifier.weight(0.7f)
                )
            }
        }
    }
}

@Composable
private fun PingChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9999.dp))
            .background(Color(0xFFF7F2EE))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = WarmEarthBrown,
            maxLines = 1
        )
    }
}
