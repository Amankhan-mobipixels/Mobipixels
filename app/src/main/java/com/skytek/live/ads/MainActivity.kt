package com.skytek.live.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.skytek.live.ads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadInterstitialAd(this, getString(R.string.interstitial_ad)) { loaded, failed ->
            if (loaded) Log.d("adddddd", "loaded")
            else if (failed) Log.d("adddddd", "failed")
        }

        showInterstitialAd(this) { showed, dismissed, error ->
            if (showed) Log.d("adddddd", "showed")
            if (dismissed) Log.d("adddddd", "dismissed")
            if (error) Log.d("adddddd", "error")
        }

        loadShowInterstitialAd(this, getString(R.string.interstitial_ad)) { loaded, failed, showed, dismissed ->
            if (loaded) Log.d("adddddd", "loaded")
            if (failed) Log.d("adddddd", "failed")
            if (showed) Log.d("adddddd", "showed")
            if (dismissed) Log.d("adddddd", "dismissed")
        }

        loadBannerAd(this,binding.banner,getString(R.string.banner_id))
        loadSmallNativeAd(this,binding.nativeSmall,getString(R.string.native_ad))
        loadAdvanceNativeAd(this,binding.nativeAdvance,getString(R.string.native_ad))
        loadCollapsibleBannerAd(this,binding.collapsibleBanner,getString(R.string.collapsible_id))
    }
}
