package com.example.wallora.model

import com.example.wallora.repository.collections.AutumnCozyCollection
import com.example.wallora.repository.collections.CuteAnimalsCollection
import com.example.wallora.repository.collections.PolishWondersCollection
import com.example.wallora.repository.collections.FantasyRealmsCollection
import com.example.wallora.repository.collections.ChibiDragonsCollection
import com.example.wallora.repository.collections.FunnyDinosaursCollection
import com.example.wallora.repository.collections.FoodMonstersCollection
import com.example.wallora.repository.collections.EnchantedForestCollection
import com.example.wallora.repository.collections.TinyChaosCollection

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

    private val zodiac = WallpaperRepository.wallpapers.filter {
        it.name in listOf(
            "Aries Rocks",
            "MOOvision",
            "Gemini Freedom",
            "Crabby's Galaxy Family Resort",
            "Roarlywood",
            "Universe Quality Control",
            "Balance Bureau",
            "Scorp Syndicate",
            "Born to Roam",
            "Summit Enterprises",
            "Galaxy Plumbing Co.",
            "Ocean Dreams Studio"
        )
    }

    val collections = listOf(

        WallpaperCollection(
            id = "tiny_chaos_vol_1",
            title = "Tiny Chaos",
            subtitle = "Wild, Funny and Chaotic Wallpapers",
            coverImage = TinyChaosCollection.wallpapers.first().image,
            wallpapers = TinyChaosCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "autumn_cozy_vol_1",
            title = "Autumn Cozy",
            subtitle = "Warm Autumn Wallpapers",
            coverImage = AutumnCozyCollection.wallpapers.first().image,
            wallpapers = AutumnCozyCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "enchanted_forest_vol_1",
            title = "Enchanted Forest",
            subtitle = "12 Magical Forest Wallpapers",
            coverImage = EnchantedForestCollection.wallpapers.first().image,
            wallpapers = EnchantedForestCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "zodiac_what_if_vol_1",
            title = "Zodiac: What If...?",
            subtitle = "12 Original Zodiac Wallpapers",
            coverImage = zodiac.first().image,
            wallpapers = zodiac,
            isPremium = false
        ),

        WallpaperCollection(
            id = "dream_animals",
            title = "Dream Animals",
            subtitle = "12 Cinematic Animal Wallpapers",
            coverImage = neonAnimals.first().image,
            wallpapers = neonAnimals,
            isPremium = false
        ),

        WallpaperCollection(
            id = "cute_animals_vol_1",
            title = "Cute Animals Vol. 1",
            subtitle = "12 Adorable Animal Wallpapers",
            coverImage = CuteAnimalsCollection.wallpapers.first().image,
            wallpapers = CuteAnimalsCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "polish_wonders_vol_1",
            title = "Polish Wonders Vol. 1",
            subtitle = "10 Magical Places in Poland",
            coverImage = PolishWondersCollection.wallpapers.first().image,
            wallpapers = PolishWondersCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "fantasy_realms_vol_1",
            title = "Fantasy Realms Vol. 1",
            subtitle = "Magical Castles and Enchanted Worlds",
            coverImage = FantasyRealmsCollection.wallpapers.first().image,
            wallpapers = FantasyRealmsCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "chibi_dragons",
            title = "Chibi Dragons",
            subtitle = "Cute Baby Dragons in Magical Worlds",
            coverImage = ChibiDragonsCollection.wallpapers.first().image,
            wallpapers = ChibiDragonsCollection.wallpapers,
            isPremium = false
        ),

        WallpaperCollection(
            id = "funny_dinosaurs_vol_1",
            title = "Funny Dinosaurs Vol. 1",
            subtitle = "10 Funny Dinosaur Adventures",
            coverImage = FunnyDinosaursCollection.wallpapers.first().image,
            wallpapers = FunnyDinosaursCollection.wallpapers,
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
            subtitle = "12 Exclusive Cyberpunk Wallpapers",
            coverImage = cyberpunkCities.first().image,
            wallpapers = cyberpunkCities,
            isPremium = false
        ),

        WallpaperCollection(
            id = "food_monsters_vol_1",
            title = "Food Monsters Vol. 1",
            subtitle = "10 Delicious Little Monsters",
            coverImage = FoodMonstersCollection.wallpapers.first().image,
            wallpapers = FoodMonstersCollection.wallpapers,
            isPremium = false
        )
    )
}