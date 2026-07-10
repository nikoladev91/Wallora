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
        fontSize = 38.sp,
        fontWeight = FontWeight.ExtraBold
    )

    Text(
        text = subtitle,
        color = Color.LightGray,
        fontSize = 17.sp,
        lineHeight = 23.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 6.dp)
    )

    Text(
        text = stats,
        color = Color.White.copy(alpha = 0.85f),
        fontSize = 17.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 6.dp)
    )

    Text(
        text = favoriteInfo,
        color = Color(0xFFFFC857),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
    )
}