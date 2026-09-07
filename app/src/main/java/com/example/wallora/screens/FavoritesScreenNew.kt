package com.example.wallora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallora.R
import com.example.wallora.model.Wallpaper
import com.example.wallora.ui.theme.WalloraBackground

@Composable
fun FavoritesScreenNew(
    wallpapers: List<Wallpaper>,
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }

    val scrollState = rememberScrollState()

    val filteredFavorites = wallpapers.filter { wallpaper ->
        matchesWallpaperSearch(
            wallpaper = wallpaper,
            query = searchText
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WalloraBackground)
            .padding(horizontal = 16.dp)
            .then(
                if (wallpapers.isNotEmpty()) {
                    Modifier.verticalScroll(scrollState)
                } else {
                    Modifier
                }
            )
    ) {

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "❤️ ${stringResource(R.string.favorites)}",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = stringResource(
                R.string.wallpapers_saved,
                wallpapers.size
            ),
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (wallpapers.isNotEmpty()) {
            SearchSection(
                searchText = searchText,
                onSearchChange = {
                    searchText = it
                }
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        if (wallpapers.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "❤️",
                    fontSize = 56.sp
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = stringResource(
                        R.string.no_favorites_yet
                    ),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = stringResource(
                        R.string.add_favorites_hint
                    ),
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }

        } else if (filteredFavorites.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🔍",
                    fontSize = 48.sp
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = stringResource(
                        R.string.no_favorites_found
                    ),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = stringResource(
                        R.string.try_another_keyword
                    ),
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

        } else {

            WallpaperGrid(
                wallpapers = filteredFavorites,
                favoriteNames = favoriteNames,
                onWallpaperClick = onWallpaperClick
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}