package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.AllInclusive
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepBurgundy
import com.example.ui.theme.FrostedGlassWhite
import com.example.ui.theme.SpecularBorderLight
import com.example.ui.theme.WarmEarthBrown
import com.example.ui.viewmodel.NavigationTab

@Composable
fun BottomNavBar(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(9999.dp),
                    ambientColor = Color(0x1A4A2E27),
                    spotColor = Color(0x2B4D0E12)
                )
                .clip(RoundedCornerShape(9999.dp))
                .background(FrostedGlassWhite)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(9999.dp)
                )
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                tab = NavigationTab.ORBIT,
                label = "Orbit",
                activeIcon = Icons.Filled.AllInclusive,
                inactiveIcon = Icons.Outlined.AllInclusive,
                isSelected = currentTab == NavigationTab.ORBIT,
                onSelect = { onTabSelected(NavigationTab.ORBIT) }
            )

            NavItem(
                tab = NavigationTab.SPARK,
                label = "Spark",
                activeIcon = Icons.Filled.LocalFireDepartment,
                inactiveIcon = Icons.Outlined.LocalFireDepartment,
                isSelected = currentTab == NavigationTab.SPARK,
                onSelect = { onTabSelected(NavigationTab.SPARK) }
            )

            NavItem(
                tab = NavigationTab.HORIZON,
                label = "Horizon",
                activeIcon = Icons.Filled.HourglassBottom,
                inactiveIcon = Icons.Outlined.HourglassEmpty,
                isSelected = currentTab == NavigationTab.HORIZON,
                onSelect = { onTabSelected(NavigationTab.HORIZON) }
            )

            NavItem(
                tab = NavigationTab.THE_DEN,
                label = "The Den",
                activeIcon = Icons.Filled.Favorite,
                inactiveIcon = Icons.Outlined.FavoriteBorder,
                isSelected = currentTab == NavigationTab.THE_DEN,
                onSelect = { onTabSelected(NavigationTab.THE_DEN) }
            )
        }
    }
}

@Composable
private fun NavItem(
    tab: NavigationTab,
    label: String,
    activeIcon: ImageVector,
    inactiveIcon: ImageVector,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val pillColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFFFDAD2) else Color.Transparent,
        label = "pill_color"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) DeepBurgundy else WarmEarthBrown.copy(alpha = 0.6f),
        label = "content_color"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(9999.dp))
            .background(pillColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSelect() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .testTag("nav_tab_${label.lowercase().replace(" ", "_")}"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isSelected) activeIcon else inactiveIcon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = contentColor
        )
    }
}
