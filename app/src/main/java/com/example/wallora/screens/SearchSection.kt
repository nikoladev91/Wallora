package com.example.wallora.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.wallora.analytics.AnalyticsManager
import com.example.wallora.ui.theme.WalloraAccent
import com.example.wallora.ui.theme.WalloraSurface
import androidx.compose.ui.res.stringResource
import com.example.wallora.R
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

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onSearchChange(newValue.text)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_wallpapers),
                color = Color.White.copy(alpha = 0.65f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = WalloraSurface,
            unfocusedContainerColor = WalloraSurface,
            disabledContainerColor = WalloraSurface,

            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            cursorColor = WalloraAccent,

            focusedBorderColor = WalloraAccent,
            unfocusedBorderColor = Color.White.copy(alpha = 0.45f),

            focusedPlaceholderColor = Color.White.copy(alpha = 0.65f),
            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.65f)
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