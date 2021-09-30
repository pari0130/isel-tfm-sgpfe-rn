package isel.meic.tfm.fei.controller

import com.android.volley.VolleyError
import isel.meic.tfm.fei.contract.ITicketController
import isel.meic.tfm.fei.inputmodel.queue.QueueStateInputModel
import isel.meic.tfm.fei.inputmodel.queue.QueuesStatesInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketsInputModel
import isel.meic.tfm.fei.webapi.TicketApi

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class TicketController(private val ticketApi: TicketApi) : ITicketController {

    override fun getServiceTickets(
        serviceId: Int,
        url: String?,
        token: String?,
        success: (TicketsInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.getPostOfficeTickets(serviceId, url, token, success, onError)
    }

    override fun getPostOfficeQueuesStates(
        postOfficeId: Int,
        url: String?,
        token: String?,
        success: (QueuesStatesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.getPostOfficeQueuesStates(postOfficeId, url, token, success, onError)
    }

    override fun takeTicket(
        ticketId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: (QueueStateInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.takeTicket(ticketId, userId, url, token, success, onError)
    }

    override fun getCurrentTicketState(
        queueId: Int,
        url: String?,
        token: String?,
        success: (TicketInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.getCurrentTicketState(queueId, url, token, success, onError)
    }

    override fun subscribeForUpdates(
        ticketId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.subscribeForUpdates(ticketId, userId, url, token, success, onError)
    }

    override fun subscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.subscribeForPostOfficeTicketsUpdates(postOfficeId, userId, url, token, success, onError)
    }

    override fun unsubscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketApi.unsubscribeForPostOfficeTicketsUpdates(postOfficeId, userId, url, token, success, onError)
    }
}