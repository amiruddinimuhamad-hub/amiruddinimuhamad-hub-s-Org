package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.R
import com.example.ui.theme.DeepBurgundy
import com.example.ui.theme.LuminousCream
import com.example.ui.theme.SunsetOrange
import com.example.ui.theme.WarmEarthBrown

@Composable
fun TopHeader(
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App title
        Text(
            text = "MYLY",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = WarmEarthBrown,
            letterSpacing = 1.sp,
            modifier = Modifier.testTag("app_title_text")
        )

        // Presence pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(9999.dp))
                .background(Color(0xE6FFFFFF))
                .border(
                    width = 1.dp,
                    color = Color(0x33F5EFC6),
                    shape = RoundedCornerShape(9999.dp)
                )
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(SunsetOrange)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Leo & Maya • 5,420 mi",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = WarmEarthBrown
                )
            }
        }

        // Maya's Avatar with presence dot
        Box(
            modifier = Modifier
                .size(42.dp)
                .clickable { onAvatarClick() }
                .testTag("maya_avatar_button")
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_maya),
                contentDescription = "Maya's Profile Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )

            // Active dot
            Box(
                modifier = Modifier
                    .size(11.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE76F51))
                    .border(2.dp, Color.White, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}
