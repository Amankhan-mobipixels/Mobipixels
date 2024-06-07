package com.mobi.pixels.adInterstitial

interface AdInterstitialLoadListeners {
    fun onLoaded()
    fun onFailedToLoad()
    fun onPreviousAdLoading()

}