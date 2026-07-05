package com.example.wallpapersapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomMenu(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111111))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(
            text = "🏠 Home",
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") }
        )

        MenuItem(
            text = "❤️ Favorites",
            selected = selectedTab == "favorites",
            onClick = { onTabSelected("favorites") }
        )

        MenuItem(
            text = "⚙️ Settings",
            selected = selectedTab == "settings",
            onClick = { onTabSelected("settings") }
        )
    }
}

@Composable
fun MenuItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) Color(0xFF64B5F6) else Color.White,
        fontSize = if (selected) 17.sp else 15.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    )
}