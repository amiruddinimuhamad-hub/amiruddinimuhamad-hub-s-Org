package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.local.PinnedNoteEntity
import com.example.data.local.WatchlistItemEntity
import com.example.ui.components.GlassCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.DeepBurgundy
import com.example.ui.theme.LuminousCream
import com.example.ui.theme.SunsetOrange
import com.example.ui.theme.WarmEarthBrown
import com.example.ui.viewmodel.DrawStroke
import com.example.ui.viewmodel.MylyViewModel

@Composable
fun TheDenScreen(
    viewModel: MylyViewModel,
    modifier: Modifier = Modifier
) {
    val theDenState by viewModel.theDenState.collectAsState()
    val pinnedNotes by viewModel.pinnedNotes.collectAsState()
    val watchlistItems by viewModel.watchlistItems.collectAsState()
    val canvasStrokes by viewModel.canvasStrokes.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopHeader(onAvatarClick = { viewModel.openAuthScreen() })
        }

        // Living Room Soundscape Hero
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = "Soundscape",
                                tint = SunsetOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "LIVING ROOM SOUNDSCAPE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunsetOrange,
                                letterSpacing = 0.5.sp
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleSoundscape() },
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF7F2EE))
                                .testTag("toggle_soundscape_button")
                        ) {
                            Icon(
                                imageVector = if (theDenState.isSoundscapePlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Soundscape play",
                                tint = WarmEarthBrown,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = theDenState.soundscapeTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Volume",
                            fontSize = 11.sp,
                            color = WarmEarthBrown.copy(alpha = 0.65f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Slider(
                            value = theDenState.soundscapeVolume,
                            onValueChange = { viewModel.setSoundscapeVolume(it) },
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = SunsetOrange,
                                activeTrackColor = SunsetOrange,
                                inactiveTrackColor = Color(0x334A2E27)
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "${(theDenState.soundscapeVolume * 100).toInt()}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmEarthBrown
                        )
                    }
                }
            }
        }

        // Our Cozy Board Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "OUR COZY BOARD",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarmEarthBrown.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(Color(0xFFFFF1D6))
                        .clickable { viewModel.toggleAddNoteDialog(true) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("pin_note_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Pin Note",
                            tint = Color(0xFFB57C00),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Pin Note",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB57C00)
                        )
                    }
                }
            }
        }

        // Pinned Notes Items
        items(pinnedNotes) { note ->
            CozyNoteCard(note = note)
        }

        // Shared Canvas Section
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
                            text = "SHARED CANVAS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange,
                            letterSpacing = 0.5.sp
                        )

                        Text(
                            text = "Maya drew a cat 12m ago",
                            fontSize = 11.sp,
                            color = WarmEarthBrown.copy(alpha = 0.65f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Doodle preview canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFFFFFDF8))
                            .border(1.dp, Color(0x224A2E27), RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Draw existing strokes
                            for (stroke in canvasStrokes) {
                                for (i in 0 until stroke.points.size - 1) {
                                    drawLine(
                                        color = stroke.color,
                                        start = stroke.points[i],
                                        end = stroke.points[i + 1],
                                        strokeWidth = stroke.strokeWidth,
                                        cap = StrokeCap.Round
                                    )
                                }
                            }
                        }

                        // Cat doodle overlay if no user strokes yet
                        if (canvasStrokes.isEmpty()) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "ᓚᘏᗢ", fontSize = 38.sp, color = WarmEarthBrown)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "“Cute loaf! Live synced across the miles” ❤️",
                                    fontSize = 11.sp,
                                    color = SunsetOrange,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.toggleCanvasDialog(true) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("open_canvas_button"),
                        shape = RoundedCornerShape(9999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepBurgundy,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "Draw",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Jump In & Draw Together", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Teleparty Watchlist
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = "Watchlist",
                                tint = SunsetOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TELEPARTY WATCHLIST",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunsetOrange,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Text(
                            text = "${watchlistItems.count { it.isCompleted }}/${watchlistItems.size} completed",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = WarmEarthBrown.copy(alpha = 0.65f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    watchlistItems.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFF7F2EE))
                                .clickable { viewModel.toggleWatchlistItem(item) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (item.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = "Status",
                                tint = if (item.isCompleted) Color(0xFF2E7D32) else WarmEarthBrown.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmEarthBrown
                                )
                                Text(
                                    text = if (item.isCompleted) "★ ${item.rating}" else item.scheduledNote,
                                    fontSize = 11.sp,
                                    color = if (item.isCompleted) SunsetOrange else WarmEarthBrown.copy(alpha = 0.65f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Shared Book Club
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
                            text = "SHARED BOOK CLUB",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange,
                            letterSpacing = 0.5.sp
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFFFF1D6))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "65% Synced",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB57C00)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tomorrow, and Tomorrow, and Tomorrow",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )
                    Text(
                        text = "Gabrielle Zevin • Reading together • ${theDenState.currentChapter}",
                        fontSize = 11.sp,
                        color = WarmEarthBrown.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Leo (You)",
                                fontSize = 11.sp,
                                color = WarmEarthBrown.copy(alpha = 0.65f)
                            )
                            Text(
                                text = "Page ${theDenState.leoBookPage}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = WarmEarthBrown
                            )
                        }

                        // Page update buttons
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF7F2EE))
                                    .clickable { viewModel.updateLeoBookPage(-5) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "-5", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = WarmEarthBrown)
                            }
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF7F2EE))
                                    .clickable { viewModel.updateLeoBookPage(+5) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "+5", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = WarmEarthBrown)
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Maya",
                                fontSize = 11.sp,
                                color = WarmEarthBrown.copy(alpha = 0.65f)
                            )
                            Text(
                                text = "Page ${theDenState.mayaBookPage}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunsetOrange
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { theDenState.leoBookPage.toFloat() / theDenState.totalBookPages },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = SunsetOrange,
                        trackColor = Color(0x33E76F51)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Add Note Dialog
    if (theDenState.showAddNoteDialog) {
        AddNoteDialog(
            onDismiss = { viewModel.toggleAddNoteDialog(false) },
            onAddNote = { title, content, type ->
                viewModel.addNote(title, content, type)
                viewModel.toggleAddNoteDialog(false)
            }
        )
    }

    // Shared Canvas Draw Dialog
    if (theDenState.showCanvasDialog) {
        CanvasDrawDialog(
            existingStrokes = canvasStrokes,
            onAddStroke = { viewModel.addCanvasStroke(it) },
            onClear = { viewModel.clearCanvasStrokes() },
            onDismiss = { viewModel.toggleCanvasDialog(false) }
        )
    }
}

