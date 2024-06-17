package com.hnp.gplx600.pages.gplxhome.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ImageFromUrl(imageUrl: String) {
  val painter = // You can customize image loading options here if needed
    rememberAsyncImagePainter(
      ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
        .apply(block = fun ImageRequest.Builder.() {
          // You can customize image loading options here if needed
        }).build()
    )

  Image(
    painter = painter,
    contentDescription = null, // You can provide a content description here if needed
    modifier = Modifier.fillMaxSize(),
    // You can specify other parameters for the Image composable here
  )
}