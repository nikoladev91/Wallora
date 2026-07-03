package com.example.wallpapersapp.model

data class Wallpaper(
    val name: String,
    val image: Int,
    val category: String,
    val rating: Double = 4.8,
    val downloads: String = "12K",
    val badge: String = "AI",
    val isTopPick: Boolean = false
)