package com.example.wallpapersapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeHeader(
    title: String,
    subtitle: String,
    favoriteInfo: String,
    stats: String
) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = subtitle,
        color = Color.LightGray,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 4.dp)
    )

    Text(
        text = stats,
        color = Color.White.copy(alpha = 0.75f),
        fontSize = 15.sp,
        modifier = Modifier.padding(top = 4.dp)
    )

    Text(
        text = favoriteInfo,
        color = Color(0xFFFF6B81),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp)
    )
}