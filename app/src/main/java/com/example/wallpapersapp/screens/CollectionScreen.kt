package com.example.wallpapersapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.CollectionRepository
import com.example.wallpapersapp.model.Wallpaper

@Composable
fun CollectionScreen(
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onBackClick: () -> Unit
) {
    val collection = CollectionRepository.collections.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "← Back",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { onBackClick() }
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = collection.title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = collection.subtitle,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        WallpaperGrid(
            wallpapers = collection.wallpapers,
            favoriteNames = favoriteNames,
            onWallpaperClick = onWallpaperClick
        )
    }
}