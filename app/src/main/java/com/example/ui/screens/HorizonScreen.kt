package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.local.ItinerarySpotEntity
import com.example.data.local.PackingItemEntity
import com.example.data.local.TimeCapsuleEntity
import com.example.ui.components.GlassCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.DeepBurgundy
import com.example.ui.theme.LuminousCream
import com.example.ui.theme.SunsetOrange
import com.example.ui.theme.WarmEarthBrown
import com.example.ui.viewmodel.MylyViewModel

@Composable
fun HorizonScreen(
    viewModel: MylyViewModel,
    modifier: Modifier = Modifier
) {
    val horizonState by viewModel.horizonState.collectAsState()
    val packingItems by viewModel.packingItems.collectAsState()
    val timeCapsules by viewModel.timeCapsules.collectAsState()
    val itinerarySpots by viewModel.itinerarySpots.collectAsState()

    val packedCount = packingItems.count { it.isChecked }
    val totalPackingCount = packingItems.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopHeader(onAvatarClick = { viewModel.openAuthScreen() })
        }

        // Reunion Flight Countdown Hero Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FlightTakeoff,
                                contentDescription = "Flight",
                                tint = SunsetOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "NEXT HUG IN MOTION",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunsetOrange,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFFFF1D6))
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Tokyo, JP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB57C00)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Terminal 1 Arrival Gate • South Wing",
                        fontSize = 12.sp,
                        color = WarmEarthBrown.copy(alpha = 0.65f)
                    )
                    Text(
                        text = "Narita Airport",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Countdown Units Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CountdownUnit(value = "${horizonState.countdownDays}", unit = "DAYS")
                        CountdownColon()
                        CountdownUnit(value = "${horizonState.countdownHours}", unit = "HOURS")
                        CountdownColon()
                        CountdownUnit(value = "${horizonState.countdownMins}", unit = "MINS")
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Progress flight track
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "LHR Departure",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = WarmEarthBrown.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "68% Time Elapsed",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunsetOrange
                            )
                            Text(
                                text = "NRT Landing",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = WarmEarthBrown.copy(alpha = 0.6f)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { horizonState.flightProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = SunsetOrange,
                            trackColor = Color(0x33E76F51)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Weather forecast pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF7F2EE))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "🌸 ${horizonState.weatherForecast}",
                            fontSize = 11.sp,
                            color = WarmEarthBrown,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Reunion Playbook (Packing, Surprise Vault, Itinerary)
        item {
            Text(
                text = "REUNION PLAYBOOK",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Packing Checklist Card
                PlaybookCard(
                    modifier = Modifier.weight(1f),
                    title = "Packing Checklist",
                    subtitle = "$packedCount of $totalPackingCount packed",
                    badge = "Leo & Maya synced",
                    icon = "🧳",
                    onClick = { viewModel.togglePackingModal(true) }
                )

                // Surprise Vault Card
                PlaybookCard(
                    modifier = Modifier.weight(1f),
                    title = "Surprise Vault",
                    subtitle = "3 Secret Gifts",
                    badge = "Hidden until touch",
                    icon = "🎁",
                    onClick = { viewModel.toggleSurpriseModal(true) }
                )

                // Add to Itinerary Card
                PlaybookCard(
                    modifier = Modifier.weight(1f),
                    title = "Tokyo Itinerary",
                    subtitle = "${itinerarySpots.size} spots pinned",
                    badge = "Date ideas",
                    icon = "📍",
                    onClick = { viewModel.toggleItineraryModal(true) }
                )
            }
        }

        // Digital Time Capsules
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DIGITAL TIME CAPSULES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarmEarthBrown.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Heart-sealed",
                    fontSize = 11.sp,
                    color = SunsetOrange,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        items(timeCapsules) { capsule ->
            TimeCapsuleCard(
                capsule = capsule,
                isPlaying = horizonState.activeCapsuleAudioPlayingId == capsule.id,
                onTogglePlay = { viewModel.toggleCapsuleAudio(capsule.id) }
            )
        }

        // Passport Stamps
        item {
            Text(
                text = "PASSPORT STAMPS • OUR TOUCHSTONE MEMORIES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PassportStampCard(
                    modifier = Modifier.weight(1f),
                    city = "Paris, 2024",
                    tag = "ENTRY • AUTUMN",
                    caption = "12 Unforgettable Days",
                    drawableId = R.drawable.paris_stamp
                )
                PassportStampCard(
                    modifier = Modifier.weight(1f),
                    city = "Kyoto, 2025",
                    tag = "ENTRY • SPRING",
                    caption = "Lanterns & Bamboo Grove",
                    drawableId = R.drawable.kyoto_stamp
                )
            }
        }

        // Personalized Love Quote with Gemini AI
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "OUR CONTINENTAL MOTTO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange,
                            letterSpacing = 0.5.sp
                        )

                        IconButton(
                            onClick = { viewModel.refreshPersonalizedQuote() },
                            modifier = Modifier.size(28.dp)
                        ) {
                            if (horizonState.isGeneratingQuote) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = SunsetOrange
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Refresh with AI",
                                    tint = SunsetOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "“${horizonState.personalizedQuote}”",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmEarthBrown,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Packing List Dialog
    if (horizonState.showPackingModal) {
        PackingListDialog(
            items = packingItems,
            onToggleItem = { viewModel.togglePackingItem(it) },
            onAddItem = { viewModel.addPackingItem(it) },
            onDismiss = { viewModel.togglePackingModal(false) }
        )
    }

    // Surprise Vault Dialog
    if (horizonState.showSurpriseModal) {
        SurpriseVaultDialog(
            onDismiss = { viewModel.toggleSurpriseModal(false) }
        )
    }

    // Itinerary & Date Ideas Dialog
    if (horizonState.showItineraryModal) {
        ItineraryDialog(
            spots = itinerarySpots,
            dateIdeas = horizonState.dateIdeas,
            isGenerating = horizonState.isGeneratingDateIdeas,
            onGenerateNewIdeas = { viewModel.generateNewDateIdeas() },
            onAddSpot = { name, loc, notes -> viewModel.addItinerarySpot(name, loc, notes) },
            onDismiss = { viewModel.toggleItineraryModal(false) }
        )
    }
}

