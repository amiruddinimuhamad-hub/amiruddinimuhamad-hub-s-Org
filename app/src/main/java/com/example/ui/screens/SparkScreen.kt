package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.SparkRecordEntity
import com.example.ui.components.GlassCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.DeepBurgundy
import com.example.ui.theme.LuminousCream
import com.example.ui.theme.SunsetOrange
import com.example.ui.theme.WarmEarthBrown
import com.example.ui.viewmodel.MylyViewModel

@Composable
fun SparkScreen(
    viewModel: MylyViewModel,
    modifier: Modifier = Modifier
) {
    val sparkState by viewModel.sparkState.collectAsState()
    val sparkRecords by viewModel.sparkRecords.collectAsState()

    val currentSpark = sparkRecords.firstOrNull { it.sparkNumber == 214 }
    val isRevealed = sparkState.isRevealed || (currentSpark?.isLeoAnswered == true)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopHeader(onAvatarClick = { viewModel.openAuthScreen() })
        }

        // Top Streak Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SPARK #214 of 365",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SunsetOrange,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Daily Ritual & Heartbeat",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(Color(0xFFFFF1D6))
                        .border(1.dp, Color(0x66E9C46A), RoundedCornerShape(9999.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "✨ Day 214 Streak",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB57C00)
                    )
                }
            }
        }

        // Today's Daily Ritual Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TODAY'S QUESTION",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange,
                            letterSpacing = 0.5.sp
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFF7F2EE))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Resets in 7h 12m",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = WarmEarthBrown.copy(alpha = 0.65f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentSpark?.question ?: "What is a small, silly moment with me that made you smile this past month?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Take a deep breath and replay the little moments across time zones.",
                        fontSize = 12.sp,
                        color = WarmEarthBrown.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Maya's answer state
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isRevealed) Color(0xFFFFF6F4) else Color(0xFFF7F2EE))
                            .border(
                                1.dp,
                                if (isRevealed) Color(0x66FF9A98) else Color(0x33CAC6B9),
                                RoundedCornerShape(20.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.avatar_maya),
                                        contentDescription = "Maya",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Maya answered ✓",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WarmEarthBrown
                                    )
                                }

                                Text(
                                    text = currentSpark?.mayaTimestamp ?: "8:42 AM • Tokyo",
                                    fontSize = 11.sp,
                                    color = WarmEarthBrown.copy(alpha = 0.6f)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (isRevealed) {
                                Text(
                                    text = currentSpark?.mayaAnswer ?: "When you tried making iced matcha with oat milk on FaceTime and sprayed matcha all over your white sweater! You looked so adorably stunned.",
                                    fontSize = 13.sp,
                                    color = WarmEarthBrown,
                                    lineHeight = 19.sp
                                )
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = SunsetOrange,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Private until both answer. Share yours to unlock Maya's note.",
                                        fontSize = 12.sp,
                                        color = WarmEarthBrown.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // User's Answer Input or Revealed View
                    if (!isRevealed) {
                        Text(
                            text = "Your heart's answer • Private until both answer",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WarmEarthBrown.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = sparkState.heartAnswerDraft,
                            onValueChange = { viewModel.updateDraftAnswer(it) },
                            placeholder = {
                                Text(
                                    text = "Remembering when we made breakfast over video call and you spilled the matcha...",
                                    fontSize = 13.sp,
                                    color = WarmEarthBrown.copy(alpha = 0.45f)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .testTag("spark_answer_input"),
                            shape = RoundedCornerShape(18.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SunsetOrange,
                                unfocusedBorderColor = Color(0x334A2E27),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                AttachmentChip(icon = "🎤", label = "Audio note")
                                AttachmentChip(icon = "☁️", label = "Sky photo")
                                AttachmentChip(icon = "✏️", label = "Doodle")
                            }

                            Text(
                                text = "${sparkState.heartAnswerDraft.length}/300",
                                fontSize = 11.sp,
                                color = WarmEarthBrown.copy(alpha = 0.5f)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.submitSparkAnswer() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("reveal_sync_button"),
                            shape = RoundedCornerShape(9999.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepBurgundy,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Sync",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Reveal & Sync Answers",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        // User's answer displayed
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFF7F2EE))
                                .padding(14.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Your Answer ✓",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WarmEarthBrown
                                    )
                                    Text(
                                        text = "Just now",
                                        fontSize = 11.sp,
                                        color = WarmEarthBrown.copy(alpha = 0.6f)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentSpark?.leoAnswer?.ifBlank { sparkState.heartAnswerDraft }
                                        ?: "Remembering when we made breakfast over video call and you spilled the matcha... You looked so adorable laughing at yourself.",
                                    fontSize = 13.sp,
                                    color = WarmEarthBrown,
                                    lineHeight = 19.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Facetime Icebreaker Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "FACETIME ICEBREAKER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange,
                            letterSpacing = 0.5.sp
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFFFF1D6))
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = sparkState.activeQuestionCategory,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB57C00)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = sparkState.currentIcebreaker,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = WarmEarthBrown,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.drawAnotherIcebreaker() },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("draw_icebreaker_button"),
                            shape = RoundedCornerShape(9999.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF7F2EE),
                                contentColor = WarmEarthBrown
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Draw",
                                modifier = Modifier.size(16.dp),
                                tint = WarmEarthBrown
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Draw Another", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = { viewModel.generateIcebreakerWithGemini() },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("gemini_icebreaker_button"),
                            shape = RoundedCornerShape(9999.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFF1D6),
                                contentColor = Color(0xFFB57C00)
                            )
                        ) {
                            if (sparkState.isGeneratingIcebreaker) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = Color(0xFFB57C00)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xFFB57C00)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Gemini AI", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Long-Distance Mini-Games Launcher (Phase 1 MVP Requirement)
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SportsEsports,
                                contentDescription = "Mini-Games",
                                tint = SunsetOrange,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "LONG-DISTANCE MINI-GAMES",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunsetOrange,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFE8F5E9))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "3 Games Ready",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    MiniGameItem(
                        title = "Would You Rather: Distance Edition",
                        description = "Pick your favorite scenario and compare answers live!",
                        icon = "⚖️",
                        onClick = { viewModel.toggleMiniGamesModal(true, 0) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MiniGameItem(
                        title = "Couple's Trivia: Who Knows Who?",
                        description = "Test how well you remember the details from London to Tokyo.",
                        icon = "🎯",
                        onClick = { viewModel.toggleMiniGamesModal(true, 1) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MiniGameItem(
                        title = "Emoji Cipher / Secret Code",
                        description = "Decode playful emoji messages sent across the miles.",
                        icon = "🧩",
                        onClick = { viewModel.toggleMiniGamesModal(true, 2) }
                    )
                }
            }
        }

        // Past Sparks Recap
        item {
            Text(
                text = "PAST SPARKS RECAP",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
        }

        items(sparkRecords.filter { it.sparkNumber < 214 }) { record ->
            PastSparkCard(record = record)
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Mini-games modal
    if (sparkState.showMiniGamesModal) {
        MiniGamesDialog(
            gameIndex = sparkState.activeMiniGameIndex,
            onDismiss = { viewModel.toggleMiniGamesModal(false) }
        )
    }
}

@Composable
private fun AttachmentChip(icon: String, label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(9999.dp))
            .background(Color(0xFFF7F2EE))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 11.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = WarmEarthBrown.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun MiniGameItem(
    title: String,
    description: String,
    icon: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF7F2EE))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarmEarthBrown
                )
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = WarmEarthBrown.copy(alpha = 0.65f),
                    maxLines = 1
                )
            }
            Text(
                text = "Play ›",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SunsetOrange
            )
        }
    }
}

