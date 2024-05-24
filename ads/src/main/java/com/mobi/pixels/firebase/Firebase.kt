package com.mobi.pixels.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.messaging.FirebaseMessaging
import com.mobi.pixels.firebase.Messaging.Companion.channelId

fun fireEvent(name: String) {
    Firebase.analytics.logEvent(name.trim()) {
        param(name, name)
    }
}

fun Context.initializeFirebaseMessaging(subscribeToTopic:String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(
                subscribeToTopic,
                subscribeToTopic,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(channel)
    }
    FirebaseMessaging.getInstance().subscribeToTopic(subscribeToTopic)
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        }

    channelId = subscribeToTopic
    startService(
        Intent(this, Messaging::class.java)
    )
}