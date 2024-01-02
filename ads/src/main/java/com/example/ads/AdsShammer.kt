package com.example.ads

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.nativead.MediaView

fun shimmerNative(activity: Activity, container:FrameLayout, nativeAdType: NativeAdType, shimmerColor: ShimmerColor?, shimmerBackgroundColor:String?){
    if (nativeAdType == NativeAdType.NativeAdvance) container.addView(activity.layoutInflater.inflate(R.layout.shammer_native_advance, null))
    else if (nativeAdType == NativeAdType.NativeSmall) container.addView(activity.layoutInflater.inflate(R.layout.shammer_native_small, null))
    if (shimmerBackgroundColor!= null) container.findViewById<LinearLayout>(R.id.shimmerBackground).setBackgroundColor(Color.parseColor(shimmerBackgroundColor))
    var color:String? = null
    when (shimmerColor) {
        ShimmerColor.Black -> color = "#000000"
        ShimmerColor.White -> color = "#FFFFFF"
        ShimmerColor.Gray -> color = "#C2C2C2"
        else -> {}
    }
if (color!=null) {
    if (nativeAdType == NativeAdType.NativeAdvance) {
        val media = container.findViewById<View>(R.id.ad_media) as MediaView
        media.setBackgroundColor(Color.parseColor(color))
    }

    container.findViewById<ImageView>(R.id.ad_app_icon).setBackgroundColor(Color.parseColor(color))
    container.findViewById<TextView>(R.id.ad_headline).setBackgroundColor(Color.parseColor(color))
    container.findViewById<TextView>(R.id.ad_body).setBackgroundColor(Color.parseColor(color))
    container.findViewById<TextView>(R.id.ad_call_to_action)
        .setBackgroundColor(Color.parseColor(color))
}

}

fun shimmerBanner(activity: Activity, container:FrameLayout, shimmerColor: ShimmerColor?, shimmerBackgroundColor:String?){
   container.addView(activity.layoutInflater.inflate(R.layout.shammer_banner, null))

    if (shimmerBackgroundColor!= null){
        container.findViewById<LinearLayout>(R.id.shimmerBackground).setBackgroundColor(Color.parseColor(shimmerBackgroundColor))
    }

    var color:String? = null
    when (shimmerColor) {
        ShimmerColor.Black -> color = "#000000"
        ShimmerColor.White -> color = "#FFFFFF"
        ShimmerColor.Gray -> color = "#C2C2C2"
        else -> {}
    }
    if (color!=null) {
        container.findViewById<ImageView>(R.id.view).setBackgroundColor(Color.parseColor(color))
    }
}