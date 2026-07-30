package com.example.wallora.repository.collections

import com.example.wallora.R
import com.example.wallora.model.Wallpaper

object FoodMonstersCollection {

    val wallpapers = listOf(

        Wallpaper(
            name = "Pizza Monster",
            image = R.drawable.food_monster_01,
            category = "Funny",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Burger Monster",
            image = R.drawable.food_monster_02,
            category = "Funny",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),
        Wallpaper(
            name = "Donut Monster",
            image = R.drawable.food_monster_03,
            category = "Funny",
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        ),

    )
}