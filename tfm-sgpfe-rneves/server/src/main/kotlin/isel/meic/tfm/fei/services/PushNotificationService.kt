package isel.meic.tfm.fei.services

import isel.meic.tfm.fei.controllers.HomePageController
import isel.meic.tfm.fei.firebase.FCMService
import isel.meic.tfm.fei.outputmodel.firebase.PushNotificationRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * TODO handle the possible exceptions
 */
@Service
class PushNotificationService @Autowired constructor(private val fcmService: FCMService) {

    private val log = LoggerFactory.getLogger(PushNotificationService::class.java)

    fun sendPushNotificationToToken(request: PushNotificationRequest) {
        try {
            fcmService.sendMessageToToken(request)
        } catch (e: InterruptedException) {
        } catch (e: ExecutionException) {
        }
    }

    fun sendPushNotificationToToken(request: PushNotificationRequest, data : Map<String, String>) {
        try {
            fcmService.sendMessageToToken(request, data)
        } catch (e: InterruptedException) {
        } catch (e: ExecutionException) {
            log.error(e.message)
        }
    }

    fun sendDataPushNotificationToToken(request: PushNotificationRequest, data : Map<String, String>) {
        try {
            fcmService.sendDataMessageToToken(request, data)
        } catch (e: InterruptedException) {
            log.error(e.message)
        } catch (e: ExecutionException) {
            log.error(e.message)
        }
    }
}