package com.example.ads

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdBanner(private val ctx: Activity, private val BannerAdContainer: FrameLayout, private val id: String, private val bannerAdType: BannerAdType) {

    private var shimmerEffect: Boolean = false
    private var shimmerColor: ShimmerColor? = null
    private var shimmerBackgroundColor: String? = null
    private var callback: ((loaded: Boolean, failed: Boolean) -> Unit)? = null

    fun shimmerEffect(effect: Boolean): AdBanner {
        shimmerEffect = effect
        return this
    }
    fun shimmerColor(color: ShimmerColor): AdBanner {
        shimmerColor = color
        return this
    }
    fun shimmerBackgroundColor(color: String): AdBanner {
        shimmerBackgroundColor = color
        return this
    }
    fun callback(callback: ((loaded: Boolean, failed: Boolean) -> Unit)?): AdBanner {
        this.callback = callback
        return this
    }
    fun load() {
        // view visibility gone if there is no internet
        if (!isOnline(ctx)){
            BannerAdContainer.visibility = View.GONE
            return
        }

        BannerAdContainer.visibility = View.VISIBLE
        BannerAdContainer.minimumHeight = 120
        BannerAdContainer.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        BannerAdContainer.removeAllViews()

        if (shimmerEffect) shimmerBanner(ctx, BannerAdContainer,shimmerColor,shimmerBackgroundColor)

        val adView = AdView(ctx)
        adView.adUnitId = id

        val adRequest: AdRequest = when (bannerAdType) {
            BannerAdType.Banner -> AdRequest.Builder().build()
            BannerAdType.CollapsibleBanner -> {
                val extras = Bundle()
                extras.putString("collapsible", "bottom")
                AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
            }
            else -> {
                AdRequest.Builder().build()
            }
        }

        val adSize: AdSize? = getAdSize(ctx)
        adView.setAdSize(adSize!!)
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                BannerAdContainer.visibility = View.GONE
                callback?.invoke(false, true)

            }

            override fun onAdLoaded() {
                BannerAdContainer.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                BannerAdContainer.removeAllViews()
                BannerAdContainer.addView(adView)
                callback?.invoke(true, false)
            }
        }

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
}

fun loadBannerAd(ctx: Activity, BannerAdContainer: FrameLayout, id: String, bannerAdType: BannerAdType): AdBanner {
    return AdBanner(ctx, BannerAdContainer, id, bannerAdType)
}