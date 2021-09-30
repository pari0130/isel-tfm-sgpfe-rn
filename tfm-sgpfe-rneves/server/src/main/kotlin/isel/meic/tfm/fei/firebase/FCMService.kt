package isel.meic.tfm.fei.firebase

import com.google.firebase.messaging.*
import isel.meic.tfm.fei.outputmodel.firebase.PushNotificationRequest
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ExecutionException

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
@Service
class FCMService {
    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageToToken(request: PushNotificationRequest) {
        val message = getPreconfiguredMessageToToken(request)
        val response = sendAndGetResponse(message)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageToToken(request: PushNotificationRequest, data: Map<String, String>) {
        val message = getPreconfiguredMessageToToken(request, data)
        val response = sendAndGetResponse(message)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendDataMessageToToken(request: PushNotificationRequest, data: Map<String, String>) {
        val message = getPreconfiguredDataMessageToToken(request, data)
        val response = sendAndGetResponse(message)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    private fun sendAndGetResponse(message: Message): String {
        return FirebaseMessaging.getInstance().sendAsync(message).get()
    }

    private fun getAndroidConfig(title: String, body: String): AndroidConfig {
        return AndroidConfig.builder()
            .setTtl(Duration.ofMinutes(5).toMillis())
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(
                AndroidNotification.builder()
                    .setSound(NotificationParameter.SOUND.getValue())
                    .setColor(NotificationParameter.COLOR.getValue())
                    .setTitle(title)
                    .setBody(body)
                    .setClickAction("FEI_NOTIFICATION_CLICK")
                    .build()
            ).build()
    }

    private fun getPreconfiguredMessageToToken(request: PushNotificationRequest): Message {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
            .build()
    }

    private fun getPreconfiguredDataMessageToToken(
        request: PushNotificationRequest,
        data: Map<String, String>
    ): Message {
        return Message.builder()
            .putAllData(data)
            .setToken(request.getToken())
            .build()
    }

    private fun getPreconfiguredMessageToToken(request: PushNotificationRequest, data: Map<String, String>): Message {
        return getPreconfiguredMessageBuilder(request)
            .putAllData(data)
            .setToken(request.getToken())
            .build()
    }

    private fun getPreconfiguredMessageBuilder(request: PushNotificationRequest): Message.Builder {
        val androidConfig = getAndroidConfig(request.getTitle()!!, request.getMessage()!!)
        val notification =
            Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getMessage())
        return Message.builder()
            .setAndroidConfig(androidConfig)
            .setNotification(notification.build())
    }

    private enum class NotificationParameter(private val value: String) {
        SOUND("default"),
        COLOR("#00574B");

        fun getValue(): String = this.value
    }
}