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
implementation 'com.github.Amankhan-mobipixels:Admob-Ads:1.2.0'
}
````
get user consent on splash or mainscreen
````
//if consent is true load your ad
val consent = GDPRMessage(this)
        consent.consentMessageRequest()
        consent.getConsent{
               if (it)  // LoadAd
            }
````
How to use:

        loadInterstitialAd(this, getString(R.string.interstitial_ad))

        showInterstitialAd(this)

        loadShowInterstitialAd(this, getString(R.string.interstitial_ad))

        loadRewardedAd(this,getString(R.string.rewarded_ad))
       
        showRewardedAd(this)
	
        loadShowRewardedAd(this,getString(R.string.rewarded_ad))

        loadBannerAd(this,binding.banner,getString(R.string.banner_id),BannerAdType.Banner)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback{
                loaded, failed ->  
            }
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
   
	