@Composable
private fun PastSparkCard(record: SparkRecordEntity) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${record.dateLabel} • #${record.sparkNumber}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SunsetOrange
                )
                Text(
                    text = "Both Answered ✓",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2E7D32)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = record.question,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = WarmEarthBrown
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Maya's answer
            Text(
                text = "Maya: \"${record.mayaAnswer}\"",
                fontSize = 12.sp,
                color = WarmEarthBrown.copy(alpha = 0.85f),
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Leo's answer
            Text(
                text = "Leo: \"${record.leoAnswer}\"",
                fontSize = 12.sp,
                color = WarmEarthBrown.copy(alpha = 0.85f),
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
fun MiniGamesDialog(
    gameIndex: Int,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
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
                        text = when (gameIndex) {
                            0 -> "Would You Rather? ⚖️"
                            1 -> "Couple's Trivia 🎯"
                            else -> "Emoji Secret Code 🧩"
                        },
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

                Spacer(modifier = Modifier.height(14.dp))

                when (gameIndex) {
                    0 -> {
                        Text(
                            text = "Distance Edition: Would you rather...",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = WarmEarthBrown.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        GameChoiceButton(text = "A) Have a 1-hour teleport hug anywhere in the world once a month")
                        Spacer(modifier = Modifier.height(8.dp))
                        GameChoiceButton(text = "B) Have infinite battery FaceTime where we never lag or disconnect")
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Maya chose: Option A (Teleport Hug!) ❤️",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange
                        )
                    }
                    1 -> {
                        Text(
                            text = "Question: What is Leo's go-to midnight comfort drink when it's raining in London?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = WarmEarthBrown
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        GameChoiceButton(text = "1) Earl Grey with oat milk and honey")
                        Spacer(modifier = Modifier.height(6.dp))
                        GameChoiceButton(text = "2) Hot spiced apple cider")
                        Spacer(modifier = Modifier.height(6.dp))
                        GameChoiceButton(text = "3) Roasted barley tea (Mugicha)")
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Maya's guess: Earl Grey with oat milk! ✓ 100% Match!",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                    else -> {
                        Text(
                            text = "Maya sent you this secret code:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = WarmEarthBrown
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFFFF1D6))
                                .padding(14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✈️ 🌸 🍵 🫂 🔒 ❤️",
                                fontSize = 24.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Decoded: \"Flight to Cherry Blossoms & Matcha with Tight Hug Forever\"",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmEarthBrown
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBurgundy,
                        contentColor = Color.White
                    )
                ) {
                    Text("Close & Send Reaction 💌", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun GameChoiceButton(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF7F2EE))
            .clickable { }
            .padding(12.dp)
    ) {
        Text(text = text, fontSize = 12.sp, color = WarmEarthBrown)
    }
}
