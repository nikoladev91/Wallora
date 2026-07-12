package com.example.wallpapersapp.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategorySection(
    categories: List<String>,
    selectedCategory: String,
    onCategoryClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val cleanCategory = category
                .substringAfter(" ")
                .trim()

            val cleanSelectedCategory = selectedCategory
                .substringAfter(" ")
                .trim()

            CategoryButton(
                name = category,
                selected = cleanCategory == cleanSelectedCategory,
                onClick = {
                    onCategoryClick(category)
                }
            )
        }
    }
}