package com.example.wallora.storage

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.wallora.model.Wallpaper

fun saveWallpaperToGallery(
    context: Context,
    wallpaper: Wallpaper
): Boolean {
    var uri: Uri? = null

    return try {
        val safeName = wallpaper.name
            .replace(" ", "_")
            .replace("/", "_")
            .replace("\\", "_")

        val values = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "Wallora_${safeName}.png"
            )

            put(
                MediaStore.Images.Media.MIME_TYPE,
                "image/png"
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "Pictures/Wallora"
                )

                put(
                    MediaStore.Images.Media.IS_PENDING,
                    1
                )
            }
        }

        val resolver = context.contentResolver

        uri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: return false

        context.resources
            .openRawResource(wallpaper.image)
            .use { input ->

                resolver
                    .openOutputStream(uri!!)
                    ?.use { output ->
                        input.copyTo(output)
                    }
                    ?: return false
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val completedValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.IS_PENDING,
                    0
                )
            }

            resolver.update(
                uri!!,
                completedValues,
                null,
                null
            )
        }

        true

    } catch (exception: Exception) {

        uri?.let {
            context.contentResolver.delete(
                it,
                null,
                null
            )
        }

        exception.printStackTrace()
        false
    }
}

fun setWallpaper(
    context: Context,
    wallpaper: Wallpaper
): Boolean {
    return try {

        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            wallpaper.image
        ) ?: return false

        val wallpaperManager =
            WallpaperManager.getInstance(context)

        wallpaperManager.setBitmap(bitmap)

        true

    } catch (exception: Exception) {
        exception.printStackTrace()
        false
    }
}