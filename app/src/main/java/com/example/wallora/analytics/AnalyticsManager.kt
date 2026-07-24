package com.example.wallora.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsManager {

    private var analytics: FirebaseAnalytics? = null

    fun init(context: Context) {
        if (analytics == null) {
            analytics = FirebaseAnalytics.getInstance(context)
        }
    }

    fun logWallpaperOpen(name: String) {
        val bundle = Bundle().apply {
            putString("wallpaper_name", name)
        }

        analytics?.logEvent("wallpaper_open", bundle)
    }

    fun logWallpaperDownload(name: String) {
        val bundle = Bundle().apply {
            putString("wallpaper_name", name)
        }

        analytics?.logEvent("wallpaper_download", bundle)
    }

    fun logWallpaperSet(name: String) {
        val bundle = Bundle().apply {
            putString("wallpaper_name", name)
        }

        analytics?.logEvent("wallpaper_set", bundle)
    }

    fun logFavoriteAdd(name: String) {
        val bundle = Bundle().apply {
            putString("wallpaper_name", name)
        }

        analytics?.logEvent("favorite_add", bundle)
    }
    fun logCollectionOpen(name: String) {
        val bundle = Bundle().apply {
            putString("collection_name", name)
        }

        analytics?.logEvent("collection_open", bundle)
    }
    fun logSearchUsed(term: String) {
        val bundle = Bundle().apply {
            putString("search_term", term)
        }

        analytics?.logEvent("search_used", bundle)
    }
}