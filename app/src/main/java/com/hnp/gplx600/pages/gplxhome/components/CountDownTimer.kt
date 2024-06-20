package com.hnp.gplx600.pages.gplxhome.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import androidx.navigation.NavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.Components
import com.hnp.gplx600.components.MyDialog
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CountDownTimer(
  startTime: Int, navController: NavController,
  globalVar: GlobalVariable,
  ct: Context
) {

  var timeLeft by remember { mutableIntStateOf(startTime) }
  LaunchedEffect(Unit) {
    while (timeLeft > 0) {
      delay(1000L)
      timeLeft--
    }
  }


  var mInterstitialAd: InterstitialAd? = null
  fun loadInterstitialAd(context: Context) {
    InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", AdRequest.Builder().build(),
      object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
          mInterstitialAd = null
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
          mInterstitialAd = interstitialAd
        }
      }
    )
  }
  fun showInterstitialAd(context: Context, onAdDismissed: () -> Unit, onAdNull: ()-> Unit) {
    if (mInterstitialAd != null) {
      mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(e: AdError) {
          mInterstitialAd = null
        }

        override fun onAdDismissedFullScreenContent() {
          mInterstitialAd = null
          loadInterstitialAd(context)
          onAdDismissed()
        }
      }
      mInterstitialAd?.show(context as Activity)
    }
    else {
      onAdNull()
    }
  }
  loadInterstitialAd(LocalContext.current)
  val coroutineScope = rememberCoroutineScope()

  val minutes = TimeUnit.SECONDS.toMinutes(timeLeft.toLong())
  val seconds = timeLeft % 60
  if (timeLeft == 0) {

    coroutineScope.launch {
      showInterstitialAd(ct, {
        navController.navigate("examfinishscreen") {
          popUpTo("examfinishscreen") {
            inclusive = true
          }
        }
        //Toast.makeText(ct, "Interstitial Ad Shown!", Toast.LENGTH_SHORT).show()
      } , {
        navController.navigate("examfinishscreen") {
          popUpTo("examfinishscreen") {
            inclusive = true
          }
        }
      })
    }
  }

  Box(
    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(0.5f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        Text(
          color = Color.Blue,
          text = "$minutes: ${if(seconds >= 10) seconds else "0$seconds"}",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Nộp bài", fontSize = 15.sp, color = Color(0xFF048B16), fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp).clickable {
          globalVar.showDialog(
            dialogTitle = "Thông báo",
            dialogText = "Chưa hết giờ, Bạn có chắc chắn muốn nộp bài?",
            dialogCat = "",
            dlConfirm = {
              coroutineScope.launch {
                showInterstitialAd(ct, {
                  navController.navigate("examfinishscreen") {
                    popUpTo("examfinishscreen") {
                      inclusive = true
                    }
                  }
                  //Toast.makeText(ct, "Interstitial Ad Shown!", Toast.LENGTH_SHORT).show()
                }, {
                  navController.navigate("examfinishscreen") {
                    popUpTo("examfinishscreen") {
                      inclusive = true
                    }
                  }
                })
              }
            },
            dlCancel = {
              //navController.navigateUp()
            }
          )
        })

    }

  }
  MyDialog().FNDialog(globalVar = globalVar)

}
