package com.example.wallora.screens

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.wallora.model.CategoryRepository
import com.example.wallora.model.CollectionRepository
import com.example.wallora.model.Wallpaper
import com.example.wallora.model.WallpaperCollection
import com.example.wallora.model.WallpaperRepository
import com.example.wallora.monetization.AdManager
import com.example.wallora.storage.FavoritesStorage
import androidx.compose.foundation.lazy.items
import com.example.wallora.monetization.AdBanner
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.clip
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import android.os.Build
import java.util.Calendar
import android.app.Activity
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
import com.example.wallora.analytics.AnalyticsManager
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.wallora.analytics.CrashlyticsManager
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import com.example.wallora.ui.theme.WalloraAccent
import com.example.wallora.ui.theme.WalloraBackground
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import com.example.wallora.ui.theme.WalloraSurface
import androidx.compose.foundation.verticalScroll



private fun downloadsToNumber(downloads: String): Int {
    val cleanValue = downloads
        .uppercase()
        .replace(",", ".")
        .trim()

    return when {
        cleanValue.endsWith("K") -> {
            cleanValue
                .removeSuffix("K")
                .toDoubleOrNull()
                ?.times(1000)
                ?.toInt()
                ?: 0
        }

        cleanValue.endsWith("M") -> {
            cleanValue
                .removeSuffix("M")
                .toDoubleOrNull()
                ?.times(1_000_000)
                ?.toInt()
                ?: 0
        }

        else -> cleanValue.toIntOrNull() ?: 0
    }
}

