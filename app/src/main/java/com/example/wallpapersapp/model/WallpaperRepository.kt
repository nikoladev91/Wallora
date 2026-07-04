package com.example.wallpapersapp.model

import com.example.wallpapersapp.R

object WallpaperRepository {

    val wallpapers = listOf(
        Wallpaper(
            name = "Neon Heart Cat",
            image = R.drawable.wallpaper_neon_cat_1,
            category = "Animals",
            rating = 4.9,
            downloads = "0",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Neon Heart Cat II",
            image = R.drawable.neon_heart_cat_v2,
            category = "Animals",
            rating = 4.8,
            downloads = "18K",
            badge = "AI",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Wolf",
            image = R.drawable.neon_wolf,
            category = "Animals",
            rating = 5.0,
            downloads = "31K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Neon Panther",
            image = R.drawable.neon_panther,
            category = "Animals",
            rating = 5.0,
            downloads = "29K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Neon Tiger",
            image = R.drawable.neon_tiger,
            category = "Animals",
            rating = 4.9,
            downloads = "27K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Fox",
            image = R.drawable.neon_fox,
            category = "Animals",
            rating = 4.9,
            downloads = "22K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Axolotl",
            image = R.drawable.neon_axolotl,
            category = "Animals",
            rating = 4.9,
            downloads = "16K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Owl",
            image = R.drawable.neon_owl,
            category = "Animals",
            rating = 4.9,
            downloads = "18K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Dog",
            image = R.drawable.neon_dog,
            category = "Animals",
            rating = 4.9,
            downloads = "24K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Butterfly",
            image = R.drawable.neon_butterfly,
            category = "Animals",
            rating = 4.9,
            downloads = "21K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Raven",
            image = R.drawable.neon_raven,
            category = "Animals",
            rating = 4.9,
            downloads = "19K",
            badge = "AMOLED",
            isTopPick = false
        ),
        Wallpaper(
            name = "Neon Dragon",
            image = R.drawable.neon_dragon,
            category = "Animals",
            rating = 5.0,
            downloads = "34K",
            badge = "AMOLED",
            isTopPick = true
        ),
        Wallpaper(
            name = "Blue Beach",
            image = R.drawable.wallpaper_2,
            category = "Nature",
            rating = 5.0,
            downloads = "1.4M",
            badge = "4K"
        ),
        Wallpaper(
            name = "Mountain",
            image = R.drawable.wallpaper_3,
            category = "Nature",
            rating = 4.8,
            downloads = "842K",
            badge = "8K"

        ),
        Wallpaper(
            name = "Galaxy",
            image = R.drawable.wallpaper_4,
            category = "Space",
            rating = 4.7,
            downloads = "512K",
            badge = "AMOLED"
        ),
        Wallpaper(
            name = "Cute Cat 2",
            image = R.drawable.wallpaper_1,
            category = "Animals",
            rating = 4.8,
            downloads = "174K",
            badge = "AI"
        ),
        Wallpaper(
            name = "Forest Dream",
            image = R.drawable.wallpaper_2,
            category = "Nature",
            rating = 4.9,
            downloads = "638K",
            badge = "4K"
        ),
        Wallpaper(
            name = "Sport Car",
            image = R.drawable.wallpaper_3,
            category = "Cars",
            rating = 4.7,
            downloads = "1.1M",
            badge = "8K"
        ),
        Wallpaper(
            name = "Gaming Neon",
            image = R.drawable.wallpaper_4,
            category = "Gaming",
            rating = 4.9,
            downloads = "812K",
            badge = "AMOLED"
        ),
        Wallpaper(
            name = "AMOLED Black",
            image = R.drawable.wallpaper_4,
            category = "AMOLED",
            rating = 5.0,
            downloads = "2.4M",
            badge = "AMOLED"
        ),
        Wallpaper(
            name = "Abstract Art",
            image = R.drawable.wallpaper_2,
            category = "Abstract",
            rating = 4.8,
            downloads = "436K",
            badge = "AI"
        ),
        Wallpaper(
            name = "Love Hearts",
            image = R.drawable.wallpaper_1,
            category = "Love",
            rating = 4.9,
            downloads = "953K",
            badge = "4K"
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
