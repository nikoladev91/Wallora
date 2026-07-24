package com.example.wallora.monetization

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


object AdManager {

    private var wallpaperOpenCount = 0
    private var premiumActionCount = 0
    private var interstitialAd: InterstitialAd? = null
    private var isLoadingInterstitial = false

    var adsEnabled = true

    private const val INTERSTITIAL_INTERVAL = 6
    private const val PREMIUM_ACTION_INTERVAL = 3

    private const val  INTERSTITIAL_ID=
        "ca-app-pub-5924658712397080/4734481143"

    fun loadInterstitial(context: Context) {
        if (!adsEnabled) return
        if (interstitialAd != null) return
        if (isLoadingInterstitial) return

        isLoadingInterstitial = true

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoadingInterstitial = false
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoadingInterstitial = false
                }
            }
        )
    }
    fun shouldShowPremiumActionInterstitial(): Boolean {
        if (!adsEnabled) return false

        premiumActionCount++

        return if (premiumActionCount >= PREMIUM_ACTION_INTERVAL) {
            premiumActionCount = 0
            true
        } else {
            false
        }
    }
    fun shouldShowInterstitial(): Boolean {
        if (!adsEnabled) return false

        wallpaperOpenCount++

        return if (wallpaperOpenCount >= INTERSTITIAL_INTERVAL) {
            wallpaperOpenCount = 0
            true
        } else {
            false
        }
    }

    fun showInterstitialIfReady(activity: Activity) {
        if (!adsEnabled) return

        val ad = interstitialAd

        if (ad == null) {
            loadInterstitial(activity)
            return
        }

        ad.fullScreenContentCallback =
            object : FullScreenContentCallback() {

                override fun onAdShowedFullScreenContent() {
                    interstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    loadInterstitial(activity)
                }

                override fun onAdFailedToShowFullScreenContent(
                    adError: AdError
                ) {
                    interstitialAd = null
                    loadInterstitial(activity)
                }
            }

        ad.show(activity)
    }
    fun showInterstitialBeforePremiumAction(
        activity: Activity,
        onFinished: () -> Unit
    ) {
        if (!adsEnabled || !shouldShowPremiumActionInterstitial()) {
            onFinished()
            return
        }

        val ad = interstitialAd

        if (ad == null) {
            loadInterstitial(activity)
            onFinished()
            return
        }

        ad.fullScreenContentCallback =
            object : FullScreenContentCallback() {

                override fun onAdShowedFullScreenContent() {
                    interstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    loadInterstitial(activity)
                    onFinished()
                }

                override fun onAdFailedToShowFullScreenContent(
                    adError: AdError
                ) {
                    interstitialAd = null
                    loadInterstitial(activity)
                    onFinished()
                }
            }

        ad.show(activity)
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