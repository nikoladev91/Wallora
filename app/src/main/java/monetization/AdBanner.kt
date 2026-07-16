package com.example.wallora.monetization

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBanner(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)

                // Oficjalny testowy banner ID Google
                adUnitId = "ca-app-pub-5924658712397080/6875677333"

                loadAd(AdRequest.Builder().build())
            }
        }
    )
}