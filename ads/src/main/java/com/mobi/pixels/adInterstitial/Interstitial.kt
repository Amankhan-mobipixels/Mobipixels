package com.mobi.pixels.adInterstitial

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

    object Interstitial {
        var mInterstitialAd: InterstitialAd? = null
        var isPreviousAdLoading = false
        var isShowingInterstitialAd = false

        fun load(ctx: Activity, id: String,loadListener: AdInterstitialLoadListeners?) {
            if (mInterstitialAd != null) {
                loadListener?.onLoaded()
                return
            }

            val callback = object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isPreviousAdLoading = false
                    mInterstitialAd = interstitialAd
                    loadListener?.onLoaded()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isPreviousAdLoading = false
                    loadListener?.onFailedToLoad()
                }
            }
            if (!isPreviousAdLoading){
                isPreviousAdLoading = true
                val adRequest = AdRequest.Builder().build()
                InterstitialAd.load(ctx, id, adRequest,callback)
            }
            else{
                loadListener?.onPreviousAdLoading()
            }
        }


        fun show(ctx: Activity, showListener: AdInterstitialShowListeners? = null) {
            if (mInterstitialAd == null) {
                showListener?.onError()
                return
            }
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    isShowingInterstitialAd = false
                    showListener?.onDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    isShowingInterstitialAd = true
                    mInterstitialAd = null
                    showListener?.onShowed()
                }
            }

            mInterstitialAd?.show(ctx)
        }

    }


