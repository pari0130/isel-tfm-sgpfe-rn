package isel.meic.tfm.fei.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.QueueState
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 * TODO Improve this
 */
class LiveUpdateService : Service() {

    private var executorService: ExecutorService? = null

    companion object {
        private const val SERVICE_ID = 10001
        private const val TAG = "LIVE_UPDATE_SERVICE"
        const val START_FOREGROUND_SERVICE = "start_foreground_service"
        const val STOP_FOREGROUND_SERVICE = "stop_foreground_service"
        const val UPDATE_FOREGROUND_SERVICE = "update_foreground_service"
        private const val UPDATE_SERVICE_FORECAST = "update_service_forecast"
        const val QUEUE_STATE_EXTRA = "queue_state_extra"
    }

    override fun onCreate() {
        Log.d(TAG, "LiveUpdate onCreate()")
        super.onCreate()
        executorService = Executors.newFixedThreadPool(1)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var userTicketNumber: Int? = null
    private var forecast: Int = 0
    private var queueState: QueueState? = null
    private var timer: Timer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            when (intent.action) {
                START_FOREGROUND_SERVICE -> {
                    startForegroundService(intent.getSerializableExtra(QUEUE_STATE_EXTRA) as QueueState?)
                }
                STOP_FOREGROUND_SERVICE -> stopForegroundService()
                UPDATE_FOREGROUND_SERVICE -> updateForegroundService(
                    intent.getSerializableExtra(QUEUE_STATE_EXTRA) as QueueState?, true
                )
                UPDATE_SERVICE_FORECAST -> updateForegroundService(
                    intent.getSerializableExtra(QUEUE_STATE_EXTRA) as QueueState?, false
                )
                else -> stopSelf()
            }
        }
        return START_STICKY
    }

    private fun startForegroundService(queueState: QueueState?) {
        Log.d(TAG, "LiveUpdate startForegroundService()")
        (application as FEIApplication).setLiveUpdateServiceStarted(true)
        val notification = createNotification(queueState, true)
        startForeground(SERVICE_ID, notification)
        userTicketNumber = queueState?.stateNumber
        this.queueState = queueState

        executorService?.execute {
            timer = Timer()
            timer?.scheduleAtFixedRate(UpdateForecastTask(), 60000, 60000)
        }
    }

    private fun stopForegroundService() {
        Log.d(TAG, "LiveUpdate stopForegroundService()")
        userTicketNumber = null
        queueState = null
        (application as FEIApplication).setLiveUpdateServiceStarted(false)
        stopForeground(true)
        stopSelf()
    }

    private fun updateForegroundService(queueState: QueueState?, newForecast: Boolean) {
        Log.d(TAG, "LiveUpdate updateForegroundService()")
        try {
            //todo verify if queueState id matched the one in memory
            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notification = createNotification(queueState, newForecast)
            mNotificationManager.notify(SERVICE_ID, notification)
        } catch (ignored: Exception) {
        }
    }

    private fun createNotification(queueState: QueueState?, newForecast: Boolean): Notification {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                packageName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = packageName
            channel.lockscreenVisibility =
                Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }
        val notificationBuilder = NotificationCompat.Builder(application, packageName)

        synchronized(forecast) {
            forecast = if (newForecast) queueState?.forecast ?: 0 else forecast
            //if todo newForecast restart timer
        }
        val hour = forecast.div(60)
        val minutes = forecast.rem(60)
        val forecast = if (hour > 0) "${hour}hr:${minutes}min" else "$minutes minutes"

        return notificationBuilder
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setSmallIcon(R.drawable.appicon)
            //.setContentTitle("FEI LiveUpdate Service ${System.currentTimeMillis()}")
            .setContentTitle("*** ${queueState?.name} ***")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Your Ticket: ${userTicketNumber ?: queueState?.stateNumber} \nBeing Attended: ${queueState?.attendedNumber} \t Forecast: $forecast")
            )
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setProgress(100, 0, true)
            .build()
    }

    override fun onDestroy() {
        Log.d(TAG, "LiveUpdate onDestroy()")
        timer?.cancel()
        timer?.purge()
        timer = null
        executorService?.shutdown()
        executorService = null
        super.onDestroy()
    }

    inner class UpdateForecastTask : TimerTask() {
        override fun run() {
            if (forecast > 0) {
                synchronized(forecast) {
                    forecast--
                    queueState?.forecast = forecast
                }
                val intent = Intent(this@LiveUpdateService, LiveUpdateService::class.java)
                intent.action = UPDATE_SERVICE_FORECAST
                intent.putExtra(QUEUE_STATE_EXTRA, queueState)
                startService(intent)
            } else timer?.cancel()
        }
    }
}