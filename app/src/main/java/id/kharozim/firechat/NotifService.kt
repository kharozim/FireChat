package id.kharozim.firechat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.kharozim.firechat.utils.Constant
import id.kharozim.firechat.utils.PreferencesHelper

class NotifService : FirebaseMessagingService() {
    private val sharedpref by lazy { PreferencesHelper(applicationContext) }
    private val db by lazy { Firebase.firestore }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sharedpref.token = token

        db.collection(Constant.COLLECTION).document(sharedpref.uid)
            .update(mapOf("token" to token))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage.data)
    }

    private fun showNotification(data: MutableMap<String, String>) {
        val title = data["title"] ?: ""
        val body = data["body"] ?: ""
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.NOTIFICATION_CHANNEL_ID,
                Constant.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, Constant.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(getColor(R.color.teal_700))
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(Constant.NOTIFICATION_ID, notification)
    }

}