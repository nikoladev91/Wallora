package com.example.wallora.analytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsManager {

    private val crashlytics: FirebaseCrashlytics
        get() = FirebaseCrashlytics.getInstance()

    fun setUserId(userId: String) {
        crashlytics.setUserId(userId)
    }

    fun wallpaperOpened(name: String) {
        crashlytics.log("Wallpaper opened: $name")
    }

    fun wallpaperDownloaded(name: String) {
        crashlytics.log("Wallpaper downloaded: $name")
    }

    fun wallpaperSet(name: String) {
        crashlytics.log("Wallpaper set: $name")
    }

    fun favoriteAdded(name: String) {
        crashlytics.log("Favorite added: $name")
    }

    fun collectionOpened(title: String) {
        crashlytics.log("Collection opened: $title")
    }

    fun searchUsed(term: String) {
        crashlytics.log("Search used: $term")
    }

    fun recordException(exception: Throwable) {
        crashlytics.recordException(exception)
    }
}