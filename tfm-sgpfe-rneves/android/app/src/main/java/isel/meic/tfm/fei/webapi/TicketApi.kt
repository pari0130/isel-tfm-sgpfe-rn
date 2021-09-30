package isel.meic.tfm.fei.webapi

import com.android.volley.VolleyError
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.inputmodel.queue.QueueStateInputModel
import isel.meic.tfm.fei.inputmodel.queue.QueuesStatesInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketsInputModel
import isel.meic.tfm.fei.utils.network.GetRequest
import isel.meic.tfm.fei.utils.network.PostRequest
import isel.meic.tfm.fei.utils.network.PostRequestWithoutType
import org.json.JSONObject

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 *
 * Makes requests to the API
 */
class TicketApi(private val application: FEIApplication) {

    fun getPostOfficeTickets(
        postOfficeId: Int,
        url: String?,
        token: String?,
        success: (TicketsInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/ticket/postoffice/$postOfficeId"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                TicketsInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    fun getPostOfficeQueuesStates(
        postOfficeId: Int,
        url: String?,
        token: String?,
        success: (QueuesStatesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/ticket/postoffice/$postOfficeId/states"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                QueuesStatesInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    fun getCurrentTicketState(
        queueId: Int,
        url: String?,
        token: String?,
        success: (TicketInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/ticket/$queueId"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                TicketInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    fun takeTicket(
        ticketId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: (QueueStateInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/ticket/$ticketId/take"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        val params = HashMap<String, String>()
        params["id"] = userId.toString()

        application.addToRequestQueue(
            PostRequest(
                completeUrl,
                JSONObject(params as Map<*, *>),
                QueueStateInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    //For now this will be a post //todo encrypt the token
    fun subscribeForUpdates(
        ticketId: Int,
        userId: Int, //this can be obtained by the user using the token
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {

        val params = HashMap<String, String>()
        params["id"] = ticketId.toString()
        params["userId"] = userId.toString()
        val restOfUrl = "/ticket/subscribe"
        val completeUrl =
            if (url != null) url + restOfUrl else application.getUrl() + restOfUrl
        application.addToRequestQueue(
            PostRequestWithoutType(
                completeUrl,
                JSONObject(params as Map<*, *>),
                token,
                { success() },
                { onError(it) })
        )
    }

    fun subscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val params = HashMap<String, String>()
        params["id"] = postOfficeId.toString()
        params["userId"] = userId.toString()
        val restOfUrl = "/ticket/postoffice/subscribe"
        val completeUrl =
            if (url != null) url + restOfUrl else application.getUrl() + restOfUrl
        application.addToRequestQueue(
            PostRequestWithoutType(
                completeUrl,
                JSONObject(params as Map<*, *>),
                token,
                { success() },
                { onError(it) })
        )
    }

    fun unsubscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val params = HashMap<String, String>()
        params["id"] = postOfficeId.toString()
        params["userId"] = userId.toString()
        val restOfUrl = "/ticket/postoffice/unsubscribe"
        val completeUrl =
            if (url != null) url + restOfUrl else application.getUrl() + restOfUrl
        application.addToRequestQueue(
            PostRequestWithoutType(
                completeUrl,
                JSONObject(params as Map<*, *>),
                token,
                { success() },
                { onError(it) })
        )
    }
}