# Admob-Ads
**add maven in your project level gradle**
````
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' 
		}
	}
}
````
**add dependency in module level gradle**
````
dependencies:
{
implementation 'com.github.Amankhan-mobipixels:Admob-Ads:1.2.9'
}
````
**get user consent on splash or mainscreen (for European Economic Area (EEA) and the UK)**
````
//if consent is true load your ad
val consent = GDPRMessage(this)
        consent.consentMessageRequest()
        consent.getConsent{
             //load ad here
            }
````
**How to use In-app updates**
````
 updateApp(UpdateType.Force){ onCancel ->
            finishAffinity()
        }
````
**How to use Firebase functionalities**
````
// In your root-level (project-level) Gradle file add

plugins {
  id("com.google.gms.google-services") version "4.4.1" apply false
}

//In your module (app-level) Gradle file add

plugins {
  id("com.google.gms.google-services")
}

After adding these dependencies Firebase analytics added by default for built-in events of your app
````
**How to use Firebase custom events**
````
 fireEvent("DownloadComplete")
````
**How to use In-app review**
````
Activity:  inAppReview()
fragment:  requireActivity().inAppReview()
````
**How to use ADS**

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
   
	
