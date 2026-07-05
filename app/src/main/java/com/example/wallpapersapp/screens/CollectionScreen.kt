package com.example.wallpapersapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.Wallpaper
import com.example.wallpapersapp.model.WallpaperCollection

@Composable
fun CollectionScreen(
    collection: WallpaperCollection,
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 16.dp, end = 16.dp, top = 42.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF1E1E1E),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clickable { onBackClick() }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "← Back",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Column {
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
            }
        }

        item {
            WallpaperGrid(
                wallpapers = collection.wallpapers,
                favoriteNames = favoriteNames,
                onWallpaperClick = onWallpaperClick
            )
        }
    }
}