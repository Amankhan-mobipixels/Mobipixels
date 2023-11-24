package com.example.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ads.AdType
import com.example.ads.GDPRMessage
import com.example.ads.loadNativeAd
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

//        loadBannerAd(this,binding.banner,getString(R.string.banner_id))
        loadNativeAd(this,binding.nativeSmall,getString(R.string.native_ad),AdType.NativeSmall,"#0730F7","#2857649A",)
        loadNativeAd(this,binding.nativeAdvance,getString(R.string.native_ad),AdType.NativeAdvance,"#FF9902","#469F7941")
//        loadCollapsibleBannerAd(this,binding.collapsibleBanner,getString(R.string.collapsible_id))
    }
}
