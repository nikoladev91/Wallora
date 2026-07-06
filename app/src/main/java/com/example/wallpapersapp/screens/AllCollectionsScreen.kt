package com.example.wallpapersapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.CollectionRepository
import com.example.wallpapersapp.model.WallpaperCollection

@Composable
fun AllCollectionsScreen(
    onCollectionClick: (WallpaperCollection) -> Unit,
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
            Text(
                text = "← Back",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(vertical = 10.dp)
            )
        }

        item {
            Text(
                text = "⭐ All Collections",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = "${CollectionRepository.collections.size} collections",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }

        items(CollectionRepository.collections) { collection ->
            FeaturedCollection(
                collection = collection,
                onClick = {
                    onCollectionClick(collection)
                }
            )
        }
    }
}