@Composable
private fun CountdownUnit(value: String, unit: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = WarmEarthBrown,
            letterSpacing = (-1).sp
        )
        Text(
            text = unit,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = WarmEarthBrown.copy(alpha = 0.55f),
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun CountdownColon() {
    Text(
        text = ":",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = SunsetOrange.copy(alpha = 0.6f),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun PlaybookCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    badge: String,
    icon: String,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF7F2EE)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown,
                maxLines = 1
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = WarmEarthBrown.copy(alpha = 0.7f),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(Color(0xFFFFF1D6))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = badge,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB57C00),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun TimeCapsuleCard(
    capsule: TimeCapsuleEntity,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(if (capsule.isLocked) Color(0xFFF7F2EE) else Color(0xFFE8F5E9))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = capsule.subtitle,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (capsule.isLocked) WarmEarthBrown.copy(alpha = 0.6f) else Color(0xFF2E7D32)
                    )
                }

                if (capsule.isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = WarmEarthBrown.copy(alpha = 0.5f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = capsule.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = capsule.description,
                fontSize = 12.sp,
                color = WarmEarthBrown.copy(alpha = 0.7f),
                lineHeight = 17.sp
            )

            if (!capsule.isLocked && capsule.capsuleType == "AUDIO") {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFF7F2EE))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onTogglePlay,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(SunsetOrange)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play capsule audio",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (isPlaying) "Playing rainy recording... 01:24" else "Play recorded message (${capsule.audioDuration})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmEarthBrown
                    )
                }
            }
        }
    }
}

@Composable
private fun PassportStampCard(
    modifier: Modifier = Modifier,
    city: String,
    tag: String,
    caption: String,
    drawableId: Int
) {
    GlassCard(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = city,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tag,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = SunsetOrange
            )

            Text(
                text = city,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown
            )

            Text(
                text = caption,
                fontSize = 10.sp,
                color = WarmEarthBrown.copy(alpha = 0.65f)
            )
        }
    }
}

