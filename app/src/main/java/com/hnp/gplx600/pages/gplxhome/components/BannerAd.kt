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
  bannerAdUnitId: String = "ca-app-pub-2965911381053768/2031307245",
) {
  AndroidView(modifier = modifier, factory = { context ->
    AdView(context).apply {
      setAdSize(AdSize.BANNER)
      adUnitId = bannerAdUnitId
      loadAd(AdRequest.Builder().build())
    }
  })
}
