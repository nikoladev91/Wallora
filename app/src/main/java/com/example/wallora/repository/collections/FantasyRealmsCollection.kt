package com.example.wallora.repository.collections

import com.example.wallora.R
import com.example.wallora.model.Wallpaper

object FantasyRealmsCollection {

    val wallpapers = listOf(

        Wallpaper(
            name = "Moonlit Castle",
            image = R.drawable.fantasy_castle_01,
            category = "Fantasy",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
                name = "Moonlit Kingdom",
        image = R.drawable.fantasy_castle_02,
        category = "Fantasy",
        rating = 5.0,
        downloads = "New",
        badge = "NEW",
        isTopPick = true
    ),
        Wallpaper(
            name = "Crystal Castle",
            image = R.drawable.fantasy_castle_03,
            category = "Fantasy",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Elven Sanctuary",
            image = R.drawable.fantasy_castle_04,
            category = "Fantasy",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
    )
}