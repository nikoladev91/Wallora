package com.example.wallpapersapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.Wallpaper

@Composable
fun WallpaperGrid(
    wallpapers: List<Wallpaper>,
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit
) {

    if (wallpapers.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "No wallpapers found",
                color = Color.LightGray,
                fontSize = 20.sp
            )
        }

    } else {

        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            wallpapers.chunked(2).forEach { rowWallpapers ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    rowWallpapers.forEach { wallpaper ->

                        Box(
                            modifier = Modifier.weight(1f)
                        ) {

                            WallpaperCard(
                                wallpaper = wallpaper,
                                isFavorite = favoriteNames.contains(wallpaper.name),
                                onClick = {
                                    onWallpaperClick(wallpaper)
                                }
                            )
                        }
                    }

                    if (rowWallpapers.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}