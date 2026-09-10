package com.example.wallora

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager

    private var notificationCollectionId by mutableStateOf<String?>(null)

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // Użytkownik sam decyduje,
            // czy chce otrzymywać powiadomienia.
        }

    private val updateLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            // Wynik procesu aktualizacji obsługuje Google Play.
        }

    private val installStateUpdatedListener =
        InstallStateUpdatedListener { state: InstallState ->

            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                appUpdateManager.completeUpdate()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        AnalyticsManager.init(applicationContext)
        CrashlyticsManager.setUserId("developer")

        MobileAds.initialize(this)

        appUpdateManager =
            AppUpdateManagerFactory.create(this)

        appUpdateManager.registerListener(
            installStateUpdatedListener
        )

        notificationCollectionId =
            intent.getStringExtra("collection_id")

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
                WallpaperScreen(
                    notificationCollectionId = notificationCollectionId,
                    onNotificationCollectionHandled = {
                        notificationCollectionId = null
                    }
                )
            }
        }

        requestNotificationPermission()
        checkForAppUpdate()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        notificationCollectionId =
            intent.getStringExtra("collection_id")
    }

    override fun onResume() {
        super.onResume()

        if (::appUpdateManager.isInitialized) {
            appUpdateManager.appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->

                    if (
                        appUpdateInfo.installStatus() ==
                        InstallStatus.DOWNLOADED
                    ) {
                        appUpdateManager.completeUpdate()
                    }
                }
        }
    }

    override fun onDestroy() {

        if (::appUpdateManager.isInitialized) {
            appUpdateManager.unregisterListener(
                installStateUpdatedListener
            )
        }

        super.onDestroy()
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

    private fun checkForAppUpdate() {

        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                val updateAvailable =
                    appUpdateInfo.updateAvailability() ==
                            UpdateAvailability.UPDATE_AVAILABLE

                val flexibleUpdateAllowed =
                    appUpdateInfo.isUpdateTypeAllowed(
                        AppUpdateType.FLEXIBLE
                    )

                if (
                    updateAvailable &&
                    flexibleUpdateAllowed
                ) {

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        updateLauncher,
                        AppUpdateOptions
                            .newBuilder(AppUpdateType.FLEXIBLE)
                            .build()
                    )
                }
            }
    }
}