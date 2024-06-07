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
        var LoadListener: AdInterstitialLoadListeners? = null
        var ShowListener: AdInterstitialShowListeners? = null
        var isPreviousAdLoading = false

        fun load(ctx: Activity, id: String) = apply {
            if (mInterstitialAd != null) {
                LoadListener?.onLoaded()
                return@apply
            }

            val callback = object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isPreviousAdLoading = false
                    mInterstitialAd = interstitialAd
                    LoadListener?.onLoaded()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isPreviousAdLoading = false
                    LoadListener?.onFailedToLoad()
                }
            }
            if (!isPreviousAdLoading){
                isPreviousAdLoading = true
                val adRequest = AdRequest.Builder().build()
                InterstitialAd.load(ctx, id, adRequest,callback)
            }
            else{
                LoadListener?.onPreviousAdLoading()
            }
        }

        fun show(ctx: Activity) = apply  {
            if (mInterstitialAd == null) {
                ShowListener?.onError()
                return@apply
            }
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    ShowListener?.onDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    mInterstitialAd = null
                    ShowListener?.onShowed()

                }
            }

            mInterstitialAd?.show(ctx)
        }

        fun adLoadListeners(listener: AdInterstitialLoadListeners?) = apply {
            LoadListener = listener
        }
        fun adShowListeners(listener: AdInterstitialShowListeners?) = apply {
            ShowListener = listener
        }

    }
