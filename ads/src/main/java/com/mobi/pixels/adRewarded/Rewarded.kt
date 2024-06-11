package com.mobi.pixels.adRewarded

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mobi.pixels.adInterstitial.Interstitial

object Rewarded {

    private var rewardedAd: RewardedAd? = null
    var loadListener: AdRewardedLoadListeners? = null
    var showListener: AdRewardedShowListeners? = null
    var isPreviousAdLoading = false
    var isShowingRewardedAd = false

    fun load(ctx: Activity, id: String) = apply{
        if (rewardedAd !=null){
            loadListener?.onLoaded()
            return@apply
        }
        val callback = object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                isPreviousAdLoading = false
                rewardedAd =ad
                loadListener?.onLoaded()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                isPreviousAdLoading = false
                loadListener?.onFailedToLoad()
            } }

        if (!isPreviousAdLoading){
            isPreviousAdLoading = true

            var adRequest = AdRequest.Builder().build()

            RewardedAd.load(ctx,id, adRequest,callback)
        }
        else{
            loadListener?.onPreviousAdLoading()
        }

    }


    fun show(ctx: Activity) = apply{
        if (rewardedAd ==null) {
            showListener?.onError()
            return@apply
        }

        rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                isShowingRewardedAd = false
                showListener?.onDismissed()

            }
            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                rewardedAd = null
                isShowingRewardedAd = true
                showListener?.onShowed()

            }
        }

        rewardedAd?.show(ctx, OnUserEarnedRewardListener { rewardItem ->
            showListener?.onCompleted()
            // Handle the reward.
            val rewardAmount = rewardItem.amount
            val rewardType = rewardItem.type
            Log.d("hjjh", "User earned the reward.")
        })


    }




    fun adLoadListeners(listener: AdRewardedLoadListeners?) = apply {
        loadListener = listener
    }
    fun adShowListeners(listener: AdRewardedShowListeners?) = apply {
       showListener = listener
    }
}