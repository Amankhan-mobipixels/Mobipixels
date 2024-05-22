package com.mobi.pixels

import android.app.Activity
import android.content.IntentSender
import com.mobi.pixels.enums.UpdateType
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

fun Activity.updateApp(updateType: UpdateType, onCancel: ((onCancel:Boolean) -> Unit)? = null) {
    val type = if (updateType == UpdateType.Flexible) 0 else 1
    val appUpdateManager = AppUpdateManagerFactory.create(this)

    val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            showCompleteUpdate()
        }
        if (state.installStatus() == InstallStatus.CANCELED) {
            onCancel?.invoke(true)
       }
    }

    appUpdateManager.appUpdateInfo.addOnSuccessListener { result ->
        if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            try {
                if (type == AppUpdateType.FLEXIBLE) {
                    appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE, this, 100)
                } else if (type == AppUpdateType.IMMEDIATE) {
                    appUpdateManager?.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, this, 100)
                }
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        }
        appUpdateManager.registerListener(installStateUpdatedListener)
    }

}

private fun Activity.showCompleteUpdate() {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), "New update is ready!", Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction("Install") {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.completeUpdate()
    }
    snackBar.show()
}