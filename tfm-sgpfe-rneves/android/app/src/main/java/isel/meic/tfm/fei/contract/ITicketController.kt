package isel.meic.tfm.fei.contract

import com.android.volley.VolleyError
import isel.meic.tfm.fei.inputmodel.queue.QueueStateInputModel
import isel.meic.tfm.fei.inputmodel.queue.QueuesStatesInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketsInputModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface ITicketController {

    fun getServiceTickets(
        serviceId: Int,
        url: String?,
        token: String?,
        success: (TicketsInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun getPostOfficeQueuesStates(
        postOfficeId: Int,
        url: String?,
        token: String?,
        success: (QueuesStatesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun takeTicket(
        ticketId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: (QueueStateInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun getCurrentTicketState(
        queueId: Int,
        url: String?,
        token: String?,
        success: (TicketInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun subscribeForUpdates(
        ticketId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun subscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun unsubscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        url: String?,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    )
}