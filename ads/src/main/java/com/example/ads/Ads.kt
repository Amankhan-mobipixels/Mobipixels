package com.example.ads

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
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

     fun loadBannerAd(activity: Activity, view: FrameLayout, id: String) {
        val adView = AdView(activity)
        adView.adUnitId = id
        view.addView(adView)
        val adRequest = AdRequest.Builder().build()
        val adSize: AdSize? = getAdSize(activity)
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize!!)
        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

     fun loadCollapsibleBannerAd(activity: Activity, view: FrameLayout, id: String) {
        val adView = AdView(activity)
        adView.adUnitId = id
        view.addView(adView)
        val extras = Bundle()
        extras.putString("collapsible", "bottom")
        val adRequest =
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
        val adSize: AdSize? = getAdSize(activity)
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize!!)
        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

     private fun getAdSize(activity: Activity): AdSize {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)

    }

     fun loadNativeAd(ctx: Activity, nativeAdContainer: FrameLayout, id: String,adType: AdType,
                      buttonColor:String,backgroundColor:String,callback: ((loaded:Boolean,failed:Boolean) -> Unit)?=null) {
        val builder = AdLoader.Builder(ctx, id)
        builder.forNativeAd { nativeAd ->
           val nativeView:NativeAdView = when(adType){
                AdType.NativeSmall -> {
                    ctx.layoutInflater.inflate(R.layout.native_small, null) as NativeAdView
                }

                AdType.NativeAdvance -> {
                    ctx.layoutInflater.inflate(R.layout.native_advance, null) as NativeAdView
                }

                else -> {
                    NativeAdView(ctx)
                }
            }
            NativeAdView(nativeAd, nativeView,buttonColor,backgroundColor,adType)
            nativeAdContainer.removeAllViews()
            nativeAdContainer.addView(nativeView)
            nativeAdContainer.visibility = View.VISIBLE
        }
        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                callback?.invoke(true, false)
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                callback?.invoke(false, true)
            }
        }).build()
        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }
    private fun NativeAdView(nativeAd: NativeAd, adView: NativeAdView,buttonColor:String, backgroundColor:String,adType: AdType) {
        adView.findViewById<LinearLayout>(R.id.background).setBackgroundColor(Color.parseColor(backgroundColor))
        if(adType == AdType.NativeAdvance){
            adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView
            adView.mediaView!!.mediaContent = nativeAd.mediaContent
        }

        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)


        (adView.headlineView as TextView?)!!.text = nativeAd.headline
        (adView.headlineView as TextView?)!!.isSelected = true
        (adView.headlineView as TextView?)!!.setTextColor(Color.parseColor(buttonColor))

        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView?)!!.text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button?)!!.text = nativeAd.callToAction
            (adView.callToActionView as Button?)!!.setBackgroundColor(Color.parseColor(buttonColor))
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(nativeAd.icon!!.drawable)
            adView.iconView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }

