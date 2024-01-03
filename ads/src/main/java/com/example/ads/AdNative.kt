package com.example.ads

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class AdNative(private val ctx: Activity, private val nativeAdContainer: FrameLayout, private val id: String, private val nativeAdType: NativeAdType) {
    private var textColor: String? = null
    private var buttonColor: String? = null
    private var backgroundColor: String? = null
    private var adIcon: NativeAdIcon? = null
    private var shimmerEffect: Boolean = false
    private var shimmerColor: ShimmerColor? = null
    private var shimmerBackgroundColor: String? = null
    private var callback: ((loaded: Boolean, failed: Boolean) -> Unit)? = null

    fun textColor(color: String): AdNative {
        textColor = color
        return this
    }

    fun buttonColor(color: String): AdNative {
        buttonColor = color
        return this
    }

    fun backgroundColor(color: String): AdNative {
        backgroundColor = color
        return this
    }

    fun adIcon(icon: NativeAdIcon): AdNative {
        adIcon = icon
        return this
    }
    fun shimmerEffect(effect: Boolean): AdNative {
        shimmerEffect = effect
        return this
    }
    fun shimmerColor(color: ShimmerColor): AdNative {
        shimmerColor = color
        return this
    }
    fun shimmerBackgroundColor(color: String): AdNative {
        shimmerBackgroundColor = color
        return this
    }
    fun callback(callback: ((loaded: Boolean, failed: Boolean) -> Unit)?): AdNative {
        this.callback = callback
        return this
    }

    fun load() {

        // view visibility gone if there is no internet
        if (!isOnline(ctx)){
            nativeAdContainer.visibility = View.GONE
            return
        }
        nativeAdContainer.visibility = View.VISIBLE
        nativeAdContainer.removeAllViews()

        if (shimmerEffect) {
            if (nativeAdType == NativeAdType.NativeAdvance) {
                nativeAdContainer.minimumHeight = 650
                nativeAdContainer.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
                shimmerNative(ctx, nativeAdContainer,nativeAdType,shimmerColor,shimmerBackgroundColor)
            }
            if (nativeAdType == NativeAdType.NativeSmall) {
                nativeAdContainer.minimumHeight = 250
                nativeAdContainer.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
                shimmerNative(ctx, nativeAdContainer,nativeAdType,shimmerColor,shimmerBackgroundColor)
            }
        }

        val builder = AdLoader.Builder(ctx, id)
        builder.forNativeAd { nativeAd ->
            val nativeView: NativeAdView = when(nativeAdType){
                NativeAdType.NativeSmall -> {
                    ctx.layoutInflater.inflate(R.layout.native_small, null) as NativeAdView
                }

                NativeAdType.NativeAdvance -> {
                    ctx.layoutInflater.inflate(R.layout.native_advance, null) as NativeAdView
                }

                else -> {
                    NativeAdView(ctx)
                }
            }
            nativeAdView(ctx,nativeAd, nativeView,nativeAdType,textColor,backgroundColor,buttonColor,adIcon)
            nativeAdContainer.removeAllViews()
            nativeAdContainer.addView(nativeView)
        }

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                nativeAdContainer.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                callback?.invoke(true, false)
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                nativeAdContainer.visibility = View.GONE
                callback?.invoke(false, true)
            }
        }).build()
        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }
    private fun nativeAdView(context: Context, nativeAd: NativeAd, adView: NativeAdView, nativeAdType: NativeAdType, textColor:String?, backgroundColor :String?,
                             buttonColor:String?, adIcon: NativeAdIcon?) {
        if (backgroundColor!= null) adView.findViewById<LinearLayout>(R.id.nativeBackground).setBackgroundColor(
            Color.parseColor(backgroundColor))
        if (adIcon!=null){
            if (adIcon == NativeAdIcon.White) {
                adView.findViewById<TextView>(R.id.icon_ad).background = context.getDrawable(R.drawable.ad_text_background_white)
                adView.findViewById<TextView>(R.id.icon_ad).setTextColor(Color.WHITE)
            }
            else {
                adView.findViewById<TextView>(R.id.icon_ad).background = context.getDrawable(R.drawable.ad_text_background_black)
                adView.findViewById<TextView>(R.id.icon_ad).setTextColor(Color.BLACK)
            }
        }
        if(nativeAdType == NativeAdType.NativeAdvance){
            adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView
            adView.mediaView!!.mediaContent = nativeAd.mediaContent
        }

        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)


        (adView.headlineView as TextView?)!!.text = nativeAd.headline
        (adView.headlineView as TextView?)!!.isSelected = true
        if (textColor!= null) (adView.headlineView as TextView?)!!.setTextColor(Color.parseColor(textColor))

        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView?)!!.text = nativeAd.body
            if (textColor!= null)  (adView.bodyView as TextView?)!!.setTextColor(Color.parseColor(textColor))
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button?)!!.text = nativeAd.callToAction
            if (buttonColor!= null) (adView.callToActionView as Button?)!!.setBackgroundColor(Color.parseColor(buttonColor))
            if (textColor!= null) (adView.callToActionView as Button?)!!.setTextColor(Color.parseColor(textColor))
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(nativeAd.icon!!.drawable)
            adView.iconView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }
}

fun loadNativeAd(ctx: Activity, nativeAdContainer: FrameLayout, id: String, nativeAdType: NativeAdType): AdNative {
    return AdNative(ctx, nativeAdContainer, id, nativeAdType)
}