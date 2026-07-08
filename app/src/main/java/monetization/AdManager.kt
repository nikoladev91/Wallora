package com.example.wallpapersapp.monetization

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdManager {

    private var wallpaperOpenCount = 0
    private var interstitialAd: InterstitialAd? = null

    var adsEnabled = true

    private const val TEST_INTERSTITIAL_ID =
        "ca-app-pub-3940256099942544/1033173712"

    fun loadInterstitial(context: Context) {
        if (!adsEnabled) return

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            TEST_INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    fun shouldShowInterstitial(): Boolean {
        if (!adsEnabled) return false

        wallpaperOpenCount++

        return if (wallpaperOpenCount >= 5) {
            wallpaperOpenCount = 0
            true
        } else {
            false
        }
    }

    fun showInterstitialIfReady(activity: Activity) {
        if (!adsEnabled) return

        val ad = interstitialAd

        if (ad != null) {
            ad.show(activity)
            interstitialAd = null
            loadInterstitial(activity)
        } else {
            Toast.makeText(
                activity,
                "Interstitial not ready yet",
                Toast.LENGTH_SHORT
            ).show()

            loadInterstitial(activity)
        }
    }

    fun disableAds() {
        adsEnabled = false
    }

    fun enableAds() {
        adsEnabled = true
    }

    fun resetCounter() {
        wallpaperOpenCount = 0
    }
}