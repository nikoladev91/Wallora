package com.example.wallora.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wallora.R

@Composable
private fun localizedCategoryName(category: String): String {

    val cleanCategory = category
        .substringAfter(" ")
        .trim()

    val emoji = category
        .substringBefore(" ")
        .takeIf { it != category }
        .orEmpty()

    val localizedName = when (cleanCategory) {

        "All" ->
            stringResource(R.string.category_all)

        "Animals" ->
            stringResource(R.string.category_animals)

        "Nature" ->
            stringResource(R.string.category_nature)

        "Space" ->
            stringResource(R.string.category_space)

        "Zodiac" ->
            stringResource(R.string.category_zodiac)

        "Cyberpunk Cities" ->
            stringResource(R.string.category_cyberpunk_cities)

        "Cars" ->
            stringResource(R.string.category_cars)

        else ->
            cleanCategory
    }

    return if (emoji.isNotBlank()) {
        "$emoji $localizedName"
    } else {
        localizedName
    }
}

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
            .horizontalScroll(scrollState)
            .padding(end = 24.dp),
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
                name = localizedCategoryName(category),
                selected = cleanCategory == cleanSelectedCategory,
                onClick = {
                    // WAŻNE:
                    // do filtrowania nadal przekazujemy
                    // oryginalną angielską wartość
                    onCategoryClick(category)
                }
            )
        }
    }
}