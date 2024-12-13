package com.example.live

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.ProductDetails
import com.mobi.pixels.enums.ShimmerColor
import com.example.live.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.mobi.pixels.adBannerOnDemand.AdBannerOnDemandListeners
import com.mobi.pixels.adBannerOnDemand.loadOnDemandBannerAd
import com.mobi.pixels.adInterstitial.AdInterstitialLoadListeners
import com.mobi.pixels.adInterstitial.Interstitial
import com.mobi.pixels.adNativeOnDemand.AdNativeOnDemandListeners
import com.mobi.pixels.adNativeOnDemand.loadOnDemandNativeAd
import com.mobi.pixels.adNativePreload.AdNativePreload
import com.mobi.pixels.adNativePreload.AdNativePreloadListeners
import com.mobi.pixels.adNativePreload.AdNativePreloadShowListeners
import com.mobi.pixels.adNativePreload.showAdNativePreloaded
import com.mobi.pixels.enums.BannerAdType
import com.mobi.pixels.enums.NativeAdIcon
import com.mobi.pixels.firebase.initializeFirebaseMessaging
import com.mobi.pixels.enums.NativeAdType
import com.mobi.pixels.enums.NativeLayoutType
import com.mobi.pixels.enums.PurchaseType
import com.mobi.pixels.enums.UpdateType
import com.mobi.pixels.firebase.InitializeRemoteConfig
import com.mobi.pixels.inAppPurchase.InAppPurchase
import com.mobi.pixels.inAppPurchase.InAppPurchase.checkPurchaseStatus
import com.mobi.pixels.inAppPurchase.PurchaseListener
import com.mobi.pixels.initialize.Ads
import com.mobi.pixels.updateApp
import com.mobi.pixels.updateAppWithRemoteConfig

class MainActivity : AppCompatActivity(),PurchaseListener{
    private lateinit var binding: ActivityMainBinding
    var adReference:AdView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFirebaseMessaging("demo", com.example.ads.R.drawable.ad_text_background_black)
        Ads.initialize(this,true)

        InitializeRemoteConfig(0){
//            val isInterEnabled = Firebase.remoteConfig.getBoolean("splashAd")
//            val interValue = Firebase.remoteConfig.getString("splash")
            val version = Firebase.remoteConfig.getString("versions")
            updateAppWithRemoteConfig(version)
        }

        updateApp(UpdateType.Force)

        var details :   List<ProductDetails>? = null
       InAppPurchase.initialize(this, listOf("hello,hi"), PurchaseType.Subscription){
            if (it.isNotEmpty()) {
                details = it

                it.forEach { productDetails ->

                    Log.d("Product Details", productDetails.toString())
                }
            } else {
                Log.d("Product Details", "No products found or an error occurred.")
            }
        }
//        InAppPurchase.launchPurchaseFlow(this,details!![0],this)

        checkPurchaseStatus(this,PurchaseType.InAppProduct){
            if (it.isNotEmpty()) {
                Log.d("Purchased Product IDs", it.joinToString(", "))
            }
        }

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
//                override fun onAdFailedToLoad(error: String) {
//                    Log.d("fd4ef",error)
//                }
//
//
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

//        showAdNativePreloaded(this,binding.nativeSmall,NativeAdType.NativeSmall,NativeLayoutType.Layout1)
//            .setBackgroundColor("#61C6A2FF")
//            .setTextColorButton("#ffffff")
//            .setButtonColor("#FF5589F1")
//            .setButtonRoundness(30)
//            .setAdIcon(NativeAdIcon.White)
//            .showListeners(object : AdNativePreloadShowListeners{
//                override fun onShowed() {
//                    Log.d("hdfgjkdf3454","onShowed")
//                }
//
//                override fun onError(error: String) {
//                    Log.d("hdfgjkdf3454","onError")
//                }
//
//            }).show()

        loadOnDemandNativeAd(this, binding.nativeSmall, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeSmall,NativeLayoutType.Layout2)
            .setBackgroundColor("#61C6A2FF")
            .setTextColorButton("#ffffff")
            .setButtonColor("#FF5589F1")
            .setButtonRoundness(30)
            .setButtonHeight(40)
            .setAdIcon(NativeAdIcon.Black)
            .enableShimmerEffect(true)
            .setShimmerBackgroundColor("#ffffff")
            .setShimmerColor(ShimmerColor.Black)
            .adListeners(object : AdNativeOnDemandListeners {
                override fun onAdLoaded() {}
                override fun onAdFailedToLoad(error: String) {}
            })
            .load()
//
//        loadOnDemandNativeAd(this, binding.nativeAdvance, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeAdvance)
//            .setBackgroundColor("#61C6A2FF")
//            .setTextColorButton("#ffffff")
//            .setButtonColor("#FF5589F1")
//            .setButtonRoundness(10)
//            .setAdIcon(NativeAdIcon.White)
//            .enableShimmerEffect(true)
//            .setShimmerBackgroundColor("#ffffff")
//            .setShimmerColor(ShimmerColor.Black)
//            .adListeners(object : AdNativeOnDemandListeners {
//                override fun onAdLoaded() {
//
//                }
//                override fun onAdFailedToLoad(error: String) {
//
//                }
//            })
//            .load()


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

    override fun onResume() {
        super.onResume()
        adReference?.resume()
   InAppPurchase.checkPendingTransactions(this,PurchaseType.InAppProduct,this)

    }

    override fun onPurchaseSuccess(value: String) {
        Log.d("dsjflkj409fjd", "product id $value")
    }

}
