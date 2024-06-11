package com.example.live

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.mobi.pixels.openAd.InitializeOpenAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        InitializeOpenAd(this@MyApplication,"ca-app-pub-3940256099942544/9257395921")
    }
}