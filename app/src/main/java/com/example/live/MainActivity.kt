package com.example.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ads.loadAdvanceNativeAd
import com.example.ads.loadBannerAd
import com.example.ads.loadCollapsibleBannerAd
import com.example.ads.loadInterstitialAd
import com.example.ads.loadRewardedAd
import com.example.ads.loadShowInterstitialAd
import com.example.ads.loadShowRewardedAd
import com.example.ads.loadSmallNativeAd
import com.example.ads.showInterstitialAd
import com.example.ads.showRewardedAd
import com.example.live.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadRewardedAd(this,getString(R.string.rewarded_ad)){loaded, failed ->
            if (loaded) Log.d("adddddd", "loaded")
            if (failed) Log.d("adddddd", "failed")
        }
        showRewardedAd(this){showed, completed, dismissed, error ->
            if (showed) Log.d("adddddd", "showed")
            if (completed) Log.d("adddddd", "complete")
            if (dismissed) Log.d("adddddd", "dismissed")
            if (error) Log.d("adddddd", "error")
        }

        loadBannerAd(this,binding.banner,getString(R.string.banner_id))
        loadSmallNativeAd(this,binding.nativeSmall,getString(R.string.native_ad))
        loadAdvanceNativeAd(this,binding.nativeAdvance,getString(R.string.native_ad))
        loadCollapsibleBannerAd(this,binding.collapsibleBanner,getString(R.string.collapsible_id))
    }
}
