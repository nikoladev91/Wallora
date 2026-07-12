package com.example.wallpapersapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrendingSection(
    selectedTrending: String,
    onTrendingSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TrendingChipButton(
            text = "🔥 Popular",
            value = "Popular",
            selected = selectedTrending == "Popular",
            onClick = onTrendingSelected
        )

        TrendingChipButton(
            text = "🆕 New",
            value = "New",
            selected = selectedTrending == "New",
            onClick = onTrendingSelected
        )

        TrendingChipButton(
            text = "⭐ Editor's Choice",
            value = "Editor's Choice",
            selected = selectedTrending == "Editor's Choice",
            onClick = onTrendingSelected
        )
    }
}

@Composable
fun TrendingChipButton(
    text: String,
    value: String,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    Text(
        text = text,
        color = if (selected) Color.Black else Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(
                color = if (selected) {
                    Color(0xFF64B5F6)
                } else {
                    Color(0xFF1E1E1E)
                },
                shape = RoundedCornerShape(50.dp)
            )
            .clickable {
                onClick(value)
            }
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
    )
}