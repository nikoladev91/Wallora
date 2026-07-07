package com.example.wallpapersapp.monetization

object AdManager {

    // Licznik otwarć tapet
    private var wallpaperOpenCount = 0

    /**
     * Wywołuj za każdym razem,
     * gdy użytkownik otworzy tapetę.
     *
     * Zwraca true, gdy można wyświetlić reklamę.
     */
    fun shouldShowInterstitial(): Boolean {

        wallpaperOpenCount++

        return if (wallpaperOpenCount >= 5) {
            wallpaperOpenCount = 0
            true
        } else {
            false
        }
    }
}