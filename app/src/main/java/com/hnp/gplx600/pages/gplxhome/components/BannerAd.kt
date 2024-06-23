package com.hnp.gplx600.pages.gplxhome.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAd(
  modifier: Modifier = Modifier,
  bannerAdUnitId: String = "ca-app-pub-3940256099942544/9214589741",
) {
  AndroidView(modifier = modifier, factory = { context ->
    AdView(context).apply {
      setAdSize(AdSize.BANNER)
      adUnitId = bannerAdUnitId
      loadAd(AdRequest.Builder().build())
    }
  })
}
