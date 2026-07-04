package com.example.wallpapersapp.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchSection(
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchChange,
        placeholder = {
            Text(text = "Search wallpapers...")
        },
        modifier = Modifier.fillMaxWidth()
    )
}