package com.example.live

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobi.pixels.enums.NativeAdIcon
import com.mobi.pixels.enums.BannerAdType
import com.mobi.pixels.enums.NativeAdType
import com.mobi.pixels.enums.NativeButtonPosition
import com.mobi.pixels.enums.ShimmerColor
import com.mobi.pixels.enums.UpdateType
import com.mobi.pixels.firebase.fireEvent
import com.mobi.pixels.firebase.initializeFirebaseMessaging
import com.mobi.pixels.inAppReview
import com.mobi.pixels.isOnline
import com.mobi.pixels.loadBannerAd
import com.mobi.pixels.loadNativeAd
import com.mobi.pixels.showInterstitialAd
import com.mobi.pixels.updateApp
import com.example.live.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isOnline(this@MainActivity)

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

        loadBannerAd(this,binding.banner,"ca-app-pub-3940256099942544/6300978111", BannerAdType.Banner)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback{
                loaded, failed ->
            }.load()
        loadBannerAd(this,binding.collapsibleBanner,"ca-app-pub-3940256099942544/2014213617",
            BannerAdType.CollapsibleBanner)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback{
                loaded, failed ->
            }.load()

        loadNativeAd(this, binding.nativeAdvance, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeAdvance)
            .backgroundColor("#000000")
            .textColorButton("#ffffff")
            .colorButton("#ff5800")
            .buttonRoundness(30)
            .adIcon(NativeAdIcon.White)
            .buttonPosition(NativeButtonPosition.Top)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback { loaded, failed ->
                // Callback logic here
            }
            .load()

        loadNativeAd(this, binding.nativeSmall,"ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeSmall)
            .backgroundColor("#000000")
            .textColorButton("#ffffff")
            .colorButton("#ff0058")
            .buttonPosition(NativeButtonPosition.Bottom)
            .buttonRoundness(30)
            .adIcon(NativeAdIcon.White)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback { loaded, failed ->
                // Callback logic here
            }
            .load()


        updateApp(UpdateType.Force){ onCancel ->
            finishAffinity()
        }

//        inAppReview()
//        fireEvent("DownloadComplete")
//        initializeFirebaseMessaging("AppName", com.example.ads.R.drawable.ad_text_background_black)
    }

    override fun onResume() {
        super.onResume()
        showInterstitialAd(this)
    }
}
