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
    private val tropicalParadise = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Bora Bora Sunset",
            "Maldives Overwater Villas",
            "Palm Beach Paradise",
            "Tropical Beach Escape",
            "Emerald Jungle Waterfall",
            "Sunset Ocean Pavilion",
            "Coral Reef World",
            "Hibiscus Bloom",
            "White Sand Shell",
            "Moonlit Beach",
            "Sea Turtle Journey",
            "Paradise Island"
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
        ),
        WallpaperCollection(
            id = "tropical_paradise_vol_1",
            title = "Tropical Paradise Vol.1",
            subtitle = "12 Exclusive Tropical Wallpapers",
            coverImage = tropicalParadise.first().image,
            wallpapers = tropicalParadise,
            isPremium = false
        )
    )
}