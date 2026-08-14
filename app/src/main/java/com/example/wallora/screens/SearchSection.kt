package com.example.wallora.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import com.example.wallora.analytics.AnalyticsManager
import com.example.wallora.ui.theme.WalloraSurface
import androidx.compose.material3.TextFieldDefaults
import com.example.wallora.ui.theme.WalloraAccent
import androidx.compose.ui.graphics.Color
@Composable
fun SearchSection(
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = searchText,
                selection = TextRange(searchText.length)
            )
        )
    }

    LaunchedEffect(searchText) {
        if (searchText != textFieldValue.text) {
            textFieldValue = TextFieldValue(
                text = searchText,
                selection = TextRange(searchText.length)
            )
        }
    }

    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onSearchChange(newValue.text)
        },
        placeholder = {
            Text(
                text = "Search wallpapers...",
                color = Color.LightGray
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = WalloraSurface,
            unfocusedContainerColor = WalloraSurface,
            disabledContainerColor = WalloraSurface,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = WalloraAccent,
            focusedIndicatorColor = WalloraAccent,
            unfocusedIndicatorColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                val cleanTerm = textFieldValue.text.trim()

                if (cleanTerm.isNotBlank()) {
                    AnalyticsManager.logSearchUsed(cleanTerm)
                }
            }
        ),
        modifier = Modifier.fillMaxWidth()
    )
}