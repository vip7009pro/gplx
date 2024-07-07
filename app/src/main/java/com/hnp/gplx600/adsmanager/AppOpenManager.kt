package com.hnp.gplx600.adsmanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.hnp.gplx600.MyApplication
import com.hnp.gplx600.api.openAppID1
import java.util.Date

class AppOpenManager(private val myApplication: MyApplication) : Application.ActivityLifecycleCallbacks {

  private var appOpenAd: AppOpenAd? = null
  private var isShowingAd = false
  private var loadTime: Long = 0
  private var isFirstLaunch = true

  init {
    myApplication.registerActivityLifecycleCallbacks(this)
  }

  fun fetchAd() {
    if (isAdAvailable()) {
      return
    }

    val request = AdRequest.Builder().build()
    AppOpenAd.load(
      myApplication, openAppID1, request,
      AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
      object : AppOpenAd.AppOpenAdLoadCallback() {
        override fun onAdLoaded(ad: AppOpenAd) {
          appOpenAd = ad
          loadTime = Date().time
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
          // Handle the error
        }
      }
    )
  }

  private fun showAdIfAvailable(activity: Activity) {
    if (!isShowingAd && isAdAvailable()) {
      isShowingAd = true
      appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
          isShowingAd = false
          appOpenAd = null
          fetchAd()
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
          isShowingAd = false
        }

        override fun onAdShowedFullScreenContent() {
          isShowingAd = true
        }
      }
      appOpenAd?.show(activity)
    } else {
      fetchAd()
    }
  }

  private fun isAdAvailable(): Boolean {
    return appOpenAd != null /*&& wasLoadTimeLessThanNHoursAgo(1)*/
  }

  private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
    val dateDifference = Date().time - loadTime
    val numMilliSecondsPerHour = 3600000
    return dateDifference < numHours * numMilliSecondsPerHour
  }

  override fun onActivityResumed(activity: Activity) {
    if (isFirstLaunch) {
      showAdIfAvailable(activity)
      isFirstLaunch = true
    }
  }

  // Implement other lifecycle callbacks if needed
  override fun onActivityPaused(activity: Activity) {}
  override fun onActivityStarted(activity: Activity) {}
  override fun onActivityDestroyed(activity: Activity) {}
  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
  override fun onActivityStopped(activity: Activity) {}
  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
}