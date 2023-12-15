package com.example.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.ads.AdIcon
import com.example.ads.AdType
import com.example.ads.loadInterstitialAd
import com.example.ads.loadNativeAd
import com.example.ads.loadShowInterstitialAd
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

        loadInterstitialAd(this, getString(R.string.interstitial_ad)){
            loaded, failed ->
            if (loaded) {
                Log.d("checkcheck", "loaded")

            }
            if (failed) Log.d("checkcheck", "failed")
        }

//        loadInterstitialAd(this, getString(R.string.interstitial_ad)){
//            loaded, failed ->
//            if (loaded) Log.d("checkcheck", "loaded")
//            if (failed) Log.d("checkcheck", "failed")
////            if (showed) Log.d("checkcheck", "showed")
////            if (dismissed) Log.d("checkcheck", "dismissed")
//        }
//        findViewById<Button>(R.id.ad).setOnClickListener{
//            showInterstitialAd(this){
//                    showed, dismissed, error ->
//                if (showed) Log.d("checkcheck", "showed")
//                if (dismissed) Log.d("checkcheck", "dismissed")
//                if (error) Log.d("checkcheck", "error")
//            }
//        }

//        loadBannerAd(this,binding.banner,getString(R.string.banner_id))
        loadNativeAd(this,binding.nativeSmall,getString(R.string.native_ad),AdType.NativeSmall)
        loadNativeAd(this,binding.nativeAdvance,getString(R.string.native_ad),AdType.NativeAdvance,
            textColor = "#ffffff", backgroundColor = "#FF9902", buttonColor = "#086EBF", adIcon = AdIcon.White)
//        loadCollapsibleBannerAd(this,binding.collapsibleBanner,getString(R.string.collapsible_id))



    }

    override fun onResume() {
        super.onResume()
        showInterstitialAd(this)
    }
}
