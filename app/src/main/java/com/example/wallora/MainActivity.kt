package com.example.wallora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import com.example.wallora.screens.WallpaperScreen
import com.google.android.gms.ads.MobileAds
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                WallpaperScreen()
            }
        }
    }
}