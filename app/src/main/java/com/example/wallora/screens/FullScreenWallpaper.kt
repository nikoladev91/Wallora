package com.example.wallora.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallora.model.Wallpaper
import com.example.wallora.ui.theme.WalloraAccent
import kotlinx.coroutines.delay

@Composable
fun FullScreenWallpaper(
    wallpaper: Wallpaper,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onSetWallpaperClick: () -> Unit,
    onBack: () -> Unit
) {

    var controlsVisible by remember(wallpaper.image) {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val ratingPreferences = remember {
        context.getSharedPreferences(
            "wallora_ratings",
            android.content.Context.MODE_PRIVATE
        )
    }

    var userRating by remember(wallpaper.name) {
        mutableStateOf(
            ratingPreferences.getInt(
                "rating_${wallpaper.name}",
                0
            )
        )
    }

    LaunchedEffect(controlsVisible, wallpaper.image) {
        if (controlsVisible) {
            delay(3000)
            controlsVisible = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(wallpaper.image) {
                detectTapGestures {
                    controlsVisible = !controlsVisible
                }
            }
    ) {

        Image(
            painter = painterResource(id = wallpaper.image),
            contentDescription = wallpaper.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = if (wallpaper.category == "Zodiac") {
                ContentScale.Fit
            } else {
                ContentScale.Crop
            }
        )

        AnimatedVisibility(
            visible = controlsVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.45f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.88f)
                                )
                            )
                        )
                )

                // Back button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 18.dp, top = 44.dp)
                        .size(46.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.65f),
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
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Bottom controls
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(
                            start = 18.dp,
                            end = 18.dp,
                            bottom = 12.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Rating
                    Text(
                        text = if (userRating == 0) {
                            "Rate this wallpaper"
                        } else {
                            "Your rating"
                        },
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        for (star in 1..5) {

                            Text(
                                text = if (star <= userRating) {
                                    "★"
                                } else {
                                    "☆"
                                },
                                color = if (star <= userRating) {
                                    WalloraAccent
                                } else {
                                    Color.White
                                },
                                fontSize = 28.sp,
                                modifier = Modifier
                                    .clickable {
                                        userRating = star

                                        ratingPreferences
                                            .edit()
                                            .putInt(
                                                "rating_${wallpaper.name}",
                                                star
                                            )
                                            .apply()
                                    }
                                    .padding(horizontal = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Favorite + Download
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

                    Spacer(modifier = Modifier.height(9.dp))

                    // Set wallpaper
                    PremiumActionButton(
                        text = "🖼 Set Wallpaper",
                        primary = true,
                        onClick = onSetWallpaperClick
                    )
                }
            }
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
            .height(44.dp)
            .background(
                color = if (primary) {
                    WalloraAccent
                } else {
                    Color.Black.copy(alpha = 0.68f)
                },
                shape = RoundedCornerShape(16.dp)
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
            fontSize = 14.sp,
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
                color = Color.Black.copy(alpha = 0.68f),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}