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
implementation 'com.github.Amankhan-mobipixels:Admob-Ads:1.1.4'
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

        loadBannerAd(this,binding.banner,getString(R.string.banner_id))

        loadCollapsibleBannerAd(this,binding.collapsibleBanner,getString(R.string.collapsible_id))

        loadNativeAd(this,binding.nativeSmall,getString(R.string.native_ad),AdType.NativeSmall,"#0730F7","#2857649A",)
       
        loadNativeAd(this,binding.nativeAdvance,getString(R.string.native_ad),AdType.NativeAdvance,"#FF9902","#469F7941")
       
   
	
