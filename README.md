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
**How to use Firebase crashlytics**
````
// In your root-level (project-level) Gradle file add

plugins {
    // Make sure that you have the AGP plugin 8.1+ dependency
    id("com.android.application") version "8.1.4" apply false

    // Make sure that you have the Google services Gradle plugin 4.4.1+ dependency
    id("com.google.gms.google-services") version "4.4.1" apply false

    // Add the dependency for the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics") version "3.0.1" apply false
}

//In your module (app-level) Gradle file add

plugins {
  // Make sure that you have the Google services Gradle plugin
  id("com.google.gms.google-services")

  // Add the Crashlytics Gradle plugin
  id("com.google.firebase.crashlytics")
}
````
**How to use Firebase Messaging**
````
// add this in menefist under application tag

     <service
            android:name="com.mobipixels.aman.firebase.Messaging"
            android:exported="false" >
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

// subscribeToTopic string is for fire notification on firebase with this topic name will immediate receive to user
//notificationIcon shows at the time of notification in app
 
initializeFirebaseMessaging(subscribeToTopic,notificationIcon)
````
**How to use In-app review**
````
Activity:  inAppReview()
fragment:  requireActivity().inAppReview()
````
**How to use ADS**

        MobileAds.initialize(this)  //initialize ads in application class or onCreate of splash screen 
	
       // you can also use mediation but you will initialize adapter for initialization instead of (MobileAds.initialize(this)) and the platform dependencies you want mediation with.
       

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
   
	