@Composable
private fun CozyNoteCard(note: PinnedNoteEntity) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Washi tape effect on photo notes
            if (note.noteType == "PHOTO") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(14.dp)
                            .background(Color(0x99F5EFC6))
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Image(
                    painter = painterResource(id = R.drawable.sunset_matcha),
                    contentDescription = note.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (note.noteType == "STICKER") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sticker_bunny_dino),
                        contentDescription = "Sticker",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(14.dp))
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFFFFF1D6))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Always You",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB57C00)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Maya stuck this on your board ❤️",
                            fontSize = 11.sp,
                            color = WarmEarthBrown.copy(alpha = 0.65f)
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SunsetOrange,
                        letterSpacing = 0.5.sp
                    )

                    Text(
                        text = note.timestamp,
                        fontSize = 10.sp,
                        color = WarmEarthBrown.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = note.content,
                    fontSize = 13.sp,
                    color = WarmEarthBrown,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 18.sp
                )

                if (note.reply.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFF7F2EE))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = note.reply,
                            fontSize = 11.sp,
                            color = Color(0xFF4A3DBB),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onAddNote: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(26.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Pin Note to Our Board 📌",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarmEarthBrown
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title (e.g. LEO'S NUDGE)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("What's on your mind? Sticky note message...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (content.isNotBlank()) {
                            onAddNote(title.ifBlank { "SWEET NOTE" }, content, "NUDGE")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBurgundy,
                        contentColor = Color.White
                    )
                ) {
                    Text("Pin Note ✨", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CanvasDrawDialog(
    existingStrokes: List<DrawStroke>,
    onAddStroke: (DrawStroke) -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit
) {
    var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var selectedColor by remember { mutableStateOf(SunsetOrange) }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(28.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Live Shared Canvas 🎨",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmEarthBrown
                    )
                    Text(
                        text = "Clear",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SunsetOrange,
                        modifier = Modifier.clickable { onClear() }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Drawing Canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFFFFDF8))
                        .border(1.dp, Color(0x334A2E27), RoundedCornerShape(18.dp))
                        .pointerInput(selectedColor) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPoints = listOf(offset)
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    currentPoints = currentPoints + change.position
                                },
                                onDragEnd = {
                                    if (currentPoints.isNotEmpty()) {
                                        onAddStroke(DrawStroke(currentPoints, selectedColor))
                                        currentPoints = emptyList()
                                    }
                                }
                            )
                        }
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Draw all committed strokes
                        for (stroke in existingStrokes) {
                            for (i in 0 until stroke.points.size - 1) {
                                drawLine(
                                    color = stroke.color,
                                    start = stroke.points[i],
                                    end = stroke.points[i + 1],
                                    strokeWidth = stroke.strokeWidth,
                                    cap = StrokeCap.Round
                                )
                            }
                        }

                        // Draw current active in-progress stroke
                        for (i in 0 until currentPoints.size - 1) {
                            drawLine(
                                color = selectedColor,
                                start = currentPoints[i],
                                end = currentPoints[i + 1],
                                strokeWidth = 6f,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Color picker palette
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf(
                        SunsetOrange,
                        Color(0xFF4A6077),
                        DeepBurgundy,
                        Color(0xFFE9C46A),
                        Color(0xFF4CAF50),
                        Color(0xFF9C27B0)
                    ).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    if (selectedColor == color) 3.dp else 0.dp,
                                    Color.DarkGray,
                                    CircleShape
                                )
                                .clickable { selectedColor = color }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBurgundy,
                        contentColor = Color.White
                    )
                ) {
                    Text("Done & Sync with Maya ✨", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
