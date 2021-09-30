package isel.meic.tfm.fei.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.QueueState
import isel.meic.tfm.fei.model.Ticket
import isel.meic.tfm.fei.model.TicketAttended
import isel.meic.tfm.fei.presentation.BaseActivity
import isel.meic.tfm.fei.service.LiveUpdateService

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * https://medium.com/android-dev-hacks/firebase-cloud-messaging-772c9680c97e
 *
 * TODO remove tools:targetApi="n" in Android Manifest
 * https://medium.com/@singh.pankajmca/fcm-integration-with-spring-boot-to-send-push-notification-from-server-side-1091cfd2cacf
 * TODO try sending only data in the push notification
 *
 * https://www.raywenderlich.com/9227276-firebase-cloud-messaging-for-android-sending-push-notifications
 * TODO cleanup
 */
class FEIFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val NOTIFICATION_ID = 101
        private const val REQUEST_CODE = 101
    }

    override fun handleIntent(intent: Intent) {
        Log.d("POMPS FCM", "FEIFirebaseMessagingService handleIntent Pomps")

        super.handleIntent(intent)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val app = (application as FEIApplication)

        Log.d("POMPS FCM", "FEIFirebaseMessagingService onMessageReceived Pomps")

        //TODO FIX THIS. This can be used with the app paused
        if (app.isApplicationActive() && remoteMessage.data.isNotEmpty()) {
            if (remoteMessage.data.containsKey("type")) {
                when (remoteMessage.data["type"]) {
                    "ticket" -> {
                        app.repo.updateTicket(
                            Ticket(
                                remoteMessage.data["ticket_id"]!!.toInt(),
                                remoteMessage.data["ticket_letter"]!!,
                                remoteMessage.data["ticket_number"]!!.toInt(),
                                remoteMessage.data["ticket_service"]!!.toInt(),
                                remoteMessage.data["ticket_name"]!!
                            )
                        )
                    }
                    "ticket_attended" -> {
                        app.repo.updateTicket(
                            TicketAttended(
                                remoteMessage.data["ticket_id"]!!.toInt(),
                                remoteMessage.data["ticket_letter"]!!,
                                remoteMessage.data["ticket_number"]!!.toInt(),
                                remoteMessage.data["ticket_service"]!!.toInt(),
                                remoteMessage.data["ticket_name"]!!
                            )
                        )
                    }
                    "ticket_for_list" -> {
                        //val queueStateDto = ObjectMapper().readValue<QueueStateDto>(remoteMessage.data, QueueStateDto::class.java)
                        //app.repo.updateTicketForList(Ticket(remoteMessage.data["ticket_id"]!!.toInt(), remoteMessage.data["ticket_letter"]!!, remoteMessage.data["ticket_number"]!!.toInt(), remoteMessage.data["ticket_service"]!!.toInt(), remoteMessage.data["ticket_name"]!!))
                        val gson = Gson()
                        val toJson = gson.toJson(remoteMessage.data)
                        val queueState = gson.fromJson(toJson, QueueState::class.java)
                        app.repo.updateQueueStateForList(queueState)

                        //TODO fix the bug in the server
                        if (queueState.attendedNumber == queueState.stateNumber) {
                            val intent = Intent(this, LiveUpdateService::class.java)
                            intent.action = LiveUpdateService.STOP_FOREGROUND_SERVICE
                            startService(intent)
                            sendNotification("Your Turn!")
                        } else if (queueState.attendedNumber + 1 == queueState.stateNumber) {
                            val intent = Intent(this, LiveUpdateService::class.java)
                            intent.action = LiveUpdateService.UPDATE_FOREGROUND_SERVICE
                            intent.putExtra(LiveUpdateService.QUEUE_STATE_EXTRA, queueState)
                            startService(intent)
                            sendNotification("You are next!")
                        } else {
                            //todo call live update service
                            val intent = Intent(this, LiveUpdateService::class.java)
                            intent.action = LiveUpdateService.UPDATE_FOREGROUND_SERVICE
                            intent.putExtra(LiveUpdateService.QUEUE_STATE_EXTRA, queueState)
                            startService(intent)
                        }
                    }
                    "your_turn" -> { //todo test
                        val intent = Intent(this, LiveUpdateService::class.java)
                        intent.action = LiveUpdateService.STOP_FOREGROUND_SERVICE
                        startService(intent)
                        sendNotification("Your Turn")
                    }
                }
            }
            return
        } else {
            //todo remove
            sendNotification("test")

        }

        // Check if message contains a notification payload.
        /*remoteMessage.notification?.let {
            data["title"] = it.title!!
            data.put("alert", it.body!!)
        }
*/
    }

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
    }

    private fun processMessage() {
        // TODO: Implement this method to update data or show notification
    }

    private fun sendNotification(message: String) {
        //TODO fix this
        val intent = Intent(this, BaseActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, REQUEST_CODE, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                packageName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = packageName
            notificationManager.createNotificationChannel(channel)
        }
        notificationBuilder = NotificationCompat.Builder(application, packageName)

        notificationBuilder.setSmallIcon(R.drawable.appicon)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}