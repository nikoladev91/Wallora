package com.example.wallora.model

import com.example.wallora.R
import com.example.wallora.repository.collections.AnimalsCollection
import com.example.wallora.repository.collections.CuteAnimalsCollection
object WallpaperRepository {

    val wallpapers =
        AnimalsCollection.wallpapers +
                CuteAnimalsCollection.wallpapers +
                listOf(

        Wallpaper(
            name = "Black Hole",
            image = R.drawable.cosmic_black_hole,
            category = "Space",
            rating = 5.0,
            downloads = "42K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Purple Galaxy",
            image = R.drawable.cosmic_purple_galaxy,
            category = "Space",
            rating = 4.9,
            downloads = "38K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Cosmic Nebula",
            image = R.drawable.cosmic_nebula,
            category = "Space",
            rating = 4.9,
            downloads = "35K",
            badge = "AMOLED"
        ),
        Wallpaper(
            name = "Saturn",
            image = R.drawable.cosmic_saturn,
            category = "Space",
            rating = 4.9,
            downloads = "31K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Moon",
            image = R.drawable.cosmic_moon,
            category = "Space",
            rating = 4.8,
            downloads = "29K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Blue Planet",
            image = R.drawable.cosmic_blue_planet,
            category = "Space",
            rating = 4.9,
            downloads = "33K",
            badge = "AMOLED"
        ),
        Wallpaper(
            name = "Red Planet",
            image = R.drawable.cosmic_red_planet,
            category = "Space",
            rating = 4.8,
            downloads = "27K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Astronaut",
            image = R.drawable.cosmic_astronaut,
            category = "Space",
            rating = 5.0,
            downloads = "45K",
            badge = "TOP"
        ),
        Wallpaper(
            name = "Cosmic Whale",
            image = R.drawable.cosmic_whale,
            category = "Space",
            rating = 5.0,
            downloads = "41K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Space Portal",
            image = R.drawable.cosmic_space_portal,
            category = "Space",
            rating = 4.9,
            downloads = "36K",
            badge = "AI"
        ),
        Wallpaper(
            name = "Shooting Star",
            image = R.drawable.cosmic_shooting_star,
            category = "Space",
            rating = 4.8,
            downloads = "25K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Solar Eclipse",
            image = R.drawable.cosmic_solar_eclipse,
            category = "Space",
            rating = 5.0,
            downloads = "44K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Bora Bora Sunset",
            image = R.drawable.tropical_bora_bora,
            category = "Nature",
            rating = 5.0,
            downloads = "52K",
            badge = "4K",
            isTopPick = true
        ),
        Wallpaper(
            name = "Maldives Overwater Villas",
            image = R.drawable.tropical_maldives_villas,
            category = "Nature",
            rating = 5.0,
            downloads = "49K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Palm Beach Paradise",
            image = R.drawable.tropical_palm_beach,
            category = "Nature",
            rating = 4.9,
            downloads = "44K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Tropical Beach Escape",
            image = R.drawable.tropical_beach_escape,
            category = "Nature",
            rating = 5.0,
            downloads = "47K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Emerald Jungle Waterfall",
            image = R.drawable.tropical_jungle_waterfall,
            category = "Nature",
            rating = 5.0,
            downloads = "53K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Sunset Ocean Pavilion",
            image = R.drawable.tropical_ocean_pavilion,
            category = "Nature",
            rating = 4.9,
            downloads = "42K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Coral Reef World",
            image = R.drawable.tropical_coral_reef,
            category = "Nature",
            rating = 5.0,
            downloads = "48K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Hibiscus Bloom",
            image = R.drawable.tropical_hibiscus,
            category = "Nature",
            rating = 4.9,
            downloads = "39K",
            badge = "4K"
        ),
        Wallpaper(
            name = "White Sand Shell",
            image = R.drawable.tropical_white_shell,
            category = "Nature",
            rating = 4.9,
            downloads = "36K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Moonlit Beach",
            image = R.drawable.tropical_moon_beach,
            category = "Nature",
            rating = 5.0,
            downloads = "58K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Sea Turtle Journey",
            image = R.drawable.tropical_sea_turtle,
            category = "Nature",
            rating = 5.0,
            downloads = "46K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Paradise Island",
            image = R.drawable.tropical_paradise_island,
            category = "Nature",
            rating = 5.0,
            downloads = "61K",
            badge = "TOP",
            isTopPick = true
        ),        Wallpaper(
            name = "Ferrari Night Drive",
            image = R.drawable.hypercar_ferrari_night,
            category = "Cars",
            rating = 5.0,
            downloads = "37K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Lamborghini Rain Street",
            image = R.drawable.hypercar_lamborghini_rain,
            category = "Cars",
            rating = 5.0,
            downloads = "34K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Porsche GT3 Mountain Road",
            image = R.drawable.hypercar_porsche_mountain,
            category = "Cars",
            rating = 4.9,
            downloads = "28K",
            badge = "4K"
        ),
        Wallpaper(
            name = "McLaren Neon Garage",
            image = R.drawable.hypercar_mclaren_garage,
            category = "Cars",
            rating = 4.9,
            downloads = "32K",
            badge = "AI"
        ),
        Wallpaper(
            name = "Bugatti City Lights",
            image = R.drawable.hypercar_bugatti_city,
            category = "Cars",
            rating = 5.0,
            downloads = "39K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Koenigsegg Desert Sunset",
            image = R.drawable.hypercar_koenigsegg_sunset,
            category = "Cars",
            rating = 5.0,
            downloads = "30K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Coastal Hypercar Sunset",
            image = R.drawable.hypercar_coastal_sunset,
            category = "Cars",
            rating = 4.9,
            downloads = "24K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Pagani Gold Reflections",
            image = R.drawable.hypercar_pagani_gold,
            category = "Cars",
            rating = 5.0,
            downloads = "18K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Cyberpunk Hypercar",
            image = R.drawable.hypercar_cyberpunk,
            category = "Cars",
            rating = 5.0,
            downloads = "31K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Tunnel Velocity",
            image = R.drawable.hypercar_tunnel,
            category = "Cars",
            rating = 4.9,
            downloads = "22K",
            badge = "NEW"
        ),
        Wallpaper(
            name = "Midnight Carbon",
            image = R.drawable.hypercar_midnight_carbon,
            category = "Cars",
            rating = 5.0,
            downloads = "26K",
            badge = "PREMIUM",
            isTopPick = true
        ),
        Wallpaper(
            name = "Classic Legend",
            image = R.drawable.hypercar_classic_legend,
            category = "Cars",
            rating = 5.0,
            downloads = "42K",
            badge = "LEGEND",
            isTopPick = true
        ),
        Wallpaper(
            name = "Alpine Sunrise",
            image = R.drawable.mountain_alpine_sunrise,
            category = "Nature",
            rating = 5.0,
            downloads = "33K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Misty Peaks",
            image = R.drawable.mountain_misty_peaks,
            category = "Nature",
            rating = 4.9,
            downloads = "21K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Frozen Summit",
            image = R.drawable.mountain_frozen_summit,
            category = "Nature",
            rating = 5.0,
            downloads = "29K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Emerald Valley",
            image = R.drawable.mountain_emerald_valley,
            category = "Nature",
            rating = 4.9,
            downloads = "24K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Golden Ridge",
            image = R.drawable.mountain_golden_ridge,
            category = "Nature",
            rating = 5.0,
            downloads = "31K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Moonlight Mountains",
            image = R.drawable.mountain_moonlight,
            category = "Nature",
            rating = 4.9,
            downloads = "26K",
            badge = "NIGHT"
        ),
        Wallpaper(
            name = "Crystal Lake",
            image = R.drawable.mountain_crystal_lake,
            category = "Nature",
            rating = 5.0,
            downloads = "35K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Storm Above Peaks",
            image = R.drawable.mountain_storm_peaks,
            category = "Nature",
            rating = 4.8,
            downloads = "19K",
            badge = "DRAMA"
        ),
        Wallpaper(
            name = "Autumn Highlands",
            image = R.drawable.mountain_autumn,
            category = "Nature",
            rating = 4.9,
            downloads = "23K",
            badge = "NEW"
        ),
        Wallpaper(
            name = "Waterfall Canyon",
            image = R.drawable.mountain_waterfall,
            category = "Nature",
            rating = 5.0,
            downloads = "28K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Endless Horizon",
            image = R.drawable.mountain_horizon,
            category = "Nature",
            rating = 4.9,
            downloads = "25K",
            badge = "PREMIUM"
        ),
        Wallpaper(
            name = "Aurora Summit",
            image = R.drawable.mountain_aurora,
            category = "Nature",
            rating = 5.0,
            downloads = "41K",
            badge = "TOP",
            isTopPick = true
        ),
        Wallpaper(
            name = "Neo Tokyo",
            image = R.drawable.cyberpunk_neo_tokyo_001,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Neon Alley",
            image = R.drawable.cyberpunk_neon_alley_002,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Skyline 2099",
            image = R.drawable.cyberpunk_skyline_2099_003,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Rain District",
            image = R.drawable.cyberpunk_rain_district_004,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Neon Harbor",
            image = R.drawable.cyberpunk_neon_harbor_005,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Moon Overlook",
            image = R.drawable.cyberpunk_moon_overlook_006,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Sunset Bridge",
            image = R.drawable.cyberpunk_sunset_bridge_007,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Sky Lounge",
            image = R.drawable.cyberpunk_sky_lounge_008,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),Wallpaper(
            name = "Infinity Pool",
            image = R.drawable.cyberpunk_infinity_pool_009,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW"
        ),
        Wallpaper(
            name = "Penthouse View",
            image = R.drawable.cyberpunk_penthouse_view_010,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW"
        ),
        Wallpaper(
            name = "Night Rider",
            image = R.drawable.cyberpunk_night_rider_011,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW"
        ),Wallpaper(
            name = "Cosmic City",
            image = R.drawable.cyberpunk_cosmic_city_012,
            category = "Cyberpunk Cities",
            rating = 5.0,
            downloads = "New",
            badge = "NEW"
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
        return wallpapers.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getWallpaperOfTheDay(): Wallpaper {
        return wallpapers.firstOrNull { it.isTopPick } ?: wallpapers.first()
    }
    fun getFilteredWallpapers(
        searchQuery: String,
        category: String
    ): List<Wallpaper> {
        return wallpapers.filter { wallpaper ->

            val matchesSearch =
                wallpaper.name.contains(searchQuery, ignoreCase = true)

            val matchesCategory =
                category == "All" || wallpaper.category == category

            matchesSearch && matchesCategory
        }
    }
    }

