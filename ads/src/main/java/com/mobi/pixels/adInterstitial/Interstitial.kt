package com.mobi.pixels.adInterstitial

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mobi.pixels.initialize.Ads
import com.mobi.pixels.isOnline

object Interstitial {
        private var mInterstitialAd: InterstitialAd? = null
        private var isPreviousAdLoading = false
        internal var isShowingInterstitialAd = false

        fun load(ctx: Activity, id: String,loadListener: AdInterstitialLoadListeners? = null) {
            if (mInterstitialAd != null) {
                loadListener?.onLoaded()
                return
            }

            if (!isOnline(ctx)) {
                loadListener?.onFailedToLoad("Internet connection not detected. Please check your connection and try again.")
                return
            }
            if (!Ads.IsAdsAllowed) {
                loadListener?.onFailedToLoad("Ads disabled by developer")
                return
            }

            val callback = object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isPreviousAdLoading = false
                    mInterstitialAd = interstitialAd
                    loadListener?.onLoaded()
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    isPreviousAdLoading = false
                    loadListener?.onFailedToLoad(adError.message)
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
            if (mInterstitialAd == null ||  isShowingInterstitialAd) {
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


