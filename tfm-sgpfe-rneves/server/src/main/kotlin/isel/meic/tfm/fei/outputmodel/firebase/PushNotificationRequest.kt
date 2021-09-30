package isel.meic.tfm.fei.outputmodel.firebase

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
class PushNotificationRequest {
    private var title: String? = null
    private var message: String? = null
    private var topic: String? = null
    private var token: String? = null

    constructor(title: String, messageBody: String, topicName: String) {
        this.title = title
        this.message = messageBody
        this.topic = topicName
    }

    fun getTitle(): String? = title

    fun setTitle(title: String) {
        this.title = title
    }

    fun getMessage(): String? = message

    fun setMessage(message: String) {
        this.message = message
    }

    fun getTopic(): String? = topic

    fun setTopic(topic: String) {
        this.topic = topic
    }

    fun getToken(): String? = token

    fun setToken(token: String) {
        this.token = token
    }
}