# Admob-Ads
add maven in your project level gradle
````
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' 
		}
	}
}
````
add dependency in module level gradle
````
dependencies:
{
implementation 'com.github.Amankhan-mobipixels:Admob-Ads:1.0.3'
}
````
How to use:

        loadInterstitialAd(this, getString(R.string.interstitial_ad)) { loaded, failed ->
            if (loaded) Log.d("adddddd", "loaded")
            if (failed) Log.d("adddddd", "failed")
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
	
