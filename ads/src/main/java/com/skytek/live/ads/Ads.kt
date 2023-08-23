package com.skytek.live.ads

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

var mInterstitialAd:InterstitialAd? = null

fun loadInterstitialAd(ctx: Activity, id: String, callback: (loaded:Boolean,failed:Boolean) -> Unit) {

    if (mInterstitialAd!=null){
        callback(true, false)
        return
    }
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(ctx, id, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            mInterstitialAd = interstitialAd
            callback(true, false)
        }
        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            callback(false, true)
        }
    })
}

    fun showInterstitialAd(ctx: Activity, callback: (showed:Boolean,dismissed:Boolean,error:Boolean) -> Unit) {
        if (mInterstitialAd==null) {
            callback(false, false,true)
            return
        }
        mInterstitialAd?.show(ctx)
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                callback(true, false,false)
                super.onAdDismissedFullScreenContent()
            }

            override fun onAdShowedFullScreenContent() {
                callback(false, true,false)
                mInterstitialAd = null
                super.onAdShowedFullScreenContent()
            }
        }
    }

     fun loadShowInterstitialAd(ctx: Activity, id: String, callback: (loaded:Boolean,failed:Boolean,showed:Boolean,dismissed:Boolean) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(ctx, id, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                callback(true,false,false,false)
                interstitialAd.show(ctx)
                interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        callback(false,false,false,true)
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdShowedFullScreenContent() {
                        callback(false,false,true,false)
                        super.onAdShowedFullScreenContent()
                    }
                }
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                callback(false,true,false,false)
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

     private fun getAdSize(activity: Activity): AdSize? {
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

     fun loadSmallNativeAd(ctx: Activity, nativeAdContainer: FrameLayout, id: String) {
        val builder = AdLoader.Builder(ctx, id)
        builder.forNativeAd { nativeAd ->
            val nativeView = ctx.layoutInflater.inflate(R.layout.native_small, null) as NativeAdView
            smallNativeAdView(nativeAd, nativeView)
            nativeAdContainer.removeAllViews()
            nativeAdContainer.addView(nativeView)
            nativeAdContainer.visibility = View.VISIBLE
        }
        val adLoader = builder.withAdListener(object : AdListener() {
            fun onAdFailedToLoad(errorCode: Int) {}
            override fun onAdClicked() {
                // Log the click event or other custom behavior.
            }
        }).build()
        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }

    private fun smallNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {

        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.priceView = adView.findViewById(R.id.ad_price)
        val constraint: ConstraintLayout = adView.findViewById(R.id.constraint)
        val ad_body = adView.findViewById<TextView>(R.id.ad_body)
        val ad_headline = adView.findViewById<TextView>(R.id.ad_headline)

        (adView.headlineView as TextView).text = nativeAd.headline
        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.INVISIBLE
        } else {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.INVISIBLE
        } else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }
        if (nativeAd.starRating == null) {
            adView.starRatingView?.visibility = View.GONE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView?.visibility = View.VISIBLE
        }
        if (nativeAd.price == null) {
            adView.priceView?.visibility = View.GONE
        } else {
            adView.priceView?.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }
        if (nativeAd.icon == null) {
            adView.iconView?.visibility = View.GONE
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraint)
            constraintSet.connect(
                R.id.ad_headline,
                ConstraintSet.TOP,
                constraint.id,
                ConstraintSet.TOP,
                24
            )
            constraintSet.connect(
                R.id.ad_headline,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                32
            )
            constraintSet.connect(
                R.id.ad_body,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                32
            )
            constraintSet.connect(
                R.id.ad_body,
                ConstraintSet.TOP,
                R.id.ad_headline,
                ConstraintSet.BOTTOM,
                3
            )
            ad_headline.textSize = 16f
            ad_body.textSize = 14f
            constraintSet.applyTo(constraint)
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon?.drawable
            )
            adView.iconView?.visibility = View.VISIBLE
        }


        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)
    }

     fun loadAdvanceNativeAd(ctx: Activity, nativeAdContainer: FrameLayout, id: String) {
        val builder = AdLoader.Builder(ctx, id)
        builder.forNativeAd { nativeAd ->
            val nativeView =
                ctx.layoutInflater.inflate(R.layout.native_advance, null) as NativeAdView
            advanceNativeAdView(nativeAd, nativeView)
            nativeAdContainer.removeAllViews()
            nativeAdContainer.addView(nativeView)
            nativeAdContainer.visibility = View.VISIBLE
        }
        val adLoader = builder.withAdListener(object : AdListener() {
            fun onAdFailedToLoad(errorCode: Int) {}
            override fun onAdClicked() {
                // Log the click event or other custom behavior.
            }
        }).build()
        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }

    private fun advanceNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        (adView.headlineView as TextView?)!!.text = nativeAd.headline
        adView.mediaView!!.mediaContent = nativeAd.mediaContent
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
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(nativeAd.icon!!.drawable)
            adView.iconView!!.visibility = View.VISIBLE
        }
        if (nativeAd.price == null) {
            adView.priceView!!.visibility = View.INVISIBLE
        } else {
            adView.priceView!!.visibility = View.VISIBLE
            (adView.priceView as TextView?)!!.text = nativeAd.price
        }
        if (nativeAd.store == null) {
            adView.storeView!!.visibility = View.INVISIBLE
        } else {
            adView.storeView!!.visibility = View.VISIBLE
            (adView.storeView as TextView?)!!.text = nativeAd.store
        }
        if (nativeAd.starRating == null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar?)!!.rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView!!.visibility = View.VISIBLE
        }
        if (nativeAd.advertiser == null) {
            adView.advertiserView!!.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView?)!!.text = nativeAd.advertiser
            adView.advertiserView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }
