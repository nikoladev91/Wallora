package com.example.wallpapersapp.screens

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.CategoryRepository
import com.example.wallpapersapp.model.CollectionRepository
import com.example.wallpapersapp.model.Wallpaper
import com.example.wallpapersapp.model.WallpaperCollection
import com.example.wallpapersapp.model.WallpaperRepository
import com.example.wallpapersapp.monetization.AdManager
import com.example.wallpapersapp.storage.FavoritesStorage
import androidx.compose.foundation.lazy.items
import com.example.wallpapersapp.monetization.AdBanner
import androidx.compose.material3.Divider
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import android.os.Build
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
@Composable
fun WallpaperScreen() {
    val wallpapers = WallpaperRepository.wallpapers
    val categories = CategoryRepository.categories
    val context = LocalContext.current

    var selectedWallpaper by remember { mutableStateOf<Wallpaper?>(null) }
    var showCollectionScreen by remember { mutableStateOf(false) }
    var returnToCollection by remember { mutableStateOf(false) }
    var selectedCollection by remember {
        mutableStateOf(CollectionRepository.collections.first())
    }
    var selectedTab by remember { mutableStateOf("home") }
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
        val matchesSearch = wallpaper.name.contains(searchText, ignoreCase = true)
        val matchesCategory = cleanCategory == "All" || wallpaper.category == cleanCategory

        matchesSearch && matchesCategory
    }

    val displayedWallpapers = when (selectedTrending) {
        "Popular" -> filteredWallpapers.sortedByDescending {
            downloadsToNumber(it.downloads)
        }

        "New" -> filteredWallpapers.reversed()

        "Editor’s Choice" -> filteredWallpapers.filter {
            it.isTopPick
        }

        else -> filteredWallpapers
    }

    fun openWallpaper(
        wallpaper: Wallpaper,
        fromCollection: Boolean
    ) {
        selectedWallpaper = wallpaper
        returnToCollection = fromCollection

        if (AdManager.shouldShowInterstitial()) {
            Toast.makeText(
                context,
                "Ad would show here",
                Toast.LENGTH_SHORT
            ).show()
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
                if (favoriteNames.contains(selectedWallpaper!!.name)) {
                    favoriteNames.remove(selectedWallpaper!!.name)
                } else {
                    favoriteNames.add(selectedWallpaper!!.name)
                }

                FavoritesStorage.saveFavorites(
                    context = context,
                    favorites = favoriteNames.toSet()
                )
            },
            onDownloadClick = {
                val saved = saveWallpaperToGallery(
                    context = context,
                    wallpaper = selectedWallpaper!!
                )

                Toast.makeText(
                    context,
                    if (saved) "Wallpaper saved successfully" else "Could not save wallpaper",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onSetWallpaperClick = {
                val success = setWallpaper(
                    context = context,
                    wallpaper = selectedWallpaper!!
                )

                Toast.makeText(
                    context,
                    if (success) "Wallpaper set successfully" else "Could not set wallpaper",
                    Toast.LENGTH_SHORT
                ).show()
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
                            showCollectionScreen = true
                        },
                        listState = homeListState
                    )

                    "favorites" -> FavoritesScreen(
                        wallpapers = wallpapers.filter { favoriteNames.contains(it.name) },
                        favoriteNames = favoriteNames,
                        onWallpaperClick = {
                            openWallpaper(it, fromCollection = false)
                        }
                    )

                    "settings" -> SettingsScreen()
                }
            }

            if (selectedTab == "home") {
                AdBanner(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            BottomMenu(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
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
        listState = listState
    )
}

@Composable
fun FavoritesScreen(
    wallpapers: List<Wallpaper>,
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "❤️ Favorites",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${wallpapers.size} saved wallpapers",
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (wallpapers.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "❤️", fontSize = 56.sp)

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
        } else {
            WallpaperGrid(
                wallpapers = wallpapers,
                favoriteNames = favoriteNames,
                onWallpaperClick = onWallpaperClick
            )
        }
    }
}

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val developerEmail = "wallora.support@gmail.com"
    var showAboutDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {

        Text(
            text = "⚙ Settings",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "General",
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingItem(
            title = "⭐ Rate Wallora",
            subtitle = "Support the project",
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.example.wallpapersapp")
                )
                context.startActivity(intent)
            }
        )

        SettingItem(
            title = "📤 Share Wallora",
            subtitle = "Invite your friends",
            onClick = {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out Wallora - Premium Wallpapers!\n\nhttps://play.google.com/store/apps/details?id=com.example.wallpapersapp"
                    )
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Share Wallora")

                context.startActivity(shareIntent)
            }
        )

        SettingItem(
            title = "📜 Privacy Policy",
            subtitle = "Read our privacy policy"
        )

        SettingItem(
            title = "📧 Contact Developer",
            subtitle = "Send feedback",
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$developerEmail")
                    putExtra(Intent.EXTRA_SUBJECT, "Wallora Feedback")
                }

                context.startActivity(emailIntent)
            }
        )

        SettingItem(
            title = "ℹ About Wallora",
            subtitle = "App information",
            onClick = {
                showAboutDialog = true
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Divider(
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Version 1.0.0",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = "Made with ❤ in Poland",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = {
                showAboutDialog = false
            },
            title = {
                Text(
                    text = "Wallora",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "AI • 4K • AMOLED Wallpapers",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Discover premium AI-generated wallpapers designed for Android devices."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Version 1.0.0")
                    Text(text = "Support: wallora.support@gmail.com")

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "© 2026 Wallora")
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
            .clickable { onClick() }
            .background(Color(0xFF171717))
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
    listState: LazyListState
) {
    val heroWallpaper = remember(wallpapers) {
        wallpapers
            .filter { it.isTopPick }
            .ifEmpty { wallpapers }
            .randomOrNull()
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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

        item {
            Text(
                text = "🔥 Today's Trending",
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

        items(
            items = wallpapers.chunked(2)
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
                    Spacer(modifier = Modifier.weight(1f))
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
                color = if (selected) Color(0xFF64B5F6) else Color(0xFF222222),
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
        val bitmap = BitmapFactory.decodeResource(context.resources, wallpaper.image)
            ?: return false

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
