package com.example.wallpapersapp.model

data class WallpaperCollection(
    val id: String,
    val title: String,
    val subtitle: String,
    val coverImage: Int,
    val wallpapers: List<Wallpaper>,
    val isPremium: Boolean = false
)