package com.mobi.pixels

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ads.R
import com.mobi.pixels.enums.NativeAdIcon
import com.mobi.pixels.enums.NativeAdType
import com.mobi.pixels.enums.NativeButtonPosition
import com.mobi.pixels.enums.ShimmerColor
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class AdNative(private val ctx: Activity, private val nativeAdContainer: FrameLayout, private val id: String, private val nativeAdType: NativeAdType) {
    private var textColorTitle: String? = null
    private var textColorDescription: String? = null
    private var textColorButton: String? = null
    private var colorButton: String? = null
    private var backgroundColor: String? = null
    private var buttonRoundness: Int = 0
    private var adIcon: NativeAdIcon? = null
    private var buttonPosition: NativeButtonPosition? = null
    private var shimmerEffect: Boolean = false
    private var shimmerColor: ShimmerColor? = null
    private var shimmerBackgroundColor: String? = null
    private var callback: ((loaded: Boolean, failed: Boolean) -> Unit)? = null


    fun textColorTitle(color: String): AdNative {
        textColorTitle = color
        return this
    }
    fun textColorDescription(color: String): AdNative {
        textColorDescription = color
        return this
    }
    fun textColorButton(color: String): AdNative {
        textColorButton = color
        return this
    }
    fun buttonRoundness(buttonround: Int): AdNative {
        buttonRoundness = buttonround
        return this
    }
    fun colorButton(color: String): AdNative {
        colorButton = color
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
    fun buttonPosition(position: NativeButtonPosition): AdNative {
        buttonPosition = position
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
                nativeAdContainer.minimumHeight = 610
                nativeAdContainer.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
                shimmerNative(ctx, nativeAdContainer,nativeAdType,shimmerColor,shimmerBackgroundColor)
            }
            if (nativeAdType == NativeAdType.NativeSmall) {
                nativeAdContainer.minimumHeight = 210
                nativeAdContainer.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
                shimmerNative(ctx, nativeAdContainer,nativeAdType,shimmerColor,shimmerBackgroundColor)
            }
        }

        val builder = AdLoader.Builder(ctx, id)
        builder.forNativeAd { nativeAd ->
            val nativeView: NativeAdView = when(nativeAdType){
                NativeAdType.NativeSmall -> {
                    if (buttonPosition == NativeButtonPosition.Top){
                        ctx.layoutInflater.inflate(R.layout.native_small_button_top, null) as NativeAdView
                    }else {
                        ctx.layoutInflater.inflate(R.layout.native_small_button_bottom, null) as NativeAdView
                    }
                }

                NativeAdType.NativeAdvance -> {
                    if (buttonPosition == NativeButtonPosition.Top){
                        ctx.layoutInflater.inflate(R.layout.native_advance_button_top, null) as NativeAdView
                    }else {
                        ctx.layoutInflater.inflate(R.layout.native_advance_button_bottom, null) as NativeAdView
                    }

                }

                else -> {
                    NativeAdView(ctx)
                }
            }
            nativeAdView(ctx,nativeAd, nativeView,nativeAdType,textColorTitle,textColorDescription,textColorButton,backgroundColor,colorButton,adIcon,buttonRoundness!!)
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
    private fun nativeAdView(context: Context, nativeAd: NativeAd, adView: NativeAdView, nativeAdType: NativeAdType, textColorTitle:String?, textColorDescription:String?, textColorButton:String?, backgroundColor :String?,
                             colorButton:String?, adIcon: NativeAdIcon?, buttonRoundness:Int) {
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
        if (textColorTitle!= null) (adView.headlineView as TextView?)!!.setTextColor(Color.parseColor(textColorTitle))

        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView?)!!.text = nativeAd.body
            if (textColorDescription!= null)  (adView.bodyView as TextView?)!!.setTextColor(Color.parseColor(textColorDescription))
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button?)!!.text = nativeAd.callToAction

            val backgroundDrawable = GradientDrawable()
            backgroundDrawable.cornerRadius = buttonRoundness.dpToPx(context).toFloat() // Adjust the corner radius as needed
            if (colorButton != null) backgroundDrawable.setColor(Color.parseColor(colorButton))
            adView.callToActionView!!.background = backgroundDrawable // Set the custom background

            //if (colorButton!= null) (adView.callToActionView as Button?)!!.setBackgroundColor(Color.parseColor(colorButton))
            if (textColorButton!= null) (adView.callToActionView as Button?)!!.setTextColor(Color.parseColor(textColorButton))
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

private fun Int.dpToPx(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun loadNativeAd(ctx: Activity, nativeAdContainer: FrameLayout, id: String, nativeAdType: NativeAdType): AdNative {
    return AdNative(ctx, nativeAdContainer, id, nativeAdType)
}