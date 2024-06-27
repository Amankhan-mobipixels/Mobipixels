# MobiPixels
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
implementation 'com.github.Amankhan-mobipixels:MobiPixels:2.1.10'
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
**How to use Firebase functionalities with default Crashlytics**
````
Must add 'google-services.json' file before application run

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

After adding these dependencies Crashlytics and Firebase analytics added by default for built-in events of your app
````
**How to use Firebase custom events**
````
 fireEvent(this.javaClass.name) // get the name of current screen running
````
**How to use Firebase Messaging**
````
// add this in menefist under application tag
     <service
            android:name="com.mobi.pixels.firebase.Messaging"
            android:exported="false" >
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

// subscribeToTopic string is for fire notification on firebase with this topic name will immediate receive to user
//notificationIcon shows at the time of notification in app
// make sure you have post notification permission for android 13 and above

context.initializeFirebaseMessaging(subscribeToTopic)
````
**How to use Remote Config**
````
 InitializeRemoteConfig{
            val isInterEnabled = Firebase.remoteConfig.getBoolean("splashAd")
            val interValue = Firebase.remoteConfig.getString("splash")
            Log.d("fgjhdf", isInterEnabled.toString())
            Log.d("fgjhdf", interValue)
        }
````
**How to use In-app review**
````
Activity:  inAppReview()
fragment:  requireActivity().inAppReview()
````
**How to use ADS**

        MobileAds.initialize(this)  //initialize ads in application class or onCreate of splash screen 
	
       // you can also use mediation but you will initialize adapter for initialization instead of (MobileAds.initialize(this)) and the platform dependencies you want mediation with.
       

      // Open App Ad
      class MyApplication:Application(){
           override fun onCreate() {
            super.onCreate()
             InitializeOpenAd(this@MyApplication,unitId,"Splash")
           }
         }


         // Interstitial AD
	     Interstitial.load(this,"ca-app-pub-3940256099942544/1033173712",object : AdInterstitialLoadListeners {
             override fun onLoaded() {
                 Log.d("sdfjkhdsf","mainonLoaded")
             }

             override fun onFailedToLoad() {
                 Log.d("sdfjkhdsf","mainonFailedToLoad")
             }

             override fun onPreviousAdLoading() {
                 Log.d("sdfjkhdsf","mainonPreviousAdLoading")
             }

         })
           Interstitial.show(this@Splash,object : AdInterstitialShowListeners{
            override fun onShowed() {
            }

            override fun onError() {
            }

            override fun onDismissed() {
            }

        })


    // Rewarded AD
	        Rewarded.load(this,"123",object :AdRewardedLoadListeners{
            override fun onFailedToLoad() {
           
            }

            override fun onLoaded() {
      
            }

            override fun onPreviousAdLoading() {
            }

        })
	
            Rewarded.show(this,object :AdRewardedShowListeners{
            override fun onCompleted() {
            }

            override fun onDismissed() {
            }

            override fun onError() {
            }

            override fun onShowed() {
            }

        })


           // Native AD
           loadOnDemandNativeAd(this, binding.nativeSmall, "ca-app-pub-3940256099942544/2247696110", NativeAdType.NativeSmall)
            .setBackgroundColor("#61C6A2FF")
            .setTextColorButton("#ffffff")
            .setButtonColor("#FF5589F1")
            .setButtonRoundness(30)
            .setAdIcon(NativeAdIcon.White)
            .enableShimmerEffect(true)
            .setShimmerBackgroundColor("#000000")
            .setShimmerColor(ShimmerColor.White)
            .adListeners(object : AdNativeOnDemandListeners {
                override fun onAdLoaded() {
                }
                override fun onAdFailedToLoad() {
                }
            })
            .load()

          // Banner AD
          val adReference = loadOnDemandBannerAd(this,binding.banner,"ca-app-pub-3940256099942544/6300978111", BannerAdType.Banner)
            .enableShimmerEffect(true)
            .setShimmerBackgroundColor("#000000")
            .setShimmerColor(ShimmerColor.White)
            .adListeners(object : AdBannerOnDemandListeners {
            override fun onAdLoaded() {

            }
            override fun onAdFailedToLoad() {


            }
        }).load()


     override fun onPause() {
        super.onPause()
        adReference?.pause()
    }

    override fun onResume() {
        super.onResume()
        adReference?.resume()
    }
   
	
