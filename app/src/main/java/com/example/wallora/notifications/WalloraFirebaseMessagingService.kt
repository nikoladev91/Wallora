package com.example.wallora.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wallora.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class WalloraFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(
            "WALLORA_FCM",
            "FCM token: $token"
        )
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(
            "WALLORA_FCM",
            "MESSAGE RECEIVED!"
        )

        val title = message.notification?.title
            ?: "Wallora"

        val body = message.notification?.body
            ?: "Nowe tapety są już dostępne ✨"

        showNotification(
            title = title,
            body = body
        )
    }

    private fun showNotification(
        title: String,
        body: String
    ) {
        val channelId = "wallora_updates_v2"

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "Wallora updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description =
                    "Notifications about new wallpapers and collections"

                enableVibration(true)
            }

            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            this,
            channelId
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationsEnabled =
            NotificationManagerCompat
                .from(this)
                .areNotificationsEnabled()

        Log.d(
            "WALLORA_FCM",
            "Notifications enabled: $notificationsEnabled"
        )

        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )

        Log.d(
            "WALLORA_FCM",
            "NOTIFICATION POSTED!"
        )
    }
}