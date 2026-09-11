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
import kotlinx.coroutines.delay
import androidx.compose.ui.res.stringResource
import com.example.wallora.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import com.example.wallora.storage.saveWallpaperToGallery
import com.example.wallora.storage.setWallpaper

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
fun WallpaperScreen(
    notificationCollectionId: String? = null,
    onNotificationCollectionHandled: () -> Unit = {}
) {
    val wallpapers = WallpaperRepository.wallpapers
    val categories = CategoryRepository.categories
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
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
    LaunchedEffect(notificationCollectionId) {

        val collectionId = notificationCollectionId
            ?: return@LaunchedEffect

        CollectionRepository.collections
            .firstOrNull { it.id == collectionId }
            ?.let { collection ->
                selectedCollection = collection
                selectedWallpaper = null
                showCollectionScreen = true
                selectedTab = "home"

                AnalyticsManager.logCollectionOpen(collection.title)
            }

        onNotificationCollectionHandled()
    }

    val favoriteNames = remember {
        mutableStateListOf<String>().apply {
            addAll(FavoritesStorage.loadFavorites(context))
        }
    }

    val filteredWallpapers = filterWallpapers(
        wallpapers = wallpapers,
        searchText = searchText,
        selectedCategory = selectedCategory
    )

    val displayedWallpapers = sortWallpapers(
        wallpapers = filteredWallpapers,
        selectedTrending = selectedTrending
    )

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

                    LegalDocumentScreenNew(
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
                            onCategoryClick = {
                                selectedCategory = it
                                searchText = ""
                            },
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
                                    .firstOrNull { it.id == "mystic_cats_vol_1" }
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
                                val language =
                                    configuration.locales[0].language

                                legalPageUrl = when (language) {
                                    "pl" ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/privacy-policy-pl.html?v=3"

                                    "de" ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/privacy-policy-de.html?v=3"

                                    "es" ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/privacy-policy-es.html?v=3"

                                    else ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/privacy-policy.html?v=3"
                                }
                            },

                            onTermsOfUseClick = {
                                val language =
                                    configuration.locales[0].language

                                legalPageUrl = when (language) {
                                    "pl" ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/terms-pl.html?v=1"

                                    "de" ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/terms-de.html?v=1"

                                    "es" ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/terms-es.html?v=1"

                                    else ->
                                        "https://nikoladev91.github.io/wallora-privacy-policy/terms.html?v=2"
                                }
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
                text = stringResource(R.string.back),
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
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            )
    ) {

        Text(
            text = "🔥  ${stringResource(R.string.new_this_week)}",
            color = Color(0xFFFFC84A),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = subtitle,
            color = Color(0xFFD0D0D0),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "${stringResource(R.string.explore_collection)}  →",
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
    LaunchedEffect(searchText) {
        if (searchText.isNotBlank()) {
            delay(300)

            listState.animateScrollToItem(
                index = 3,
                scrollOffset = -180
            )
        }
    }
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
                title = stringResource(R.string.mystic_cats_is_here),
                subtitle = stringResource(R.string.mystic_cats_banner_subtitle),
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
                    text = "⭐ ${stringResource(R.string.collections)}",
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
                    text = stringResource(R.string.explore_wallpapers),
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
                    text = stringResource(R.string.search_results),
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
                            text = stringResource(R.string.no_wallpapers_found),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(R.string.try_another_keyword),
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
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = 8.dp)
            .background(
                color = if (selected) Color(0xFFFFC857) else Color(0xFF222222),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Color.Black else Color.White,
            modifier = Modifier.size(18.dp)
        )

        Text(
            text = name,
            color = if (selected) Color.Black else Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable
fun TrendingChip(
    text: String
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF1E1E1E),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