@Composable
fun PackingListDialog(
    items: List<PackingItemEntity>,
    onToggleItem: (PackingItemEntity) -> Unit,
    onAddItem: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newItemName by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(28.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .height(480.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Packing Checklist 🧳",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmEarthBrown
                        )
                        Text(
                            text = "${items.count { it.isChecked }} of ${items.size} packed",
                            fontSize = 12.sp,
                            color = WarmEarthBrown.copy(alpha = 0.6f)
                        )
                    }
                    Text(
                        text = "✕",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown.copy(alpha = 0.5f),
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newItemName,
                        onValueChange = { newItemName = it },
                        placeholder = { Text("Add item...", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SunsetOrange,
                            unfocusedBorderColor = Color(0x334A2E27)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (newItemName.isNotBlank()) {
                                onAddItem(newItemName)
                                newItemName = ""
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(DeepBurgundy)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (item.isChecked) Color(0xFFE8F5E9) else Color(0xFFF7F2EE))
                                .clickable { onToggleItem(item) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (item.isChecked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = "Check",
                                tint = if (item.isChecked) Color(0xFF2E7D32) else WarmEarthBrown.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = item.name,
                                fontSize = 13.sp,
                                color = WarmEarthBrown,
                                fontWeight = if (item.isChecked) FontWeight.Normal else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SurpriseVaultDialog(onDismiss: () -> Unit) {
    var revealed1 by remember { mutableStateOf(false) }
    var revealed2 by remember { mutableStateOf(false) }
    var revealed3 by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(28.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Surprise Vault 🎁",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )
                    Text(
                        text = "✕",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown.copy(alpha = 0.5f),
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Sealed surprises prepared for Tokyo reunion. Tap to peek or keep hidden until the airport hug!",
                    fontSize = 12.sp,
                    color = WarmEarthBrown.copy(alpha = 0.65f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SurpriseVaultItem(
                    title = "Surprise #1: Secret Dinner Booking",
                    revealed = revealed1,
                    secretText = "🍣 Hidden omakase rooftop counter in Ebisu at sunset!",
                    onToggle = { revealed1 = !revealed1 }
                )

                Spacer(modifier = Modifier.height(10.dp))

                SurpriseVaultItem(
                    title = "Surprise #2: In-flight Gift",
                    revealed = revealed2,
                    secretText = "💌 Hand-engraved pocket compass with coordinates of London & Tokyo.",
                    onToggle = { revealed2 = !revealed2 }
                )

                Spacer(modifier = Modifier.height(10.dp))

                SurpriseVaultItem(
                    title = "Surprise #3: Kyoto Day Trip",
                    revealed = revealed3,
                    secretText = "🎋 Private morning matcha ceremony at a secluded bamboo forest villa.",
                    onToggle = { revealed3 = !revealed3 }
                )
            }
        }
    }
}

@Composable
private fun SurpriseVaultItem(
    title: String,
    revealed: Boolean,
    secretText: String,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF7F2EE))
            .clickable { onToggle() }
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = WarmEarthBrown)
                Text(
                    text = if (revealed) "Hide" else "Tap to Peek 👀",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SunsetOrange
                )
            }
            if (revealed) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = secretText, fontSize = 12.sp, color = WarmEarthBrown, lineHeight = 17.sp)
            }
        }
    }
}

@Composable
fun ItineraryDialog(
    spots: List<ItinerarySpotEntity>,
    dateIdeas: List<String>,
    isGenerating: Boolean,
    onGenerateNewIdeas: () -> Unit,
    onAddSpot: (String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var newSpotName by remember { mutableStateOf("") }
    var newLocation by remember { mutableStateOf("Tokyo") }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(28.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .height(520.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tokyo Itinerary & Date Ideas 📍",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )
                    Text(
                        text = "✕",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown.copy(alpha = 0.5f),
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Gemini AI ideas card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFFFF1D6))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "✨ Gemini Date Idea Sparks",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB57C00)
                            )
                            IconButton(
                                onClick = onGenerateNewIdeas,
                                modifier = Modifier.size(24.dp)
                            ) {
                                if (isGenerating) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(14.dp),
                                        strokeWidth = 2.dp,
                                        color = Color(0xFFB57C00)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "New ideas",
                                        tint = Color(0xFFB57C00),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        dateIdeas.take(2).forEach { idea ->
                            Text(
                                text = idea,
                                fontSize = 11.sp,
                                color = WarmEarthBrown,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "PINNED SPOTS (${spots.size})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SunsetOrange
                )

                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(spots) { spot ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFF7F2EE))
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = spot.spotName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WarmEarthBrown
                                    )
                                    Text(
                                        text = spot.location,
                                        fontSize = 11.sp,
                                        color = SunsetOrange,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = spot.notes,
                                    fontSize = 11.sp,
                                    color = WarmEarthBrown.copy(alpha = 0.65f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
