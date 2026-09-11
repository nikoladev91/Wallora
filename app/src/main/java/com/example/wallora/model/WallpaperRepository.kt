package com.example.wallora.model

import com.example.wallora.R
import com.example.wallora.repository.collections.AnimalsCollection
import com.example.wallora.repository.collections.CuteAnimalsCollection
import com.example.wallora.repository.collections.PolishWondersCollection
import com.example.wallora.repository.collections.FantasyRealmsCollection
import com.example.wallora.repository.collections.ChibiDragonsCollection
import com.example.wallora.repository.collections.FoodMonstersCollection
import com.example.wallora.repository.collections.FunnyDinosaursCollection
import com.example.wallora.repository.collections.TinyChaosCollection
import com.example.wallora.repository.collections.AutumnCozyCollection
import com.example.wallora.repository.collections.EnchantedForestCollection
import com.example.wallora.repository.collections.UnicornOverloadCollection
object WallpaperRepository {

    val wallpapers =
        AnimalsCollection.wallpapers +
                CuteAnimalsCollection.wallpapers +
                PolishWondersCollection.wallpapers +
                FantasyRealmsCollection.wallpapers +
                ChibiDragonsCollection.wallpapers +
                FoodMonstersCollection.wallpapers +
                FunnyDinosaursCollection.wallpapers +
                TinyChaosCollection.wallpapers +
                AutumnCozyCollection.wallpapers +
                EnchantedForestCollection.wallpapers +
                UnicornOverloadCollection.wallpapers +
                listOf(

                    Wallpaper(
                        name = "Black Hole",
                        image = R.drawable.cosmic_black_hole,
                        category = "Space",

                        tags = listOf(

                            "black hole", "space", "cosmic", "universe",

                            "galaxy", "dark", "stars", "amoled"

                        ),
                        rating = 5.0,
                        downloads = "42K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Purple Galaxy",
                        image = R.drawable.cosmic_purple_galaxy,
                        category = "Space",

                        tags = listOf(

                            "galaxy", "space", "cosmic", "universe",

                            "stars", "purple", "nebula", "amoled"

                        ),
                        rating = 4.9,
                        downloads = "38K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Cosmic Nebula",
                        image = R.drawable.cosmic_nebula,
                        category = "Space",

                        tags = listOf(

                            "nebula", "space", "cosmic", "galaxy",

                            "stars", "universe", "colorful", "amoled"

                        ),
                        rating = 4.9,
                        downloads = "35K",
                        badge = "AMOLED"
                    ),
                    Wallpaper(
                        name = "Saturn",
                        image = R.drawable.cosmic_saturn,
                        category = "Space",

                        tags = listOf(

                            "saturn", "planet", "space", "cosmic",

                            "rings", "solar system", "universe"

                        ),
                        rating = 4.9,
                        downloads = "31K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Moon",
                        image = R.drawable.cosmic_moon,
                        category = "Space",

                        tags = listOf(

                            "moon", "lunar", "space", "night",

                            "cosmic", "satellite", "stars"

                        ),
                        rating = 4.8,
                        downloads = "29K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Blue Planet",
                        image = R.drawable.cosmic_blue_planet,
                        category = "Space",

                        tags = listOf(

                            "planet", "blue", "space", "cosmic",

                            "earthlike", "universe", "stars"

                        ),
                        rating = 4.9,
                        downloads = "33K",
                        badge = "AMOLED"
                    ),
                    Wallpaper(
                        name = "Red Planet",
                        image = R.drawable.cosmic_red_planet,
                        category = "Space",

                        tags = listOf(

                            "planet", "red", "mars", "space",

                            "cosmic", "desert", "universe"

                        ),
                        rating = 4.8,
                        downloads = "27K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Astronaut",
                        image = R.drawable.cosmic_astronaut,
                        category = "Space",

                        tags = listOf(

                            "astronaut", "space", "cosmic", "stars",

                            "universe", "spacesuit", "exploration"

                        ),
                        rating = 5.0,
                        downloads = "45K",
                        badge = "TOP"
                    ),
                    Wallpaper(
                        name = "Cosmic Whale",
                        image = R.drawable.cosmic_whale,
                        category = "Space",

                        tags = listOf(

                            "whale", "space", "cosmic", "fantasy",

                            "galaxy", "stars", "animal", "amoled"

                        ),
                        rating = 5.0,
                        downloads = "41K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Space Portal",
                        image = R.drawable.cosmic_space_portal,
                        category = "Space",

                        tags = listOf(

                            "portal", "space", "cosmic", "galaxy",

                            "fantasy", "wormhole", "universe"

                        ),
                        rating = 4.9,
                        downloads = "36K",
                        badge = "AI"
                    ),
                    Wallpaper(
                        name = "Shooting Star",
                        image = R.drawable.cosmic_shooting_star,
                        category = "Space",

                        tags = listOf(

                            "shooting star", "meteor", "space", "night",

                            "stars", "cosmic", "sky"

                        ),
                        rating = 4.8,
                        downloads = "25K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Solar Eclipse",
                        image = R.drawable.cosmic_solar_eclipse,
                        category = "Space",

                        tags = listOf(

                            "eclipse", "solar eclipse", "sun", "moon",

                            "space", "cosmic", "dark", "amoled"

                        ),
                        rating = 5.0,
                        downloads = "44K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Bora Bora Sunset",
                        image = R.drawable.tropical_bora_bora,
                        category = "Nature",

                        tags = listOf(

                            "bora bora", "tropical", "sunset", "beach",

                            "ocean", "island", "nature", "summer"

                        ),
                        rating = 5.0,
                        downloads = "52K",
                        badge = "4K",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Maldives Overwater Villas",
                        image = R.drawable.tropical_maldives_villas,
                        category = "Nature",

                        tags = listOf(

                            "maldives", "tropical", "ocean", "water",

                            "villas", "island", "beach", "travel"

                        ),
                        rating = 5.0,
                        downloads = "49K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Palm Beach Paradise",
                        image = R.drawable.tropical_palm_beach,
                        category = "Nature",

                        tags = listOf(

                            "palm", "beach", "tropical", "paradise",

                            "ocean", "summer", "nature", "island"

                        ),
                        rating = 4.9,
                        downloads = "44K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Tropical Beach Escape",
                        image = R.drawable.tropical_beach_escape,
                        category = "Nature",

                        tags = listOf(

                            "tropical", "beach", "ocean", "summer",

                            "paradise", "island", "nature", "travel"

                        ),
                        rating = 5.0,
                        downloads = "47K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Emerald Jungle Waterfall",
                        image = R.drawable.tropical_jungle_waterfall,
                        category = "Nature",

                        tags = listOf(

                            "jungle", "waterfall", "forest", "nature",

                            "green", "tropical", "river", "landscape"

                        ),
                        rating = 5.0,
                        downloads = "53K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Sunset Ocean Pavilion",
                        image = R.drawable.tropical_ocean_pavilion,
                        category = "Nature",

                        tags = listOf(

                            "sunset", "ocean", "pavilion", "tropical",

                            "sea", "beach", "travel", "nature"

                        ),
                        rating = 4.9,
                        downloads = "42K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Coral Reef World",
                        image = R.drawable.tropical_coral_reef,
                        category = "Nature",

                        tags = listOf(

                            "coral", "reef", "ocean", "sea",

                            "underwater", "fish", "nature", "tropical"

                        ),
                        rating = 5.0,
                        downloads = "48K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Hibiscus Bloom",
                        image = R.drawable.tropical_hibiscus,
                        category = "Nature",

                        tags = listOf(

                            "hibiscus", "flower", "floral", "nature",

                            "tropical", "pink", "bloom", "plant"

                        ),
                        rating = 4.9,
                        downloads = "39K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "White Sand Shell",
                        image = R.drawable.tropical_white_shell,
                        category = "Nature",

                        tags = listOf(

                            "shell", "beach", "white sand", "ocean",

                            "sea", "tropical", "summer", "nature"

                        ),
                        rating = 4.9,
                        downloads = "36K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Moonlit Beach",
                        image = R.drawable.tropical_moon_beach,
                        category = "Nature",

                        tags = listOf(

                            "beach", "moon", "moonlight", "night",

                            "ocean", "sea", "tropical", "amoled"

                        ),
                        rating = 5.0,
                        downloads = "58K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Sea Turtle Journey",
                        image = R.drawable.tropical_sea_turtle,
                        category = "Nature",

                        tags = listOf(

                            "turtle", "sea turtle", "animal", "ocean",

                            "sea", "underwater", "nature", "tropical"

                        ),
                        rating = 5.0,
                        downloads = "46K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Paradise Island",
                        image = R.drawable.tropical_paradise_island,
                        category = "Nature",

                        tags = listOf(

                            "island", "paradise", "tropical", "beach",

                            "ocean", "summer", "nature", "travel"

                        ),
                        rating = 5.0,
                        downloads = "61K",
                        badge = "TOP",
                        isTopPick = true
                    ),        Wallpaper(
                        name = "Ferrari Night Drive",
                        image = R.drawable.hypercar_ferrari_night,
                        category = "Cars",

                        tags = listOf(

                            "ferrari", "car", "cars", "supercar",

                            "sports car", "night", "drive", "luxury"

                        ),
                        rating = 5.0,
                        downloads = "37K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Lamborghini Rain Street",
                        image = R.drawable.hypercar_lamborghini_rain,
                        category = "Cars",

                        tags = listOf(

                            "lamborghini", "car", "cars", "supercar",

                            "sports car", "rain", "street", "luxury"

                        ),
                        rating = 5.0,
                        downloads = "34K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Porsche GT3 Mountain Road",
                        image = R.drawable.hypercar_porsche_mountain,
                        category = "Cars",

                        tags = listOf(

                            "porsche", "gt3", "car", "cars",

                            "sports car", "mountain", "road", "luxury"

                        ),
                        rating = 4.9,
                        downloads = "28K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "McLaren Neon Garage",
                        image = R.drawable.hypercar_mclaren_garage,
                        category = "Cars",

                        tags = listOf(

                            "mclaren", "car", "cars", "supercar",

                            "neon", "garage", "sports car", "luxury"

                        ),
                        rating = 4.9,
                        downloads = "32K",
                        badge = "AI"
                    ),
                    Wallpaper(
                        name = "Bugatti City Lights",
                        image = R.drawable.hypercar_bugatti_city,
                        category = "Cars",

                        tags = listOf(

                            "bugatti", "car", "cars", "supercar",

                            "city", "night", "lights", "luxury"

                        ),
                        rating = 5.0,
                        downloads = "39K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Koenigsegg Desert Sunset",
                        image = R.drawable.hypercar_koenigsegg_sunset,
                        category = "Cars",

                        tags = listOf(

                            "koenigsegg", "car", "cars", "supercar",

                            "desert", "sunset", "luxury", "sports car"

                        ),
                        rating = 5.0,
                        downloads = "30K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Coastal Hypercar Sunset",
                        image = R.drawable.hypercar_coastal_sunset,
                        category = "Cars",

                        tags = listOf(

                            "hypercar", "car", "cars", "coast",

                            "sunset", "ocean", "sports car", "luxury"

                        ),
                        rating = 4.9,
                        downloads = "24K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Pagani Gold Reflections",
                        image = R.drawable.hypercar_pagani_gold,
                        category = "Cars",

                        tags = listOf(

                            "pagani", "car", "cars", "supercar",

                            "gold", "luxury", "sports car", "reflection"

                        ),
                        rating = 5.0,
                        downloads = "18K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Cyberpunk Hypercar",
                        image = R.drawable.hypercar_cyberpunk,
                        category = "Cars",

                        tags = listOf(

                            "hypercar", "car", "cars", "cyberpunk",

                            "neon", "future", "sports car", "night"

                        ),
                        rating = 5.0,
                        downloads = "31K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Tunnel Velocity",
                        image = R.drawable.hypercar_tunnel,
                        category = "Cars",

                        tags = listOf(

                            "car", "cars", "tunnel", "speed",

                            "velocity", "sports car", "night", "road"

                        ),
                        rating = 4.9,
                        downloads = "22K",
                        badge = "NEW"
                    ),
                    Wallpaper(
                        name = "Midnight Carbon",
                        image = R.drawable.hypercar_midnight_carbon,
                        category = "Cars",

                        tags = listOf(

                            "car", "cars", "carbon", "midnight",

                            "black", "luxury", "sports car", "dark"

                        ),
                        rating = 5.0,
                        downloads = "26K",
                        badge = "PREMIUM",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Classic Legend",
                        image = R.drawable.hypercar_classic_legend,
                        category = "Cars",

                        tags = listOf(

                            "car", "cars", "classic", "vintage",

                            "legend", "retro", "automotive", "luxury"

                        ),
                        rating = 5.0,
                        downloads = "42K",
                        badge = "LEGEND",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Alpine Sunrise",
                        image = R.drawable.mountain_alpine_sunrise,
                        category = "Nature",

                        tags = listOf(

                            "mountain", "mountains", "alpine", "sunrise",

                            "nature", "landscape", "hiking", "outdoor"

                        ),
                        rating = 5.0,
                        downloads = "33K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Misty Peaks",
                        image = R.drawable.mountain_misty_peaks,
                        category = "Nature",

                        tags = listOf(

                            "mountain", "mountains", "peaks", "mist",

                            "fog", "nature", "landscape", "outdoor"

                        ),
                        rating = 4.9,
                        downloads = "21K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Frozen Summit",
                        image = R.drawable.mountain_frozen_summit,
                        category = "Nature",

                        tags = listOf(

                            "mountain", "mountains", "summit", "snow",

                            "ice", "winter", "nature", "landscape"

                        ),
                        rating = 5.0,
                        downloads = "29K",
                        badge = "AMOLED",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Emerald Valley",
                        image = R.drawable.mountain_emerald_valley,
                        category = "Nature",

                        tags = listOf(

                            "valley", "mountain", "mountains", "green",

                            "nature", "landscape", "forest", "outdoor"

                        ),
                        rating = 4.9,
                        downloads = "24K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Golden Ridge",
                        image = R.drawable.mountain_golden_ridge,
                        category = "Nature",

                        tags = listOf(

                            "ridge", "mountain", "mountains", "golden",

                            "sunset", "nature", "landscape", "outdoor"

                        ),
                        rating = 5.0,
                        downloads = "31K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Moonlight Mountains",
                        image = R.drawable.mountain_moonlight,
                        category = "Nature",

                        tags = listOf(

                            "mountain", "mountains", "moon", "moonlight",

                            "night", "nature", "landscape", "dark"

                        ),
                        rating = 4.9,
                        downloads = "26K",
                        badge = "NIGHT"
                    ),
                    Wallpaper(
                        name = "Crystal Lake",
                        image = R.drawable.mountain_crystal_lake,
                        category = "Nature",

                        tags = listOf(

                            "lake", "mountain", "mountains", "water",

                            "nature", "landscape", "reflection", "outdoor"

                        ),
                        rating = 5.0,
                        downloads = "35K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Storm Above Peaks",
                        image = R.drawable.mountain_storm_peaks,
                        category = "Nature",

                        tags = listOf(

                            "storm", "mountain", "mountains", "peaks",

                            "clouds", "dramatic", "nature", "landscape"

                        ),
                        rating = 4.8,
                        downloads = "19K",
                        badge = "DRAMA"
                    ),
                    Wallpaper(
                        name = "Autumn Highlands",
                        image = R.drawable.mountain_autumn,
                        category = "Nature",

                        tags = listOf(

                            "autumn", "fall", "highlands", "mountain",

                            "mountains", "nature", "orange", "landscape"

                        ),
                        rating = 4.9,
                        downloads = "23K",
                        badge = "NEW"
                    ),
                    Wallpaper(
                        name = "Waterfall Canyon",
                        image = R.drawable.mountain_waterfall,
                        category = "Nature",

                        tags = listOf(

                            "waterfall", "canyon", "mountain", "mountains",

                            "river", "nature", "landscape", "outdoor"

                        ),
                        rating = 5.0,
                        downloads = "28K",
                        badge = "4K"
                    ),
                    Wallpaper(
                        name = "Endless Horizon",
                        image = R.drawable.mountain_horizon,
                        category = "Nature",

                        tags = listOf(

                            "horizon", "mountain", "mountains", "landscape",

                            "nature", "sky", "outdoor", "panorama"

                        ),
                        rating = 4.9,
                        downloads = "25K",
                        badge = "PREMIUM"
                    ),
                    Wallpaper(
                        name = "Aurora Summit",
                        image = R.drawable.mountain_aurora,
                        category = "Nature",

                        tags = listOf(

                            "aurora", "mountain", "mountains", "summit",

                            "night", "nature", "sky", "landscape"

                        ),
                        rating = 5.0,
                        downloads = "41K",
                        badge = "TOP",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Neo Tokyo",
                        image = R.drawable.cyberpunk_neo_tokyo_001,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "tokyo", "city", "cyberpunk", "neon",

                            "japan", "future", "night", "urban"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Neon Alley",
                        image = R.drawable.cyberpunk_neon_alley_002,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "alley", "city", "cyberpunk", "neon",

                            "street", "night", "urban", "future"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Skyline 2099",
                        image = R.drawable.cyberpunk_skyline_2099_003,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "skyline", "city", "cyberpunk", "future",

                            "neon", "urban", "night", "sci fi"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Rain District",
                        image = R.drawable.cyberpunk_rain_district_004,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "rain", "city", "cyberpunk", "district",

                            "street", "night", "urban", "neon"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Neon Harbor",
                        image = R.drawable.cyberpunk_neon_harbor_005,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "harbor", "city", "cyberpunk", "neon",

                            "water", "night", "urban", "future"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Moon Overlook",
                        image = R.drawable.cyberpunk_moon_overlook_006,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "moon", "city", "cyberpunk", "night",

                            "overlook", "skyline", "urban", "future"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Sunset Bridge",
                        image = R.drawable.cyberpunk_sunset_bridge_007,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "bridge", "city", "cyberpunk", "sunset",

                            "urban", "future", "skyline", "neon"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Sky Lounge",
                        image = R.drawable.cyberpunk_sky_lounge_008,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "lounge", "city", "cyberpunk", "sky",

                            "luxury", "night", "urban", "future"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),Wallpaper(
                        name = "Infinity Pool",
                        image = R.drawable.cyberpunk_infinity_pool_009,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "pool", "infinity pool", "city", "cyberpunk",

                            "luxury", "water", "skyline", "future"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),
                    Wallpaper(
                        name = "Penthouse View",
                        image = R.drawable.cyberpunk_penthouse_view_010,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "penthouse", "city", "cyberpunk", "luxury",

                            "view", "skyline", "urban", "night"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),
                    Wallpaper(
                        name = "Night Rider",
                        image = R.drawable.cyberpunk_night_rider_011,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "rider", "city", "cyberpunk", "night",

                            "vehicle", "street", "neon", "future"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),Wallpaper(
                        name = "Cosmic City",
                        image = R.drawable.cyberpunk_cosmic_city_012,
                        category = "Cyberpunk Cities",

                        tags = listOf(

                            "city", "cyberpunk", "cosmic", "space",

                            "future", "neon", "urban", "sci fi"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),
                    Wallpaper(
                        name = "Aries Rocks",
                        image = R.drawable.zodiac_01_aries,
                        category = "Zodiac",

                        tags = listOf(

                            "aries", "zodiac", "astrology", "ram",

                            "fire sign", "horoscope", "stars"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    Wallpaper(
                        name = "MOOvision",
                        image = R.drawable.zodiac_02_taurus,
                        category = "Zodiac",

                        tags = listOf(

                            "taurus", "zodiac", "astrology", "bull",

                            "earth sign", "horoscope", "stars"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    Wallpaper(
                        name = "Gemini Freedom",
                        image = R.drawable.zodiac_03_gemini,
                        category = "Zodiac",

                        tags = listOf(

                            "gemini", "zodiac", "astrology", "twins",

                            "air sign", "horoscope", "stars"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    Wallpaper(
                        name = "Crabby's Galaxy Family Resort",
                        image = R.drawable.zodiac_04_cancer,
                        category = "Zodiac",

                        tags = listOf(

                            "cancer", "zodiac", "astrology", "crab",

                            "water sign", "horoscope", "galaxy"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),

                    Wallpaper(
                        name = "Roarlywood",
                        image = R.drawable.zodiac_05_leo,
                        category = "Zodiac",

                        tags = listOf(

                            "leo", "zodiac", "astrology", "lion",

                            "fire sign", "horoscope", "stars"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    Wallpaper(
                        name = "Universe Quality Control",
                        image = R.drawable.zodiac_06_virgo,
                        category = "Zodiac",

                        tags = listOf(

                            "virgo", "zodiac", "astrology", "maiden",

                            "earth sign", "horoscope", "universe"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),

                    Wallpaper(
                        name = "Balance Bureau",
                        image = R.drawable.zodiac_07_libra,
                        category = "Zodiac",

                        tags = listOf(

                            "libra", "zodiac", "astrology", "scales",

                            "air sign", "horoscope", "balance"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),

                    Wallpaper(
                        name = "Scorp Syndicate",
                        image = R.drawable.zodiac_08_scorpio,
                        category = "Zodiac",

                        tags = listOf(

                            "scorpio", "zodiac", "astrology", "scorpion",

                            "water sign", "horoscope", "stars"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    Wallpaper(
                        name = "Born to Roam",
                        image = R.drawable.zodiac_09_sagittarius,
                        category = "Zodiac",

                        tags = listOf(

                            "sagittarius", "zodiac", "astrology", "archer",

                            "fire sign", "horoscope", "travel"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),

                    Wallpaper(
                        name = "Summit Enterprises",
                        image = R.drawable.zodiac_10_capricorn,
                        category = "Zodiac",

                        tags = listOf(

                            "capricorn", "zodiac", "astrology", "goat",

                            "earth sign", "horoscope", "mountain"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    Wallpaper(
                        name = "Galaxy Plumbing Co.",
                        image = R.drawable.zodiac_11_aquarius,
                        category = "Zodiac",

                        tags = listOf(

                            "aquarius", "zodiac", "astrology", "water bearer",

                            "air sign", "horoscope", "galaxy"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW"
                    ),

                    Wallpaper(
                        name = "Ocean Dreams Studio",
                        image = R.drawable.zodiac_12_pisces,
                        category = "Zodiac",

                        tags = listOf(

                            "pisces", "zodiac", "astrology", "fish",

                            "water sign", "horoscope", "ocean"

                        ),
                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),
                    Wallpaper(
                        name = "Enchanted Autumn Cabin",
                        image = R.drawable.autumn_magic_01,
                        category = "Nature",

                        tags = listOf(
                            "autumn",
                            "forest",
                            "cabin",
                            "waterfall",
                            "moon",
                            "cozy",
                            "fantasy",
                            "nature"
                        ),

                        rating = 5.0,
                        downloads = "New",
                        badge = "NEW",
                        isTopPick = true
                    ),

                    )
    fun getAllWallpapers(): List<Wallpaper> {
        return wallpapers
    }

    fun getTopPicks(): List<Wallpaper> {
        return wallpapers.filter { it.isTopPick }
    }

    fun getByCategory(category: String): List<Wallpaper> {
        return if (category == "All") {
            wallpapers
        } else {
            wallpapers.filter { it.category == category }
        }
    }

    fun searchWallpapers(query: String): List<Wallpaper> {
        val cleanQuery = query.trim()

        if (cleanQuery.isBlank()) {
            return wallpapers
        }

        return wallpapers.filter { wallpaper ->
            wallpaper.name.contains(cleanQuery, ignoreCase = true) ||
                    wallpaper.category.contains(cleanQuery, ignoreCase = true) ||
                    wallpaper.tags.any { tag ->
                        tag.contains(cleanQuery, ignoreCase = true)
                    }
        }
    }

    fun getFilteredWallpapers(
        searchQuery: String,
        category: String
    ): List<Wallpaper> {

        val cleanQuery = searchQuery.trim()

        return wallpapers.filter { wallpaper ->

            val matchesSearch =
                cleanQuery.isBlank() ||
                        wallpaper.name.contains(cleanQuery, ignoreCase = true) ||
                        wallpaper.category.contains(cleanQuery, ignoreCase = true) ||
                        wallpaper.tags.any { tag ->
                            tag.contains(cleanQuery, ignoreCase = true)
                        }

            val matchesCategory =
                category == "All" || wallpaper.category == category

            matchesSearch && matchesCategory
        }
    }    }