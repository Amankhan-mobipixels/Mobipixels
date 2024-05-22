package com.mobi.pixels.firebase

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class Messaging : FirebaseMessagingService() {

 companion object {
  var icon: Int? = null
 }

 override fun onMessageReceived(remoteMessage: RemoteMessage) {
  super.onMessageReceived(remoteMessage)
  showNotification(remoteMessage.notification!!.title, remoteMessage.notification!!.body)
 }

 @SuppressLint("MissingPermission")
 private fun showNotification(title: String?, message: String?) {
  val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "VideoDownloader")
   .setContentTitle(title)
   .setSmallIcon(icon!!)
   .setAutoCancel(true)
   .setContentText(message)
  val managerCompat = NotificationManagerCompat.from(this)
  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
   return
  }
  managerCompat.notify(999, builder.build())
 }
}