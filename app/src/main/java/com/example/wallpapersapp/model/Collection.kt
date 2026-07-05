package com.example.wallpapersapp.model

data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val coverResId: Int,
    val wallpapers: List<Wallpaper>,
    val isPremium: Boolean = false
)