package com.example.wallora

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wallora.analytics.AnalyticsManager
import com.example.wallora.analytics.CrashlyticsManager
import com.example.wallora.screens.WallpaperScreen
import com.example.wallora.ui.theme.WalloraAccent
import com.example.wallora.ui.theme.WalloraBackground
import com.example.wallora.ui.theme.WalloraSurface
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // Użytkownik sam decyduje,
            // czy chce otrzymywać powiadomienia.
        }

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

        when (savedBackgroundColor) {

            0xFF2B2B2B -> {
                WalloraBackground = Color(0xFF2B2B2B)
                WalloraSurface = Color(0xFF3A3A3A)
                WalloraAccent = Color(0xFF5A5A5A)
            }

            0xFF0F1F3A -> {
                WalloraBackground = Color(0xFF0F1F3A)
                WalloraSurface = Color(0xFF17365D)
                WalloraAccent = Color(0xFF2F6FDB)
            }

            0xFF4B2E83 -> {
                WalloraBackground = Color(0xFF4B2E83)
                WalloraSurface = Color(0xFF5B3A9B)
                WalloraAccent = Color(0xFF8B5CF6)
            }

            0xFF6A1E2E -> {
                WalloraBackground = Color(0xFF6A1E2E)
                WalloraSurface = Color(0xFF81283B)
                WalloraAccent = Color(0xFFFF5CA8)
            }

            0xFF00A86B -> {
                WalloraBackground = Color(0xFF00A86B)
                WalloraSurface = Color(0xFF008C57)
                WalloraAccent = Color(0xFF00D98B)
            }

            else -> {
                WalloraBackground = Color(0xFF000000)
                WalloraSurface = Color(0xFF171717)
                WalloraAccent = Color(0xFF3A3A3A)
            }
        }

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                WallpaperScreen()
            }
        }

        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val permissionGranted =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (!permissionGranted) {
                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
}