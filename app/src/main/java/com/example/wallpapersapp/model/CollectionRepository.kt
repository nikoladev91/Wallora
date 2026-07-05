package com.example.wallpapersapp.model

object CollectionRepository {

    private val neonAnimals = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Neon Heart Cat",
            "Neon Heart Cat II",
            "Neon Wolf",
            "Neon Panther",
            "Neon Tiger",
            "Neon Fox",
            "Neon Axolotl",
            "Neon Owl",
            "Neon Dog",
            "Neon Butterfly",
            "Neon Raven",
            "Neon Dragon"
        )
    }

    private val cosmicDreams = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Black Hole",
            "Purple Galaxy",
            "Cosmic Nebula",
            "Saturn",
            "Moon",
            "Blue Planet",
            "Red Planet",
            "Astronaut",
            "Cosmic Whale",
            "Space Portal",
            "Shooting Star",
            "Solar Eclipse"
        )
    }

    val collections = listOf(
        WallpaperCollection(
            id = "neon_animals_vol_1",
            title = "Neon Animals Vol.1",
            subtitle = "12 Exclusive AMOLED Wallpapers",
            coverImage = neonAnimals.first().image,
            wallpapers = neonAnimals,
            isPremium = false
        ),
        WallpaperCollection(
            id = "cosmic_dreams_vol_1",
            title = "Cosmic Dreams Vol.1",
            subtitle = "12 Exclusive Space Wallpapers",
            coverImage = cosmicDreams.first().image,
            wallpapers = cosmicDreams,
            isPremium = false
        )
    )
}