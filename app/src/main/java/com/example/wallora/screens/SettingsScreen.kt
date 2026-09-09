package com.example.wallora.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallora.R
import com.example.wallora.ui.theme.WalloraAccent
import com.example.wallora.ui.theme.WalloraBackground
import com.example.wallora.ui.theme.WalloraSurface
import androidx.compose.ui.draw.clip
@Composable
fun SettingsScreen(
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfUseClick: () -> Unit
) {
    val context = LocalContext.current
    val developerEmail = "wallora.support@gmail.com"
    val appVersion = remember {
        try {
            context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName ?: ""
        } catch (exception: Exception) {
            ""
        }
    }

    var showAboutDialog by remember { mutableStateOf(false) }
    var showRateDialog by remember { mutableStateOf(false) }

    val shareMessage = stringResource(R.string.share_wallora_message)
    val shareChooserTitle = stringResource(R.string.share_wallora_chooser)

    val preferences = remember {
        context.getSharedPreferences(
            "wallora_preferences",
            android.content.Context.MODE_PRIVATE
        )
    }

    fun changeBackground(
        color: Color,
        surfaceColor: Color,
        accentColor: Color,
        colorValue: Long
    ) {
        WalloraBackground = color
        WalloraSurface = surfaceColor
        WalloraAccent = accentColor

        preferences.edit()
            .putLong("background_color", colorValue)
            .apply()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WalloraBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 120.dp)
    ) {

        Text(
            text = stringResource(R.string.settings_title),
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.background_color),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.choose_wallora_style),
            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            BackgroundColorOption(
                name = stringResource(R.string.color_black),
                color = Color(0xFF000000),
                selected = WalloraBackground == Color(0xFF000000),
                onClick = {
                    changeBackground(
                        color = Color(0xFF000000),
                        surfaceColor = Color(0xFF171717),
                        accentColor = Color(0xFF3A3A3A),
                        colorValue = 0xFF000000
                    )
                }
            )

            BackgroundColorOption(
                name = stringResource(R.string.color_charcoal),
                color = Color(0xFF2B2B2B),
                selected = WalloraBackground == Color(0xFF2B2B2B),
                onClick = {
                    changeBackground(
                        color = Color(0xFF2B2B2B),
                        surfaceColor = Color(0xFF3A3A3A),
                        accentColor = Color(0xFF5A5A5A),
                        colorValue = 0xFF2B2B2B
                    )
                }
            )

            BackgroundColorOption(
                name = stringResource(R.string.color_navy),
                color = Color(0xFF0F1F3A),
                selected = WalloraBackground == Color(0xFF0F1F3A),
                onClick = {
                    changeBackground(
                        color = Color(0xFF0F1F3A),
                        surfaceColor = Color(0xFF17365D),
                        accentColor = Color(0xFF2F6FDB),
                        colorValue = 0xFF0F1F3A
                    )
                }
            )

            BackgroundColorOption(
                name = stringResource(R.string.color_purple),
                color = Color(0xFF4B2E83),
                selected = WalloraBackground == Color(0xFF4B2E83),
                onClick = {
                    changeBackground(
                        color = Color(0xFF4B2E83),
                        surfaceColor = Color(0xFF5B3A9B),
                        accentColor = Color(0xFF8B5CF6),
                        colorValue = 0xFF4B2E83
                    )
                }
            )

            BackgroundColorOption(
                name = stringResource(R.string.color_rose),
                color = Color(0xFF6A1E2E),
                selected = WalloraBackground == Color(0xFF6A1E2E),
                onClick = {
                    changeBackground(
                        color = Color(0xFF6A1E2E),
                        surfaceColor = Color(0xFF81283B),
                        accentColor = Color(0xFFFF5CA8),
                        colorValue = 0xFF6A1E2E
                    )
                }
            )

            BackgroundColorOption(
                name = stringResource(R.string.color_green),
                color = Color(0xFF00A86B),
                selected = WalloraBackground == Color(0xFF00A86B),
                onClick = {
                    changeBackground(
                        color = Color(0xFF00A86B),
                        surfaceColor = Color(0xFF008C57),
                        accentColor = Color(0xFF00D98B),
                        colorValue = 0xFF00A86B
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        SettingItem(
            title = "⭐ ${stringResource(R.string.rate_wallora)}",
            subtitle = stringResource(R.string.support_project),
            onClick = {
                showRateDialog = true
            }
        )

        SettingItem(
            title = "📤 ${stringResource(R.string.share_wallora)}",
            subtitle = stringResource(R.string.invite_friends),
            onClick = {
                val appPackageName = context.packageName

                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND

                    putExtra(
                        Intent.EXTRA_TEXT,
                        "$shareMessage\n\n" +
                                "https://play.google.com/store/apps/details?id=$appPackageName"
                    )

                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(
                    sendIntent,
                    shareChooserTitle
                )

                context.startActivity(shareIntent)
            }
        )

        SettingItem(
            title = "📜 ${stringResource(R.string.privacy_policy)}",
            subtitle = stringResource(R.string.read_privacy_policy),
            onClick = onPrivacyPolicyClick
        )

        SettingItem(
            title = "📄 ${stringResource(R.string.terms_of_use)}",
            subtitle = stringResource(R.string.read_terms_of_use),
            onClick = onTermsOfUseClick
        )

        SettingItem(
            title = "📧 ${stringResource(R.string.contact_developer)}",
            subtitle = stringResource(R.string.send_feedback),
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$developerEmail")
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Wallora Feedback"
                    )
                }

                context.startActivity(emailIntent)
            }
        )

        SettingItem(
            title = "ℹ️ ${stringResource(R.string.about_wallora)}",
            subtitle = stringResource(R.string.app_information),
            onClick = {
                showAboutDialog = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Version $appVersion",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 14.sp
        )

        Text(
            text = stringResource(R.string.made_in_poland),
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 13.sp
        )
    }

    if (showRateDialog) {
        AlertDialog(
            onDismissRequest = {
                showRateDialog = false
            },
            title = {
                Text(
                    text = "⭐ ${stringResource(R.string.rate_wallora)}"
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.rate_dialog_message)
                )
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRateDialog = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cancel)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRateDialog = false

                        val appPackageName = context.packageName

                        val marketIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )

                        val webIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://play.google.com/store/apps/details?id=$appPackageName"
                            )
                        )

                        try {
                            context.startActivity(marketIntent)
                        } catch (exception: Exception) {
                            context.startActivity(webIntent)
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.rate)
                    )
                }
            }
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = {
                showAboutDialog = false
            },
            title = {
                Column {
                    Text(
                        text = "Wallora",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Text(
                        text = stringResource(R.string.premium_wallpapers),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = stringResource(R.string.ai_crafted_wallpapers),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(R.string.optimized_amoled),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(R.string.weekly_wallpaper_collections),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(R.string.made_in_poland_short),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Version $appVersion",
                        color = Color.Gray
                    )

                    Text(
                        text = stringResource(R.string.developer),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Nikola Ławniczak"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = developerEmail,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "© 2026 Wallora"
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAboutDialog = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.close)
                    )
                }
            }
        )
    }
}

@Composable
fun BackgroundColorOption(
    name: String,
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(58.dp)
            .clickable {
                onClick()
            }
    ) {

        Box(
            modifier = Modifier
                .size(46.dp)
                .background(
                    color = color,
                    shape = CircleShape
                )
                .border(
                    width = if (selected) 3.dp else 1.dp,
                    color = if (selected) Color.White else Color.Gray,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            if (selected) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            color = if (selected) Color.White else Color.LightGray,
            fontSize = 11.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            maxLines = 1
        )
    }
}


@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                onClick()
            }
            .background(WalloraSurface)
            .padding(16.dp)
    ) {

        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            color = Color.LightGray,
            fontSize = 14.sp
        )
    }
}
