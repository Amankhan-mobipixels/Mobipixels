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
implementation 'com.github.Amankhan-mobipixels:Admob-Ads:1.2.8'
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
In-app updates
````
updateApp(UpdateType.Force)
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
            .textColorTitle("#FF0101")
            .textColorDescription("#0F2197")
            .textColorButton("#AB0AF1")
            .colorButton("#D6D311")
            .backgroundColor("#07DA11")
            .buttonPosition(NativeButtonPosition.Bottom)
            .buttonRoundness(30)
            .shimmerEffect(true)
            .shimmerBackgroundColor("#000000")
            .shimmerColor(ShimmerColor.White)
            .callback { loaded, failed ->
                // Callback logic here
            }
            .load()
   
	
