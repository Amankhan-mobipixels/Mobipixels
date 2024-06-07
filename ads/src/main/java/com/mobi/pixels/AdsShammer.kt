package com.mobi.pixels

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.example.ads.R
import com.google.android.gms.ads.nativead.MediaView
import com.mobi.pixels.enums.NativeAdType
import com.mobi.pixels.enums.ShimmerColor

fun shimmerNative(
    activity: Activity,
    container: FrameLayout,
    nativeAdType: NativeAdType,
    shimmerColor: ShimmerColor?,
    shimmerBackgroundColor: String?) {
    // Inflate the appropriate layout based on the nativeAdType
    val layoutId = when (nativeAdType) {
        NativeAdType.NativeAdvance -> R.layout.shimmer_native_advance
        NativeAdType.NativeSmall -> R.layout.shimmer_native_small
    }
    container.addView(activity.layoutInflater.inflate(layoutId, null))
    // Set the shimmer background color if provided
    shimmerBackgroundColor?.let { container.findViewById<LinearLayout>(R.id.shimmerBackground).setBackgroundColor(Color.parseColor(it)) }
    // Determine the shimmer color based on the provided shimmerColor
    val color = when (shimmerColor) {
        ShimmerColor.Black -> "#000000"
        ShimmerColor.White -> "#FFFFFF"
        ShimmerColor.Gray -> "#C2C2C2"
        else -> null
    }
    // Set the shimmer color for the appropriate views if the color is determined
    color?.let {
        if (nativeAdType == NativeAdType.NativeAdvance) { val media = container.findViewById<View>(R.id.ad_media) as MediaView
            media.setBackgroundColor(Color.parseColor(it))
        }
        listOf(R.id.ad_app_icon, R.id.ad_headline, R.id.ad_body, R.id.ad_call_to_action).forEach { viewId ->
            if (viewId==R.id.ad_call_to_action){
                container.findViewById<CardView>(viewId).setCardBackgroundColor(Color.parseColor(it))
                return
            }
            container.findViewById<View>(viewId).setBackgroundColor(Color.parseColor(it))
        }
    }
}

fun shimmerBanner(
    activity: Activity,
    container: FrameLayout,
    shimmerColor: ShimmerColor?,
    shimmerBackgroundColor: String?
) {
    // Inflate the shimmer banner layout and add it to the container
    container.addView(activity.layoutInflater.inflate(R.layout.shimmer_banner, null))

    // Set the shimmer background color if provided
    shimmerBackgroundColor?.let {
        container.findViewById<LinearLayout>(R.id.shimmerBackground)
            .setBackgroundColor(Color.parseColor(it))
    }

    // Determine the shimmer color based on the provided shimmerColor
    val color = when (shimmerColor) {
        ShimmerColor.Black -> "#000000"
        ShimmerColor.White -> "#FFFFFF"
        ShimmerColor.Gray -> "#C2C2C2"
        else -> null
    }

    // Set the shimmer view color if it was determined
    color?.let {
        container.findViewById<ImageView>(R.id.view).setBackgroundColor(Color.parseColor(it))
    }
}