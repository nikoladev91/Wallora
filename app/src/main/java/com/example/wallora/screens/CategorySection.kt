package com.example.wallora.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Widgets
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

    return when (cleanCategory) {

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

            val icon = when (cleanCategory) {

                "All" ->
                    Icons.Default.Widgets

                "Animals" ->
                    Icons.Default.Pets

                "Nature" ->
                    Icons.Default.Forest

                "Space" ->
                    Icons.Default.Public

                "Zodiac" ->
                    Icons.Default.Star

                "Cyberpunk Cities" ->
                    Icons.Default.ViewInAr

                "Cars" ->
                    Icons.Default.DirectionsCar

                else ->
                    Icons.Default.Widgets
            }

            CategoryButton(
                name = localizedCategoryName(category),
                icon = icon,
                selected = cleanCategory == cleanSelectedCategory,
                onClick = {
                    onCategoryClick(category)
                }
            )
        }
    }
}