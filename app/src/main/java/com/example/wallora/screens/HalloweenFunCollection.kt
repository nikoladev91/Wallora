package com.example.wallora.repository.collections

import com.example.wallora.R
import com.example.wallora.model.Wallpaper

object HalloweenFunCollection {

    val wallpapers = listOf(

        Wallpaper(
            name = "Pumpkin Party",
            image = R.drawable.halloween_fun_01,
            category = "Halloween",
            tags = listOf(
                "halloween",
                "pumpkin",
                "ghost",
                "funny",
                "cute",
                "party",
                "dj",
                "autumn",
                "spooky",
                "cat",
                "night",
                "orange"
            ),
            rating = 5.0,
            downloads = "0",
            badge = "NEW",
            isTopPick = true
        )
    )
}