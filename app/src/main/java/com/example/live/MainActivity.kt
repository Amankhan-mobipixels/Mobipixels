package com.example.live

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mobi.pixels.enums.BannerAdType
import com.mobi.pixels.enums.ShimmerColor
import com.mobi.pixels.isOnline
import com.example.live.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.mobi.pixels.adBannerOnDemand.AdBannerOnDemandListeners
import com.mobi.pixels.adBannerOnDemand.loadOnDemandBannerAd
import com.mobi.pixels.adInterstitial.AdInterstitialLoadListeners
import com.mobi.pixels.adInterstitial.AdInterstitialShowListeners
import com.mobi.pixels.adInterstitial.Interstitial
import com.mobi.pixels.adNativeOnDemand.AdNativeOnDemandListeners
import com.mobi.pixels.adNativeOnDemand.loadOnDemandNativeAd
import com.mobi.pixels.adRewarded.Rewarded
import com.mobi.pixels.enums.NativeAdIcon
import com.mobi.pixels.enums.NativeAdType
import com.mobi.pixels.firebase.InitializeRemoteConfig

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    var adReference:AdView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        InitializeRemoteConfig{
//            val isInterEnabled = Firebase.remoteConfig.getBoolean("splashAd")
//            val interValue = Firebase.remoteConfig.getString("splash")
//            Log.d("fgjhdf", isInterEnabled.toString())
//            Log.d("fgjhdf", interValue)
//        }
//
//
//   MobileAds.initialize(this)
//        isOnline(this@MainActivity)
//        val consent = GDPRMessage(this)
//        consent.consentMessageRequest("551C6DD69736913D8C23756B69E049E9",true)
//        consent.getConsent{
//            Log.d("Fdsfsdfsdsavt", it.toString())
//        }
//
//     Interstitial.load(this,"ca-app-pub-3940256099942544/1033173712")
//         .adLoadListeners(object : AdInterstitialLoadListeners{
//             override fun onLoaded() {
//                 Interstitial.show(this@MainActivity)
//             }
//
//             override fun onFailedToLoad() {
//                 Log.d("sdfjkhdsf","mainonFailedToLoad")
//             }
//
//             override fun onPreviousAdLoading() {
//                 Log.d("sdfjkhdsf","mainonPreviousAdLoading")
//             }
//
//         })

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
                override fun onAdFailedToLoad() {

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
                override fun onAdFailedToLoad() {

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
