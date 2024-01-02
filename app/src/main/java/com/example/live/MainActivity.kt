package com.example.live

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ads.AdIcon
import com.example.ads.BannerAdType
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
//        consent.consentMessageRequest()
//        consent.getConsent{
//                Log.d("Fdsfsdfsdsavt", it.toString())
//            }

//        loadInterstitialAd(this, getString(R.string.interstitial_ad)){
//            loaded, failed ->
//            if (loaded) {
//                Log.d("checkcheck", "loaded")
//
//            }
//            if (failed) Log.d("checkcheck", "failed")
//        }



        loadBannerAd(this,binding.banner,getString(R.string.banner_id),BannerAdType.Banner)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .load()
        loadNativeAd(this, binding.nativeAdvance, getString(R.string.native_ad), NativeAdType.NativeAdvance)
            .backgroundColor("#000000")
            .textColor("#ffffff")
            .buttonColor("#ffffff")
            .adIcon(AdIcon.White)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback { loaded, failed ->
                // Callback logic here
            }
            .load()

        loadNativeAd(this, binding.nativeSmall, getString(R.string.native_ad), NativeAdType.NativeSmall)
            .backgroundColor("#000000")
            .textColor("#ffffff")
            .buttonColor("#000000")
            .adIcon(AdIcon.White)
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
