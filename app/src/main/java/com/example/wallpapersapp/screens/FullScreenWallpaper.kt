package com.example.wallpapersapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.Wallpaper
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FullScreenWallpaper(
    wallpaper: Wallpaper,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onSetWallpaperClick: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = wallpaper.image),
            contentDescription = wallpaper.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.15f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.92f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 48.dp)
                .size(48.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(50.dp)
                )
                .clickable {
                    onBack()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    horizontal = 22.dp,
                    vertical = 34.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = wallpaper.name,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "★ ${wallpaper.rating}",
                color = Color(0xFFFFD54F),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoChip(
                    text = if (wallpaper.isTopPick) {
                        "👑 TOP PICK"
                    } else {
                        wallpaper.badge
                    }
                )

                InfoChip(
                    text = "⬇ ${wallpaper.downloads}"
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    PremiumActionButton(
                        text = if (isFavorite) {
                            "❤️ Favorited"
                        } else {
                            "🤍 Favorite"
                        },
                        primary = false,
                        onClick = onFavoriteClick
                    )
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    PremiumActionButton(
                        text = "⬇ Download",
                        primary = false,
                        onClick = onDownloadClick
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PremiumActionButton(
                text = "🖼 Set Wallpaper",
                primary = true,
                onClick = onSetWallpaperClick
            )
        }
    }
}
@Composable
fun PremiumActionButton(
    text: String,
    primary: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = if (primary) {
                    Color(0xFF64B5F6)
                } else {
                    Color.White.copy(alpha = 0.18f)
                },
                shape = RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (primary) {
                Color.Black
            } else {
                Color.White
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun InfoChip(
    text: String
) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Black.copy(alpha = 0.55f),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 7.dp
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}