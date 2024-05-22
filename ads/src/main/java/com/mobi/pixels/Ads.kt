package com.mobi.pixels

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

var mInterstitialAd:InterstitialAd? = null

    fun loadInterstitialAd(ctx: Activity, id: String, callback: ((loaded:Boolean,failed:Boolean) -> Unit)? = null) {

    if (mInterstitialAd !=null){
        callback?.invoke(true, false)
        return
    }
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(ctx, id, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            mInterstitialAd = interstitialAd
            callback?.invoke(true, false)
        }
        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            callback?.invoke(false, true)
        }
    })
}
    fun showInterstitialAd(ctx: Activity, callback: ((showed:Boolean,dismissed:Boolean,error:Boolean) -> Unit)? = null) {
        if (mInterstitialAd ==null) {
            callback?.invoke(false, false, true)
            return
        }
        mInterstitialAd?.show(ctx)
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                callback?.invoke(false, true, false)
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                callback?.invoke(true, false, false)
                mInterstitialAd = null
            }
        }
    }
    fun loadShowInterstitialAd(ctx: Activity, id: String, callback: ((loaded:Boolean,failed:Boolean,showed:Boolean,dismissed:Boolean) -> Unit)? = null) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(ctx, id, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                callback?.invoke(true, false, false, false)
                interstitialAd.show(ctx)

                interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        callback?.invoke(false, false, false, true)
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdShowedFullScreenContent() {
                        callback?.invoke(false, false, true, false)
                        super.onAdShowedFullScreenContent()
                    }
                }
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                callback?.invoke(false, true, false, false)
            }
        })
    }

     private var rewardedAd: RewardedAd? = null
     fun loadRewardedAd(ctx: Activity,id:String,callback: ((loaded:Boolean,failed:Boolean) -> Unit)? = null){
         if (rewardedAd !=null){
             callback?.invoke(true, false)
             return
         }

       var adRequest = AdRequest.Builder().build()
       RewardedAd.load(ctx,id, adRequest, object : RewardedAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            callback?.invoke(false, true)
        }
        override fun onAdLoaded(ad: RewardedAd) {
            rewardedAd =ad
            callback?.invoke(true, false)

        }
    })
}
     fun showRewardedAd(ctx: Activity,callback: ((showed: Boolean,completed:Boolean,dismissed:Boolean,error:Boolean) -> Unit)? = null){
         if (rewardedAd ==null) {
             callback?.invoke(false, false, false, true)
             return
         }
         rewardedAd?.show(ctx, OnUserEarnedRewardListener { rewardItem ->
             callback?.invoke(false, true, false, false)
             // Handle the reward.
             val rewardAmount = rewardItem.amount
             val rewardType = rewardItem.type
             Log.d("hjjh", "User earned the reward.")
         })

         rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
             override fun onAdShowedFullScreenContent() {
                 super.onAdShowedFullScreenContent()
                     rewardedAd = null
                 callback?.invoke(true, false, false, false)
             }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    callback?.invoke(false, false, true, false)
                }

        }
}
     fun loadShowRewardedAd(ctx: Activity,id:String,callback: ((loaded:Boolean,failed:Boolean,showed: Boolean,completed:Boolean,dismissed:Boolean) -> Unit)? = null){
    var adRequest = AdRequest.Builder().build()
    RewardedAd.load(ctx,id, adRequest, object : RewardedAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            callback?.invoke(false, true, false, false, false)
        }
        override fun onAdLoaded(ad: RewardedAd) {
            callback?.invoke(true, false, false, false, false)
            ad.show(ctx, OnUserEarnedRewardListener { rewardItem ->
                callback?.invoke(false, false, false, true, false)
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Log.d("hjjh", "User earned the reward.")
            })

            ad.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    callback?.invoke(false, false, true, false, false)
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    callback?.invoke(false, false, false, false, true)
                }
            }
        }
    })
}




