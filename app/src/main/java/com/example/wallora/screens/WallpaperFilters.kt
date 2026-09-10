package com.example.wallora.screens

import com.example.wallora.model.Wallpaper
fun matchesWallpaperSearch(
    wallpaper: Wallpaper,
    query: String
): Boolean {
    val cleanQuery = query
        .trim()
        .lowercase()

    if (cleanQuery.isBlank()) {
        return true
    }

    val translatedQueries = when (cleanQuery) {

        "zwierzę",
        "zwierze",
        "zwierzęta",
        "zwierzeta",
        "animal",
        "animals" ->
            listOf("animal", "animals")

        "kot",
        "koty" ->
            listOf("cat", "cats")

        "pies",
        "psy" ->
            listOf("dog", "dogs")

        "wilk",
        "wilki" ->
            listOf("wolf", "wolves")

        "smok",
        "smoki" ->
            listOf("dragon", "dragons")

        "dinozaur",
        "dinozaury" ->
            listOf("dinosaur", "dinosaurs")

        "natura" ->
            listOf("nature")

        "las",
        "lasy" ->
            listOf("forest", "woods")

        "góra",
        "gora",
        "góry",
        "gory" ->
            listOf("mountain", "mountains")

        "wodospad",
        "wodospady" ->
            listOf("waterfall", "waterfalls")

        "morze" ->
            listOf("sea", "ocean")

        "ocean" ->
            listOf("ocean", "sea")

        "kosmos" ->
            listOf("space", "cosmic", "galaxy")

        "galaktyka",
        "galaktyki" ->
            listOf("galaxy", "galaxies")

        "samochód",
        "samochod",
        "samochody",
        "auto",
        "auta" ->
            listOf("car", "cars")

        "cyberpunk",
        "cyberpunkowe miasta" ->
            listOf("cyberpunk", "city", "cities")

        "zodiak" ->
            listOf("zodiac")

        else ->
            listOf(cleanQuery)
    }

    fun matchesText(text: String): Boolean {
        val normalizedText = text
            .trim()
            .lowercase()

        val words = normalizedText
            .split(Regex("[^a-z0-9]+"))
            .filter { it.isNotBlank() }

        return translatedQueries.any { translatedQuery ->
            normalizedText == translatedQuery ||
                    words.any { word ->
                        word == translatedQuery
                    }
        }
    }

    return matchesText(wallpaper.name) ||
            matchesText(wallpaper.category) ||
            wallpaper.tags.any { tag ->
                matchesText(tag)
            }
}

fun filterWallpapers(
    wallpapers: List<Wallpaper>,
    searchText: String,
    selectedCategory: String
): List<Wallpaper> {

    return wallpapers.filter { wallpaper ->

        val cleanCategory =
            selectedCategory.substringAfter(" ").trim()

        val matchesSearch =
            matchesWallpaperSearch(
                wallpaper = wallpaper,
                query = searchText
            )

        val matchesCategory =
            cleanCategory == "All" ||
                    wallpaper.category == cleanCategory

        matchesSearch && matchesCategory
    }
}

fun sortWallpapers(
    wallpapers: List<Wallpaper>,
    selectedTrending: String
): List<Wallpaper> {

    return when (selectedTrending) {

        "Popular" ->
            wallpapers.sortedByDescending {
                downloadsToNumberLocal(it.downloads)
            }

        "New" ->
            wallpapers.reversed()

        "Editor's Choice" ->
            wallpapers.filter {
                it.isTopPick
            }

        else ->
            wallpapers
    }
}

private fun downloadsToNumberLocal(downloads: String): Int {

    return when {

        downloads.equals(
            "New",
            ignoreCase = true
        ) -> Int.MAX_VALUE

        downloads.endsWith("K", ignoreCase = true) -> {
            downloads
                .dropLast(1)
                .replace(",", ".")
                .toDoubleOrNull()
                ?.times(1000)
                ?.toInt()
                ?: 0
        }

        downloads.endsWith("M", ignoreCase = true) -> {
            downloads
                .dropLast(1)
                .replace(",", ".")
                .toDoubleOrNull()
                ?.times(1_000_000)
                ?.toInt()
                ?: 0
        }

        else ->
            downloads
                .filter { it.isDigit() }
                .toIntOrNull()
                ?: 0
    }
}