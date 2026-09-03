package com.example.wallora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.wallora.ui.theme.WalloraAccent

@Composable
fun BottomMenu(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111111))
            .navigationBarsPadding()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(
            text = "🏠 ${stringResource(R.string.home)}",
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") },
            modifier = Modifier.weight(1f)
        )

        MenuItem(
            text = "❤️ ${stringResource(R.string.favorites)}",
            selected = selectedTab == "favorites",
            onClick = { onTabSelected("favorites") },
            modifier = Modifier.weight(1f)
        )

        MenuItem(
            text = "⚙️ ${stringResource(R.string.settings)}",
            selected = selectedTab == "settings",
            onClick = { onTabSelected("settings") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun MenuItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .heightIn(min = 56.dp)
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) WalloraAccent else Color.White,
            fontSize = if (selected) 17.sp else 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}