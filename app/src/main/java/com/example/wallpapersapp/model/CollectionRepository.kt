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
    private val hypercars = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Ferrari Night Drive",
            "Lamborghini Rain Street",
            "Porsche GT3 Mountain Road",
            "McLaren Neon Garage",
            "Bugatti City Lights",
            "Koenigsegg Desert Sunset",
            "Coastal Hypercar Sunset",
            "Pagani Gold Reflections",
            "Cyberpunk Hypercar",
            "Tunnel Velocity",
            "Midnight Carbon",
            "Classic Legend"
        )
    }
    private val epicMountains = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Alpine Sunrise",
            "Misty Peaks",
            "Frozen Summit",
            "Emerald Valley",
            "Golden Ridge",
            "Moonlight Mountains",
            "Crystal Lake",
            "Storm Above Peaks",
            "Autumn Highlands",
            "Waterfall Canyon",
            "Endless Horizon",
            "Aurora Summit"
        )
    }

    private val cyberpunkCities = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Neo Tokyo",
            "Neon Alley",
            "Skyline 2099",
            "Rain District",
            "Neon Harbor",
            "Moon Overlook",
            "Sunset Bridge",
            "Sky Lounge",
            "Infinity Pool",
            "Penthouse View",
            "Night Rider",
            "Cosmic City"
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
        ),
        WallpaperCollection(
            id = "hypercars_vol_1",
            title = "Hypercars Vol.1",
            subtitle = "12 Exclusive Hypercar Wallpapers",
            coverImage = hypercars.first().image,
            wallpapers = hypercars,
            isPremium = false
        ),
        WallpaperCollection(
            id = "epic_mountains_vol_1",
            title = "Epic Mountains Vol.1",
            subtitle = "12 Exclusive Mountain Wallpapers",
            coverImage = epicMountains.first().image,
            wallpapers = epicMountains,
            isPremium = false
        ),
        WallpaperCollection(
            id = "cyberpunk_cities_vol_1",
            title = "Cyberpunk Cities Vol.1",
            subtitle = "1 Premium Cyberpunk Wallpaper",
            coverImage = cyberpunkCities.first().image,
            wallpapers = cyberpunkCities,
            isPremium = false
        )
    )
}