package com.example.wallora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wallora.analytics.AnalyticsManager
import com.example.wallora.analytics.CrashlyticsManager
import com.example.wallora.screens.WallpaperScreen
import com.example.wallora.ui.theme.WalloraBackground
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        AnalyticsManager.init(applicationContext)
        CrashlyticsManager.setUserId("developer")

        MobileAds.initialize(this)

        // Load the background color selected by the user
        val preferences = getSharedPreferences(
            "wallora_preferences",
            MODE_PRIVATE
        )

        val savedBackgroundColor = preferences.getLong(
            "background_color",
            0xFF000000
        )

        WalloraBackground = Color(savedBackgroundColor)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                WallpaperScreen()
            }
        }
    }
}