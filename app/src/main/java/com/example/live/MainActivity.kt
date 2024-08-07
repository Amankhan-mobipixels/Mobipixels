package com.example.live

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobi.pixels.enums.ShimmerColor
import com.example.live.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.mobi.pixels.adInterstitial.AdInterstitialLoadListeners
import com.mobi.pixels.adInterstitial.Interstitial
import com.mobi.pixels.adNativeOnDemand.AdNativeOnDemandListeners
import com.mobi.pixels.adNativeOnDemand.loadOnDemandNativeAd
import com.mobi.pixels.enums.NativeAdIcon
import com.mobi.pixels.enums.NativeAdType
import com.mobi.pixels.enums.UpdateType
import com.mobi.pixels.firebase.InitializeRemoteConfig
import com.mobi.pixels.initialize.Ads
import com.mobi.pixels.updateAppWithRemoteConfig

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    var adReference:AdView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Ads.initialize(this,true)

        InitializeRemoteConfig{
//            val isInterEnabled = Firebase.remoteConfig.getBoolean("splashAd")
//            val interValue = Firebase.remoteConfig.getString("splash")
            val version = Firebase.remoteConfig.getString("versions")
            updateAppWithRemoteConfig(version)
        }

//        updateApp(UpdateType.Force){ onCancel ->
//            finishAffinity()
//        }


//        isOnline(this@MainActivity)
//        val consent = GDPRMessage(this)
//        consent.consentMessageRequest("551C6DD69736913D8C23756B69E049E9",true)
//        consent.getConsent{
//            Log.d("Fdsfsdfsdsavt", it.toString())
//        }
//
     Interstitial.load(this,"ca-app-pub-3940256099942544/1033173712", object : AdInterstitialLoadListeners{
         override fun onLoaded() {

         }

         override fun onFailedToLoad(error: String) {

         }

         override fun onPreviousAdLoading() {

         }


     })
        Interstitial.show(this)

//
//
//
//
//         adReference = loadOnDemandBannerAd(this,binding.banner,"ca-app-pub-3940256099942544/6300978111", BannerAdType.Banner)
//            .enableShimmerEffect(true)
//            .setShimmerBackgroundColor("#000000")
//            .setShimmerColor(ShimmerColor.White)
//            .adListeners(object : AdBannerOnDemandListeners {
//            override fun onAdLoaded() {
//
//            }
//            override fun onAdFailedToLoad() {
//
//
//            }
//        }).load()

//        loadOnDemandBannerAd(this,binding.collapsibleBanner,"ca-app-pub-3940256099942544/6300978111", BannerAdType.CollapsibleBanner)
//            .enableShimmerEffect(true)
//            .setShimmerBackgroundColor("#000000")
//            .setShimmerColor(ShimmerColor.White)
//            .adListeners(object : AdBannerOnDemandListeners {
//                override fun onAdLoaded() {
//
//                }
//                override fun onAdFailedToLoad() {
//
//
//                }
//            }).load()

        loadOnDemandNativeAd(this, binding.nativeSmall, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeSmall)
            .setBackgroundColor("#61C6A2FF")
            .setTextColorButton("#ffffff")
            .setButtonColor("#FF5589F1")
            .setButtonRoundness(30)
            .setAdIcon(NativeAdIcon.White)
            .enableShimmerEffect(true)
            .setShimmerBackgroundColor("#ffffff")
            .setShimmerColor(ShimmerColor.Black)
            .adListeners(object : AdNativeOnDemandListeners {
                override fun onAdLoaded() {

                }
                override fun onAdFailedToLoad(error: String) {

                }
            })
            .load()
//
        loadOnDemandNativeAd(this, binding.nativeAdvance, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeAdvance)
            .setBackgroundColor("#61C6A2FF")
            .setTextColorButton("#ffffff")
            .setButtonColor("#FF5589F1")
            .setButtonRoundness(10)
            .setAdIcon(NativeAdIcon.White)
            .enableShimmerEffect(true)
            .setShimmerBackgroundColor("#ffffff")
            .setShimmerColor(ShimmerColor.Black)
            .adListeners(object : AdNativeOnDemandListeners {
                override fun onAdLoaded() {

                }
                override fun onAdFailedToLoad(error: String) {

                }
            })
            .load()


//        updateApp(UpdateType.Force){ onCancel ->
//            finishAffinity()
//        }
//
//        inAppReview()
//        fireEvent("successfull")
//        initializeFirebaseMessaging("demo")
    }

//    override fun onPause() {
//        super.onPause()
//        adReference?.pause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        adReference?.resume()
//    }

}
