package com.example.wallpapersapp.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapersapp.model.CategoryRepository
import com.example.wallpapersapp.model.Wallpaper
import com.example.wallpapersapp.model.WallpaperRepository
import com.example.wallpapersapp.storage.FavoritesStorage
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.foundation.lazy.LazyColumn
import com.example.wallpapersapp.screens.HomeHeader
import android.app.WallpaperManager
import androidx.compose.ui.draw.alpha
import androidx.compose.animation.core.animateDpAsState
@Composable
fun WallpaperScreen() {
    val wallpapers = WallpaperRepository.wallpapers
    val categories = CategoryRepository.categories
    val context = LocalContext.current

    var selectedWallpaper by remember { mutableStateOf<Wallpaper?>(null) }
    var showCollectionScreen by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("home") }
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedTrending by remember { mutableStateOf("Popular") }

    val favoriteNames = remember {
        mutableStateListOf<String>().apply {
            addAll(FavoritesStorage.loadFavorites(context))
        }
    }

    val filteredWallpapers = wallpapers.filter { wallpaper ->
        val cleanCategory = selectedCategory.substringAfter(" ").trim()

        val matchesSearch = wallpaper.name.contains(searchText, ignoreCase = true)

        val matchesCategory =
            cleanCategory == "All" || wallpaper.category == cleanCategory

        matchesSearch && matchesCategory
    }
    val displayedWallpapers = when (selectedTrending) {
        "New" -> filteredWallpapers.reversed()
        "Editor’s Choice" -> filteredWallpapers.filter { it.isTopPick }
        else -> filteredWallpapers
    }

    if (showCollectionScreen) {
        CollectionScreen(
            wallpapers = wallpapers,
            favoriteNames = favoriteNames,
            onWallpaperClick = { selectedWallpaper = it },
            onBackClick = { showCollectionScreen = false }
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
            onBack = { selectedWallpaper = null }
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
                        onWallpaperClick = { selectedWallpaper = it },
                        onFeaturedCollectionClick = {
                            showCollectionScreen = true
                        }
                    )

                    "favorites" -> FavoritesScreen(
                        wallpapers = wallpapers.filter { favoriteNames.contains(it.name) },
                        favoriteNames = favoriteNames,
                        onWallpaperClick = { selectedWallpaper = it }
                    )

                    "settings" -> SettingsScreen()
                }
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
    onFeaturedCollectionClick: () -> Unit
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
        onFeaturedCollectionClick = onFeaturedCollectionClick
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

        SettingItem(
            title = "🌙 Dark Theme",
            subtitle = "Enabled"
        )

        SettingItem(
            title = "⭐ Rate Wallora",
            subtitle = "Coming soon"
        )

        SettingItem(
            title = "📤 Share Wallora",
            subtitle = "Coming soon"
        )

        SettingItem(
            title = "📧 Contact Developer",
            subtitle = "Coming soon"
        )

        SettingItem(
            title = "ℹ Version",
            subtitle = "0.1.0"
        )
    }
}
@Composable
fun SettingItem(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(
                color = Color(0xFF171717),
                shape = RoundedCornerShape(18.dp)
            )
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
    onFeaturedCollectionClick: () -> Unit
) {
    val heroWallpaper = remember(wallpapers) {
        wallpapers
            .filter { it.isTopPick }
            .ifEmpty { wallpapers }
            .randomOrNull()
    }
    LazyColumn(
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
                favoriteInfo = favoriteInfo
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
            FeaturedCollection(
                onClick = onFeaturedCollectionClick
            )
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

        item {
            WallpaperGrid(
                wallpapers = wallpapers,
                favoriteNames = favoriteNames,
                onWallpaperClick = onWallpaperClick
            )
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

@Composable
fun WallpaperCard(
    wallpaper: Wallpaper,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        label = "cardScale"
    )

    val cardAlpha by animateFloatAsState(
        targetValue = if (pressed) 0.88f else 1f,
        label = "cardAlpha"
    )

    val elevation by animateDpAsState(
        targetValue = if (pressed) 2.dp else 8.dp,
        label = "cardElevation"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .scale(scale)
            .alpha(cardAlpha)
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = wallpaper.image),
                contentDescription = wallpaper.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
                    .background(
                        Color(0xAA000000),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "✨ 4K",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
                    .background(
                        Color.Black.copy(alpha = 0.55f),
                        RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = if (wallpaper.isTopPick) "👑 TOP PICK" else wallpaper.badge,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isFavorite) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .background(
                            Color.Black.copy(alpha = 0.55f),
                            RoundedCornerShape(50.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = "❤",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = wallpaper.name,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "★ ${wallpaper.rating} • ${wallpaper.downloads} downloads",
                    color = Color(0xFFD0D0D0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


fun saveWallpaperToGallery(context: Context, wallpaper: Wallpaper): Boolean {
    return try {
        val bitmap = BitmapFactory.decodeResource(context.resources, wallpaper.image)
            ?: return false

        val safeName = wallpaper.name
            .replace(" ", "_")
            .replace("/", "_")
            .replace("\\", "_")

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Wallora_${safeName}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Wallora")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: return false

        resolver.openOutputStream(uri).use { outputStream ->
            if (outputStream == null) return false
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
        }

        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, values, null, null)

        true
    } catch (exception: Exception) {
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