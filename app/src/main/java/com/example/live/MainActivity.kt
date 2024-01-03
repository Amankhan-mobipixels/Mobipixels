package com.example.live

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ads.NativeAdIcon
import com.example.ads.BannerAdType
import com.example.ads.GDPRMessage
import com.example.ads.NativeAdType
import com.example.ads.ShimmerColor
import com.example.ads.loadBannerAd
import com.example.ads.loadNativeAd
import com.example.ads.showInterstitialAd
import com.example.live.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val consent = GDPRMessage(this)
//        consent.consentMessageRequest("551C6DD69736913D8C23756B69E049E9",true)
//        consent.getConsent{
//            Log.d("Fdsfsdfsdsavt", it.toString())
//        }


//        loadInterstitialAd(this,"ca-app-pub-3940256099942544/1033173712"){
//            loaded, failed ->
//            if (loaded) {
//                Log.d("checkcheck", "loaded")
//
//            }
//            if (failed) Log.d("checkcheck", "failed")
//        }


//

        loadBannerAd(this,binding.banner,"ca-app-pub-3940256099942544/6300978111",BannerAdType.Banner)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback{
                loaded, failed ->
            }.load()
        loadBannerAd(this,binding.collapsibleBanner,"ca-app-pub-3940256099942544/2014213617",BannerAdType.CollapsibleBanner)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback{
                loaded, failed ->
            }.load()

        loadNativeAd(this, binding.nativeAdvance, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeAdvance)
            .backgroundColor("#000000")
            .textColor("#ffffff")
            .buttonColor("#ffffff")
            .adIcon(NativeAdIcon.White)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback { loaded, failed ->
                // Callback logic here
            }
            .load()

        loadNativeAd(this, binding.nativeSmall,"ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeSmall)
            .backgroundColor("#000000")
            .textColor("#ffffff")
            .buttonColor("#000000")
            .adIcon(NativeAdIcon.White)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback { loaded, failed ->
                // Callback logic here
            }
            .load()

    }

    override fun onResume() {
        super.onResume()
        showInterstitialAd(this)
    }
}
