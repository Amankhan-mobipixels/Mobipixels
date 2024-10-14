package com.mobi.pixels.adNativeOnDemand

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
import com.mobi.pixels.enums.ShimmerColor
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.mobi.pixels.initialize.Ads
import com.mobi.pixels.isOnline
import com.mobi.pixels.shimmerNative

class AdNativeOnDemand(
    private val ctx: Activity,
    private val nativeAdContainer: FrameLayout,
    private val id: String,
    private val nativeAdType: NativeAdType
) {
    private var textColorTitle: String? = null
    private var textColorDescription: String? = null
    private var textColorButton: String? = null
    private var colorButton: String? = null
    private var backgroundColor: String? = null
    private var buttonRoundness: Int = 0
    private var adIcon: NativeAdIcon? = null
    private var shimmerEffect: Boolean = false
    private var shimmerColor: ShimmerColor? = null
    private var shimmerBackgroundColor: String? = null
    private var adLoadListener: AdNativeOnDemandListeners? = null

    fun setTextColorTitle(color: String) = apply { textColorTitle = color }
    fun setTextColorDescription(color: String) = apply { textColorDescription = color }
    fun setTextColorButton(color: String) = apply { textColorButton = color }
    fun setButtonRoundness(buttonRound: Int) = apply { buttonRoundness = buttonRound }
    fun setButtonColor(color: String) = apply { colorButton = color }
    fun setBackgroundColor(color: String) = apply { backgroundColor = color }
    fun setAdIcon(icon: NativeAdIcon) = apply { adIcon = icon }
    fun enableShimmerEffect(effect: Boolean) = apply { shimmerEffect = effect }
    fun setShimmerColor(color: ShimmerColor) = apply { shimmerColor = color }
    fun setShimmerBackgroundColor(color: String) = apply { shimmerBackgroundColor = color }
    fun adListeners(listener: AdNativeOnDemandListeners?) = apply {
        adLoadListener = listener
    }

    fun load() {

        if (!isOnline(ctx)) {
            nativeAdContainer.visibility = View.GONE
            adLoadListener?.onAdFailedToLoad("Internet connection not detected. Please check your connection and try again.")
            return
        }
        if (!Ads.IsAdsAllowed) {
            nativeAdContainer.visibility = View.GONE
            adLoadListener?.onAdFailedToLoad("Ads disabled by developer")
            return
        }

        setupNativeContainer()

        if (shimmerEffect) {
            shimmerNative(ctx, nativeAdContainer, nativeAdType, shimmerColor, shimmerBackgroundColor)
        }

        val adLoader = AdLoader.Builder(ctx, id)
            .forNativeAd { nativeAd -> displayNativeAd(nativeAd) }
            .withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    adLoadListener?.onAdLoaded()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    nativeAdContainer.visibility = View.GONE
                    adLoadListener?.onAdFailedToLoad(loadAdError.message)
                }
            })
            .build()

        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }

    private fun setupNativeContainer() {
        nativeAdContainer.apply {
            visibility = View.VISIBLE
            removeAllViews()
            layoutParams.height =  FrameLayout.LayoutParams.WRAP_CONTENT
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        }
    }

    private fun displayNativeAd(nativeAd: NativeAd) {
        val nativeView = createNativeView()
        nativeAdView(ctx, nativeAd, nativeView)
        nativeAdContainer.apply {
            removeAllViews()
            addView(nativeView)
        }
    }

    private fun createNativeView(): NativeAdView {
        val layoutId = when (nativeAdType) {
            NativeAdType.NativeSmall -> R.layout.native_small

            NativeAdType.NativeAdvance -> R.layout.native_advance
        }
        return ctx.layoutInflater.inflate(layoutId, null) as NativeAdView
    }

    private fun nativeAdView(
        context: Context,
        nativeAd: NativeAd,
        adView: NativeAdView
    ) {
        adView.apply {
            headlineView = findViewById(R.id.ad_headline)
            bodyView = findViewById(R.id.ad_body)
            callToActionView = findViewById(R.id.ad_call_to_action)
            iconView = findViewById(R.id.ad_app_icon)
            if (nativeAdType == NativeAdType.NativeAdvance) {
                mediaView = findViewById(R.id.ad_media)
                mediaView?.mediaContent = nativeAd.mediaContent
            }

            findViewById<LinearLayout>(R.id.nativeBackground)?.apply {
                backgroundColor?.let { setBackgroundColor(Color.parseColor(it)) }
            }

            findViewById<TextView>(R.id.icon_ad)?.apply {
                adIcon?.let { icon ->
                    val backgroundDrawableRes = if (icon == NativeAdIcon.White) {
                        R.drawable.ad_text_background_white
                    } else {
                        R.drawable.ad_text_background_black
                    }
                    background = context.getDrawable(backgroundDrawableRes)
                    setTextColor(if (icon == NativeAdIcon.White) Color.WHITE else Color.BLACK)
                }
            }

            (headlineView as TextView).apply {
                text = nativeAd.headline
                isSelected = true
                textColorTitle?.let { setTextColor(Color.parseColor(it)) }
            }

            bodyView?.apply {
                visibility = if (nativeAd.body == null) View.INVISIBLE else View.VISIBLE
                (this as TextView).apply {
                    text = nativeAd.body
                    textColorDescription?.let { setTextColor(Color.parseColor(it)) }
                }
            }

            callToActionView?.apply {
                visibility = if (nativeAd.callToAction == null) View.INVISIBLE else View.VISIBLE
                (this as Button).apply {
                    text = nativeAd.callToAction
                    textColorButton?.let { setTextColor(Color.parseColor(it)) }
//                    colorButton?.let { backgroundTintList = ColorStateList.valueOf(Color.parseColor(it)) }
                    background = GradientDrawable().apply {
                        cornerRadius = buttonRoundness.dpToPx(context).toFloat()
                        colorButton?.let { setColor(Color.parseColor(it)) }
                    }
                    layoutParams.height = 40
                }
            }

            iconView?.apply {
                visibility = if (nativeAd.icon == null) View.GONE else View.VISIBLE
                (this as ImageView).apply {
                    setImageDrawable(nativeAd.icon?.drawable)
                }
            }

            setNativeAd(nativeAd)
        }
    }
}

private fun Int.dpToPx(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun loadOnDemandNativeAd(
    ctx: Activity,
    nativeAdContainer: FrameLayout,
    id: String,
    nativeAdType: NativeAdType
): AdNativeOnDemand {
    return AdNativeOnDemand(ctx, nativeAdContainer, id, nativeAdType)
}
