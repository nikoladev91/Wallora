package com.example.wallpapersapp.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.Toast
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

@Composable
fun WallpaperScreen() {
    val wallpapers = WallpaperRepository.wallpapers
    val categories = CategoryRepository.categories
    val context = LocalContext.current

    var selectedWallpaper by remember { mutableStateOf<Wallpaper?>(null) }
    var selectedTab by remember { mutableStateOf("home") }
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val favoriteNames = remember {
        mutableStateListOf<String>().apply {
            addAll(FavoritesStorage.loadFavorites(context))
        }
    }

    val filteredWallpapers = wallpapers.filter { wallpaper ->
        val matchesSearch = wallpaper.name.contains(searchText, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || wallpaper.category == selectedCategory
        matchesSearch && matchesCategory
    }

    if (selectedWallpaper != null) {
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
                        wallpapers = filteredWallpapers,
                        allWallpapersCount = wallpapers.size,
                        favoriteNames = favoriteNames,
                        searchText = searchText,
                        selectedCategory = selectedCategory,
                        categories = categories.map { it.name },
                        onSearchChange = { searchText = it },
                        onCategoryClick = { selectedCategory = it },
                        onWallpaperClick = { selectedWallpaper = it }
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
    categories: List<String>,
    onSearchChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onWallpaperClick: (Wallpaper) -> Unit
) {
    GalleryContent(
        title = "✦ Wallora",
        subtitle = "AI • 4K • AMOLED",
        favoriteInfo = "🔥 New wallpapers every week",
        wallpapers = wallpapers,
        favoriteNames = favoriteNames,
        searchText = searchText,
        selectedCategory = selectedCategory,
        categories = categories,
        showSearch = true,
        showCategories = true,
        onSearchChange = onSearchChange,
        onCategoryClick = onCategoryClick,
        onWallpaperClick = onWallpaperClick
    )
}

@Composable
fun FavoritesScreen(
    wallpapers: List<Wallpaper>,
    favoriteNames: List<String>,
    onWallpaperClick: (Wallpaper) -> Unit
) {
    if (wallpapers.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "❤️", fontSize = 56.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Brak ulubionych tapet",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Dodaj tapetę do ulubionych, a pojawi się tutaj.",
                color = Color.LightGray,
                fontSize = 16.sp
            )
        }
    } else {
        GalleryContent(
            title = "Favorites",
            subtitle = "${wallpapers.size} favorite wallpapers",
            favoriteInfo = "❤️ Saved wallpapers",
            wallpapers = wallpapers,
            favoriteNames = favoriteNames,
            searchText = "",
            selectedCategory = "All",
            categories = emptyList(),
            showSearch = false,
            showCategories = false,
            onSearchChange = {},
            onCategoryClick = {},
            onWallpaperClick = onWallpaperClick
        )
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
            text = "Settings",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "🌙 Dark mode: ON", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "🔔 Daily wallpaper: Coming soon", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "👑 Premium: Coming soon", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "💰 Ads: Coming soon", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
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
    categories: List<String>,
    showSearch: Boolean,
    showCategories: Boolean,
    onSearchChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onWallpaperClick: (Wallpaper) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = subtitle,
            color = Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = favoriteInfo,
            color = Color(0xFFFF6B81),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "🔥 Trending",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {

            TrendingChip("🔥 Popular")
            Spacer(modifier = Modifier.width(8.dp))

            TrendingChip("🆕 New")
            Spacer(modifier = Modifier.width(8.dp))

            TrendingChip("⭐ Editor's Choice")
        }
        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(26.dp)
        ) {
            Box {

                Image(
                    painter = painterResource(id = wallpapers.first().image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.85f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(18.dp)
                ) {

                    Text(
                        "⭐ Wallpaper of the Day",
                        color = Color(0xFFFFD54F),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        wallpapers.first().name,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        if (showSearch) {
            TextField(
                value = searchText,
                onValueChange = onSearchChange,
                placeholder = {
                    Text(text = "Search wallpapers...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

        if (showCategories) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 16.dp)
            ) {
                categories.forEach { category ->
                    CategoryButton(
                        name = category,
                        selected = category == selectedCategory,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }

        if (wallpapers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No wallpapers found",
                    color = Color.LightGray,
                    fontSize = 20.sp
                )
            }
        } else {

            Text(
                text = "🔥 Today's Trending",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 18.dp, bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(top = 18.dp),
            ) {
                items(wallpapers) { wallpaper ->
                    WallpaperCard(
                        wallpaper = wallpaper,
                        isFavorite = favoriteNames.contains(wallpaper.name),
                        onClick = { onWallpaperClick(wallpaper) }
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp)
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
                    text = "AI",
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = wallpaper.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                   )
            }
        }
    }
}

@Composable
fun FullScreenWallpaper(
    wallpaper: Wallpaper,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(wallpaper.image),
            contentDescription = wallpaper.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.75f))
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = wallpaper.name,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = if (isFavorite) "❤️ Dodano do ulubionych" else "🤍 Dodaj do ulubionych",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onFavoriteClick() }
            )

            Text(
                text = "⬇ Pobierz tapetę",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onDownloadClick() }
            )

            Text(
                text = "📱 Ustaw jako tapetę",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "⬅ Powrót",
                color = Color(0xFF64B5F6),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onBack() }
            )
        }
    }
}

@Composable
fun BottomMenu(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111111))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(
            text = "🏠 Home",
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") }
        )

        MenuItem(
            text = "❤️ Favorites",
            selected = selectedTab == "favorites",
            onClick = { onTabSelected("favorites") }
        )

        MenuItem(
            text = "⚙️ Settings",
            selected = selectedTab == "settings",
            onClick = { onTabSelected("settings") }
        )
    }
}

@Composable
fun MenuItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) Color(0xFF64B5F6) else Color.White,
        fontSize = if (selected) 17.sp else 15.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    )
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