private fun matchesWallpaperSearch(
    wallpaper: Wallpaper,
    query: String
): Boolean {
    val cleanQuery = query.trim().lowercase()

    if (cleanQuery.isBlank()) {
        return true
    }

    fun matchesText(text: String): Boolean {
        val normalizedText = text.trim().lowercase()

        if (normalizedText == cleanQuery) {
            return true
        }

        val words = normalizedText
            .split(Regex("[^a-z0-9]+"))
            .filter { it.isNotBlank() }

        return words.any { word ->
            word == cleanQuery
        }
    }

    return matchesText(wallpaper.name) ||
            matchesText(wallpaper.category) ||
            wallpaper.tags.any { tag ->
                matchesText(tag)
            }
}
private fun Context.findActivity(): Activity? {
    var currentContext = this

    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }

        currentContext = currentContext.baseContext
    }

    return currentContext as? Activity
}
@Composable
fun WallpaperScreen() {
    val wallpapers = WallpaperRepository.wallpapers
    val categories = CategoryRepository.categories
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferences = remember {
        context.getSharedPreferences(
            "wallora_preferences",
            android.content.Context.MODE_PRIVATE
        )
    }

    var termsAccepted by remember {
        mutableStateOf(
            preferences.getBoolean("terms_accepted", false)
        )
    }
    LaunchedEffect(Unit) {
        AdManager.loadInterstitial(context)
    }
    var selectedWallpaper by remember { mutableStateOf<Wallpaper?>(null) }
    var showCollectionScreen by remember { mutableStateOf(false) }
    var returnToCollection by remember { mutableStateOf(false) }
    var selectedCollection by remember {
        mutableStateOf(CollectionRepository.collections.first())
    }
    var selectedTab by remember { mutableStateOf("home") }
    var legalPageUrl by remember { mutableStateOf<String?>(null) }
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedTrending by remember { mutableStateOf("Popular") }
    val homeListState = rememberLazyListState()

    val favoriteNames = remember {
        mutableStateListOf<String>().apply {
            addAll(FavoritesStorage.loadFavorites(context))
        }
    }

    val filteredWallpapers = wallpapers.filter { wallpaper ->
        val cleanCategory = selectedCategory.substringAfter(" ").trim()

        val matchesSearch =
            matchesWallpaperSearch(
                wallpaper = wallpaper,
                query = searchText
            )

        val matchesCategory =
            cleanCategory == "All" ||
                    wallpaper.category == cleanCategory

        matchesSearch && matchesCategory
    }

    val displayedWallpapers = when (selectedTrending) {
        "Popular" -> filteredWallpapers.sortedByDescending {
            downloadsToNumber(it.downloads)
        }

        "New" -> filteredWallpapers.reversed()

        "Editor's Choice" -> filteredWallpapers.filter {
            it.isTopPick
        }

        else -> filteredWallpapers
    }

    fun openWallpaper(
        wallpaper: Wallpaper,
        fromCollection: Boolean
    ) {
        AnalyticsManager.logWallpaperOpen(wallpaper.name)
        CrashlyticsManager.wallpaperOpened(wallpaper.name)

        selectedWallpaper = wallpaper
        returnToCollection = fromCollection

        if (AdManager.shouldShowInterstitial()) {
            context.findActivity()?.let { activity ->
                AdManager.showInterstitialIfReady(activity)
            }
        }
    }
    if (showCollectionScreen) {
        CollectionScreen(
            collection = selectedCollection,
            favoriteNames = favoriteNames,
            onWallpaperClick = {
                openWallpaper(it, fromCollection = true)
                showCollectionScreen = false
            },
            onBackClick = {
                selectedWallpaper = null
                showCollectionScreen = false
                selectedTab = "home"
            }
        )
    } else if (selectedWallpaper != null) {
        FullScreenWallpaper(
            wallpaper = selectedWallpaper!!,
            isFavorite = favoriteNames.contains(selectedWallpaper!!.name),
            onFavoriteClick = {
                val wallpaperName = selectedWallpaper!!.name

                if (favoriteNames.contains(wallpaperName)) {
                    favoriteNames.remove(wallpaperName)
                } else {
                    favoriteNames.add(wallpaperName)
                    AnalyticsManager.logFavoriteAdd(wallpaperName)
                }

                FavoritesStorage.saveFavorites(
                    context = context,
                    favorites = favoriteNames.toSet()
                )
            },
            onDownloadClick = {
                val wallpaperToSave = selectedWallpaper
                    ?: return@FullScreenWallpaper

                val saveWallpaper: () -> Unit = {
                    coroutineScope.launch {
                        val saved = withContext(Dispatchers.IO) {
                            saveWallpaperToGallery(
                                context = context,
                                wallpaper = wallpaperToSave
                            )
                        }

                        if (saved) {
                            AnalyticsManager.logWallpaperDownload(
                                wallpaperToSave.name
                            )
                        }

                        Toast.makeText(
                            context,
                            if (saved) {
                                "Wallpaper saved successfully"
                            } else {
                                "Could not save wallpaper"
                            },
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val activity = context as? Activity

                if (activity != null) {
                    AdManager.showInterstitialBeforePremiumAction(
                        activity = activity,
                        onFinished = saveWallpaper
                    )
                } else {
                    saveWallpaper()
                }
            },
            onSetWallpaperClick = {

                val wallpaperToSet = selectedWallpaper
                    ?: return@FullScreenWallpaper

                val setSelectedWallpaper: () -> Unit = {
                    coroutineScope.launch {

                        val success = withContext(Dispatchers.IO) {
                            setWallpaper(
                                context = context,
                                wallpaper = wallpaperToSet
                            )
                        }

                        if (success) {
                            AnalyticsManager.logWallpaperSet(wallpaperToSet.name)
                        }

                        Toast.makeText(
                            context,
                            if (success) {
                                "Wallpaper set successfully"
                            } else {
                                "Could not set wallpaper"
                            },
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val activity = context as? Activity

                if (activity != null) {
                    AdManager.showInterstitialBeforePremiumAction(
                        activity = activity,
                        onFinished = setSelectedWallpaper
                    )
                } else {
                    setSelectedWallpaper()
                }
            },
            onBack = {
                selectedWallpaper = null
                showCollectionScreen = returnToCollection
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                if (legalPageUrl != null) {

                    LegalDocumentScreen(
                        url = legalPageUrl!!,
                        onBack = {
                            legalPageUrl = null
                        }
                    )

                } else {

                    when (selectedTab) {

                        "home" -> HomeScreen(
                            wallpapers = displayedWallpapers,
                            allWallpapersCount = wallpapers.size,
                            favoriteNames = favoriteNames,
                            searchText = searchText,
                            selectedCategory = selectedCategory,
                            selectedTrending = selectedTrending,
                            categories = categories.map { it.name },
                            onSearchChange = { searchText = it },
                            onCategoryClick = { selectedCategory = it },
                            onTrendingSelected = { selectedTrending = it },
                            onWallpaperClick = {
                                openWallpaper(it, fromCollection = false)
                            },
                            onFeaturedCollectionClick = { collection ->
                                selectedCollection = collection
                                AnalyticsManager.logCollectionOpen(collection.title)
                                showCollectionScreen = true
                            },
                            onNewCollectionClick = {
                                CollectionRepository.collections
                                    .firstOrNull { it.id == "autumn_cozy_vol_1" }
                                    ?.let { collection ->
                                        selectedCollection = collection
                                        AnalyticsManager.logCollectionOpen(collection.title)
                                        showCollectionScreen = true
                                    }
                            },
                            listState = homeListState
                        )

                        "favorites" -> FavoritesScreen(
                            wallpapers = wallpapers.filter {
                                favoriteNames.contains(it.name)
                            },
                            favoriteNames = favoriteNames,
                            onWallpaperClick = {
                                openWallpaper(it, fromCollection = false)
                            }
                        )

                        "settings" -> SettingsScreen(
                            onPrivacyPolicyClick = {
                                legalPageUrl =
                                    "https://nikoladev91.github.io/wallora-privacy-policy/"
                            },
                            onTermsOfUseClick = {
                                legalPageUrl =
                                    "https://nikoladev91.github.io/wallora-privacy-policy/terms.html?v=2"
                            }
                        )
                    }
                }
            }

            if (legalPageUrl == null) {

                if (selectedTab == "home") {
                    AdBanner(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }

                BottomMenu(
                    selectedTab = selectedTab,
                    onTabSelected = {
                        selectedTab = it
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    wallpapers: List<Wallpaper>,
    allWallpapersCount: Int,
    favoriteNames: List<String>,
    searchText: String,
    selectedCategory: String,
    selectedTrending: String,
    categories: List<String>,
    onSearchChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onTrendingSelected: (String) -> Unit,
    onWallpaperClick: (Wallpaper) -> Unit,
    onFeaturedCollectionClick: (WallpaperCollection) -> Unit,
    onNewCollectionClick: () -> Unit,
    listState: LazyListState
) {
    GalleryContent(
        title = "✦ Wallora",
        subtitle = "AI • 4K • AMOLED",
        favoriteInfo = "🔥 New wallpapers every week",
        wallpapers = wallpapers,
        favoriteNames = favoriteNames,
        searchText = searchText,
        selectedCategory = selectedCategory,
        selectedTrending = selectedTrending,
        categories = categories,
        showSearch = true,
        showCategories = true,
        onSearchChange = onSearchChange,
        onCategoryClick = onCategoryClick,
        onTrendingSelected = onTrendingSelected,
        onWallpaperClick = onWallpaperClick,
        onFeaturedCollectionClick = onFeaturedCollectionClick,
        onNewCollectionClick = onNewCollectionClick,
        listState = listState
    )
}

@Composable
fun FavoritesScreen(
    wallpapers: List<Wallpaper>,
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val filteredFavorites = wallpapers.filter { wallpaper ->
        matchesWallpaperSearch(
            wallpaper = wallpaper,
            query = searchText
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WalloraBackground)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .then(
                if (wallpapers.isNotEmpty()) {
                    Modifier.verticalScroll(scrollState)
                } else {
                    Modifier
                }
            )
    ) {

        Text(
            text = "❤️ Favorites",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${wallpapers.size} wallpapers saved",
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (wallpapers.isNotEmpty()) {
            SearchSection(
                searchText = searchText,
                onSearchChange = { searchText = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (wallpapers.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "❤️",
                    fontSize = 56.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No favorites yet",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Add wallpapers to favorites and they will appear here.",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }

        } else if (filteredFavorites.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🔍",
                    fontSize = 48.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No favorites found",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Try another keyword",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

        } else {

            WallpaperGrid(
                wallpapers = filteredFavorites,
                favoriteNames = favoriteNames,
                onWallpaperClick = onWallpaperClick
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingsScreen(
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfUseClick: () -> Unit
) {
    val context = LocalContext.current
    val developerEmail = "wallora.support@gmail.com"
    var showAboutDialog by remember { mutableStateOf(false) }

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
            text = "⚙ Settings",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Background color",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Choose your Wallora style",
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
                name = "Black",
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
                name = "Charcoal",
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
                name = "Navy",
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
                name = "Purple",
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
                name = "Rose",
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
                name = "Green",
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
            title = "⭐ Rate Wallora",
            subtitle = "Support the project",
            onClick = {
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
        )

        SettingItem(
            title = "📤 Share Wallora",
            subtitle = "Invite your friends",
            onClick = {
                val appPackageName = context.packageName

                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out Wallora - Premium Wallpapers!\n\n" +
                                "https://play.google.com/store/apps/details?id=$appPackageName"
                    )
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(sendIntent, "Share Wallora")

                context.startActivity(shareIntent)
            }
        )

        SettingItem(
            title = "📜 Privacy Policy",
            subtitle = "Read our privacy policy",
            onClick = onPrivacyPolicyClick
        )

        SettingItem(
            title = "📄 Terms of Use",
            subtitle = "Read our terms of use",
            onClick = onTermsOfUseClick
        )

        SettingItem(
            title = "📧 Contact Developer",
            subtitle = "Send feedback",
            onClick = {
                val emailIntent =
                    Intent(Intent.ACTION_SENDTO).apply {
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
            title = "ℹ️ About Wallora",
            subtitle = "App information",
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
            text = "Version 1.1.1",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 14.sp
        )

        Text(
            text = "Made with ❤ in Poland",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 13.sp
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
                        text = "Premium Wallpapers",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            },

            text = {
                Column {

                    Text(
                        text = "✨ AI Crafted Wallpapers",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "📱 Optimized for AMOLED Displays",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "🌍 Weekly Wallpaper Collections",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "❤️ Made in Poland",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Version 1.0.1",
                        color = Color.Gray
                    )

                    Text(
                        text = "Developer",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Nikola Ławniczak"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "wallora.support@gmail.com",
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
                    Text("Close")
                }
            }
        )
    }
}
@Composable
fun LegalDocumentScreen(
    url: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WalloraBackground)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onBack() }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Back",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { context ->
                android.webkit.WebView(context).apply {
                    settings.javaScriptEnabled = false
                    webViewClient = android.webkit.WebViewClient()
                    loadUrl(url)
                }
            },
            update = { webView ->
                if (webView.url != url) {
                    webView.loadUrl(url)
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
@Composable
fun NewWallpapersBanner(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFF211B2E))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        Text(
            text = "🍂  NEW THIS WEEK",
            color = Color(0xFFFFC84A),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = subtitle,
            color = Color(0xFFD0D0D0),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Explore collection  →",
            color = Color(0xFFFFC84A),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GalleryContent(
    title: String,
    subtitle: String,
    favoriteInfo: String,
    wallpapers: List<Wallpaper>,
    favoriteNames: List<String>,
    searchText: String,
    selectedCategory: String,
    selectedTrending: String,
    categories: List<String>,
    showSearch: Boolean,
    showCategories: Boolean,
    onSearchChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onTrendingSelected: (String) -> Unit,
    onWallpaperClick: (Wallpaper) -> Unit,
    onFeaturedCollectionClick: (WallpaperCollection) -> Unit,
    onNewCollectionClick: () -> Unit,
    listState: LazyListState
) {
    val heroWallpaper = remember {
        val allWallpapers = WallpaperRepository.wallpapers

        val dailyWallpapers = allWallpapers
            .filter { it.isTopPick }
            .ifEmpty { allWallpapers }

        if (dailyWallpapers.isEmpty()) {
            null
        } else {
            val dayOfYear = Calendar.getInstance()
                .get(Calendar.DAY_OF_YEAR)

            val wallpaperIndex = dayOfYear % dailyWallpapers.size

            dailyWallpapers[wallpaperIndex]


        }
    }
    val displayedWallpapers = wallpapers

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(WalloraBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HomeHeader(
                title = title,
                subtitle = subtitle,
                favoriteInfo = favoriteInfo,
                stats = "${WallpaperRepository.wallpapers.size} Wallpapers • ${CollectionRepository.collections.size} Collections"
            )
        }

        item {
            NewWallpapersBanner(
                title = "Autumn Cozy is here",
                subtitle = "12 new wallpapers • warm autumn vibes",
                onClick = onNewCollectionClick
            )
        }

        if (heroWallpaper != null) {
            item {
                HeroBanner(
                    wallpaper = heroWallpaper,
                    onExploreClick = {
                        onWallpaperClick(heroWallpaper)
                    }
                )
            }
        }

        if (showSearch) {
            item {
                SearchSection(
                    searchText = searchText,
                    onSearchChange = onSearchChange
                )
            }
        }

        if (showCategories) {
            item {
                CategorySection(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategoryClick = onCategoryClick
                )
            }
        }
        if (searchText.isBlank()) {
            item {
                Text(
                    text = "⭐ Collections",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    CollectionRepository.collections.forEach { collection ->
                        Box(
                            modifier = Modifier.width(280.dp)
                        ) {
                            FeaturedCollection(
                                collection = collection,
                                onClick = {
                                    onFeaturedCollectionClick(collection)
                                }
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Explore Wallpapers",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                TrendingSection(
                    selectedTrending = selectedTrending,
                    onTrendingSelected = onTrendingSelected
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

        } else {
            item {
                Text(
                    text = "Search results",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (displayedWallpapers.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "No wallpapers found",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Try another keyword",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        items(
            items = displayedWallpapers.chunked(2)
        ) { rowWallpapers ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                rowWallpapers.forEach { wallpaper ->
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        WallpaperCard(
                            wallpaper = wallpaper,
                            isFavorite = favoriteNames.contains(wallpaper.name),
                            onClick = {
                                onWallpaperClick(wallpaper)
                            }
                        )
                    }
                }

                if (rowWallpapers.size == 1) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
@Composable
fun CategoryButton(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = name,
        color = if (selected) Color.Black else Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(end = 8.dp)
            .background(
                color = if (selected) WalloraAccent else Color(0xFF222222),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    )
}

fun saveWallpaperToGallery(
    context: Context,
    wallpaper: Wallpaper
): Boolean {
    var uri: Uri? = null

    return try {
        val safeName = wallpaper.name
            .replace(" ", "_")
            .replace("/", "_")
            .replace("\\", "_")

        val values = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "Wallora_${safeName}.png"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "Pictures/Wallora"
                )
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver

        uri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: return false

        context.resources.openRawResource(wallpaper.image).use { input ->
            resolver.openOutputStream(uri!!)?.use { output ->
                input.copyTo(output)
            } ?: return false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val completedValues = ContentValues().apply {
                put(MediaStore.Images.Media.IS_PENDING, 0)
            }

            resolver.update(
                uri!!,
                completedValues,
                null,
                null
            )
        }

        true
    } catch (exception: Exception) {
        uri?.let {
            context.contentResolver.delete(it, null, null)
        }

        exception.printStackTrace()
        false
    }
}

fun setWallpaper(context: Context, wallpaper: Wallpaper): Boolean {
    return try {
        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            wallpaper.image
        ) ?: return false

        val wallpaperManager = WallpaperManager.getInstance(context)
        wallpaperManager.setBitmap(bitmap)

        true
    } catch (exception: Exception) {
        exception.printStackTrace()
        false
    }
}



@Composable
fun TrendingChip(text: String) {
    Box(
        modifier = Modifier
            .background(
                Color(0xFF1E1E1E),
                RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}