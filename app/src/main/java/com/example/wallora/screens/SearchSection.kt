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
            Text(text = "Search wallpapers...")
        },
        modifier = Modifier.fillMaxWidth()
    )
}