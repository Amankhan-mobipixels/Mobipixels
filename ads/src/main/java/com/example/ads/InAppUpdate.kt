package com.example.ads

import android.app.Activity
import android.content.IntentSender
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability


fun Activity.updateAPP(updateType: UpdateType) {
    val type = if (updateType == UpdateType.Flexible) 0 else 1
    val appUpdateManager = AppUpdateManagerFactory.create(this)

    val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            showCompleteUpdate()
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