package com.example.wallora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallora.R
import com.example.wallora.model.Wallpaper
import com.example.wallora.model.WallpaperCollection
import com.example.wallora.ui.theme.WalloraBackground

@Composable
fun getLocalizedCollectionTitle(
    collection: WallpaperCollection
): String {
    return when (collection.id) {

        "rainbow_chaos_vol_1" ->
            stringResource(R.string.collection_rainbow_chaos_title)

        "rainy_nights_vol_1" ->
            stringResource(R.string.collection_rainy_nights_title)

        "enchanted_forest_vol_1" ->
            stringResource(R.string.enchanted_forest_title)

        "unicorn_overload_vol_1" ->
            stringResource(R.string.collection_unicorn_overload_title)

        "mystic_cats_vol_1" ->
            stringResource(R.string.collection_mystic_cats_title)

        else ->
            collection.title
    }
}

@Composable
fun getLocalizedCollectionSubtitle(
    subtitle: String
): String {
    return when (subtitle) {

        "Wild, Colorful and Sweet Fantasy Wallpapers" ->
            stringResource(R.string.collection_rainbow_chaos_subtitle)

        "Atmospheric Rainy Night Wallpapers" ->
            stringResource(R.string.collection_rainy_nights_subtitle)

        "Wild, Funny and Chaotic Wallpapers" ->
            stringResource(R.string.collection_tiny_chaos_subtitle)

        "Wild, Colorful and Magical Unicorn Wallpapers" ->
            stringResource(R.string.collection_unicorn_overload_subtitle)

        "12 Mystical and Magical Cat Wallpapers" ->
            stringResource(R.string.collection_mystic_cats_subtitle)

        "Warm Autumn Wallpapers" ->
            stringResource(R.string.collection_autumn_cozy_subtitle)

        "12 Magical Forest Wallpapers" ->
            stringResource(R.string.enchanted_forest_subtitle)

        "12 Original Zodiac Wallpapers" ->
            stringResource(R.string.collection_zodiac_subtitle)

        "12 Cinematic Animal Wallpapers" ->
            stringResource(R.string.collection_dream_animals_subtitle)

        "12 Adorable Animal Wallpapers" ->
            stringResource(R.string.collection_cute_animals_subtitle)

        "10 Magical Places in Poland" ->
            stringResource(R.string.collection_polish_wonders_subtitle)

        "Magical Castles and Enchanted Worlds" ->
            stringResource(R.string.collection_fantasy_realms_subtitle)

        "Cute Baby Dragons in Magical Worlds" ->
            stringResource(R.string.collection_chibi_dragons_subtitle)

        "10 Funny Dinosaur Adventures" ->
            stringResource(R.string.collection_funny_dinosaurs_subtitle)

        "12 Exclusive Space Wallpapers" ->
            stringResource(R.string.collection_cosmic_dreams_subtitle)

        "12 Exclusive Tropical Wallpapers" ->
            stringResource(R.string.collection_tropical_paradise_subtitle)

        "12 Exclusive Hypercar Wallpapers" ->
            stringResource(R.string.collection_hypercars_subtitle)

        "12 Exclusive Mountain Wallpapers" ->
            stringResource(R.string.collection_epic_mountains_subtitle)

        "12 Exclusive Cyberpunk Wallpapers" ->
            stringResource(R.string.collection_cyberpunk_cities_subtitle)

        "10 Delicious Little Monsters" ->
            stringResource(R.string.collection_food_monsters_subtitle)

        else ->
            subtitle
    }
}

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
            .background(WalloraBackground)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 42.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF1E1E1E),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clickable {
                        onBackClick()
                    }
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = stringResource(R.string.back),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Column {

                Text(
                    text = getLocalizedCollectionTitle(collection),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = getLocalizedCollectionSubtitle(
                        collection.subtitle
                    ),
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