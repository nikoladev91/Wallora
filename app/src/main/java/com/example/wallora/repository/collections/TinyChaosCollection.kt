package com.example.wallora.repository.collections

import com.example.wallora.R
import com.example.wallora.model.Wallpaper

object TinyChaosCollection {

    val wallpapers = listOf(

        Wallpaper(
            name = "Rocket Shark",
            image = R.drawable.tiny_chaos_01,
            category = "Animals",
            tags = listOf(
                "shark",
                "skateboard",
                "neon",
                "city",
                "funny",
                "chaos",
                "kids",
                "cool",
                "street",
                "cyberpunk"
            ),
            rating = 5.0,
            downloads = "New",
            badge = "NEW",
            isTopPick = true
        )

    )
}