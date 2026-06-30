package com.example.wallpapersapp.storage

import android.content.Context

object FavoritesStorage {

    private const val PREFS_NAME = "wallpapers_prefs"
    private const val FAVORITES_KEY = "favorites"

    fun saveFavorites(context: Context, favorites: Set<String>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply()
    }

    fun loadFavorites(context: Context): MutableSet<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(FAVORITES_KEY, emptySet())?.toMutableSet()
            ?: mutableSetOf()
    }
}