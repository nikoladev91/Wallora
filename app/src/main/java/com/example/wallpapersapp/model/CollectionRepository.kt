package com.example.wallpapersapp.model

object CollectionRepository {

    val collections = listOf(
        WallpaperCollection(
            id = "neon_animals_vol_1",
            title = "Neon Animals Vol.1",
            subtitle = "12 Exclusive AMOLED Wallpapers",
            coverImage = WallpaperRepository.wallpapers.first().image,
            wallpapers = WallpaperRepository.wallpapers,
            isPremium = false
        )
    )
}