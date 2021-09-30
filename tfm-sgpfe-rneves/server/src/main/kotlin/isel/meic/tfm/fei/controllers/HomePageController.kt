package isel.meic.tfm.fei.controllers

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import isel.meic.tfm.fei.dto.UserDTO
import isel.meic.tfm.fei.outputmodel.firebase.PushNotificationRequest
import isel.meic.tfm.fei.services.PushNotificationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
@RestController
@RequestMapping
class HomePageController @Autowired constructor(private val pushNotificationService: PushNotificationService) {

    private val log = LoggerFactory.getLogger(HomePageController::class.java)

    init {
        log.info("Homepage controller was constructed")
    }

    @GetMapping("/")
    fun getRoot(): String {
        return "SGPFE - TFM - RN Server Running!"
    }

    //to test push notification... todo delete this
    /*@PostMapping("/notify")
    fun sendNotification(){
        val req = PushNotificationRequest("Title", "Body", "Topic")
        req.setToken( "cFBXwi9xnWQ:APA91bHR2kG44lPkNh4xHOzphnD4aHC7Pc29JfiD0rHLhDz6E2ezGDRTZLViNQ8hTBznsyRoVpoyIR5el9Ieuec4is7cJzOvHU8PQjlCr0Xbzsab4zI_pH8pExM0yAo0wWy5tvvGtdXa")
        val data : MutableMap<String, String> = HashMap()
        data["ticket_id"] = "1"
        data["ticket_number"] = "2"
        data["ticket_letter"] = "A"
        data["ticket_service"] = "1"

        pushNotificationService.sendPushNotificationToToken(req, data)
    }
*/
    //to test push notification... todo delete this
    @PostMapping("/notify")
    fun sendNotification2(@RequestBody body: String){
        val userParams = ObjectMapper().readValue(body, TokenDTO::class.java)
        val req = PushNotificationRequest("Title", "Body", "Topic")
        req.setToken(userParams.token)
        pushNotificationService.sendPushNotificationToToken(req)
    }
}


@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenDTO(@JsonProperty("token") val token: String)