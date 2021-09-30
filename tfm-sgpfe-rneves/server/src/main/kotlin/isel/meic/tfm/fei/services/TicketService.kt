package isel.meic.tfm.fei.services

import isel.meic.tfm.fei.contract.dal.ISessionDAL
import isel.meic.tfm.fei.contract.dal.ITicketDAL
import isel.meic.tfm.fei.contract.services.ITicketService
import isel.meic.tfm.fei.entities.QueueState
import isel.meic.tfm.fei.entities.Ticket
import isel.meic.tfm.fei.outputmodel.firebase.PushNotificationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Service
class TicketService @Autowired constructor(
    private val ticketDAL: ITicketDAL,
    private val pushNotificationService: PushNotificationService,
    private val sessionDAL: ISessionDAL
) : ITicketService {

    override fun takeTicket(
        ticketId: Int,
        userId: Int
    ): QueueState? { //todo verify if there is a number available
        val ticket = ticketDAL.takeTicket(ticketId, userId)
        if (ticket != null) {
            notifyObservers(ticket, "ticket_for_list")
            notifyNextInLine(ticketId, ticket.stateNumber)//todo remove this from here. should be in ticketAttended
        }
        return ticket
    }

    override fun getCurrentTicketNumber(ticketId: Int): Ticket? {
        return this.ticketDAL.getCurrentTicketState(ticketId)
    }

    override fun exchangeTicket(userId: Int, serviceId: Int): Ticket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelTicket(ticketId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServiceTickets(serviceId: Int): Collection<Ticket> {
        return this.ticketDAL.getServiceTickets(serviceId)
    }

    override fun getPostOfficeQueueStates(postOfficeId: Int): Collection<QueueState> {
        return ticketDAL.getPostOfficeQueueStates(postOfficeId)
    }

    override fun ticketAttended(ticketId: Int) {
        if (this.ticketDAL.ticketAttended(ticketId)) {
            val queueState = this.ticketDAL.getQueueState(ticketId)
            if (queueState != null) {
                //TEMP
                notifyTurn(ticketId, queueState.attendedNumber)
                notifyObservers(queueState, "ticket_for_list")
            }
            //notifyObservers(ticket, "ticket_attended")

        }
    }

    override fun getPostOfficeBeingAttendedTickets(postId: Int, accessToken : String): Collection<Ticket> {
        if(postId != 0)
            return this.ticketDAL.getPostOfficeBeingAttendedTickets(postId)
        val userId = sessionDAL.getUserIdFromAccessToken(accessToken)
        return ticketDAL.getPostOfficeBeingAttendedTicketsForUser(userId)
    }

    override fun subscribeForUpdates(ticketId: Int, userId: Int) {
        return this.ticketDAL.registerForUpdates(ticketId, userId)
    }

    override fun subscribeForPostOfficeUpdates(postId: Int, userId: Int) {
        return this.ticketDAL.registerForPostOfficeUpdates(postId, userId)
    }

    override fun unsubscribeForUpdates(ticketId: Int, userId: Int) {
        return this.ticketDAL.unregisterForUpdates(ticketId, userId)
    }

    override fun unsubscribeForPostOfficeUpdates(postId: Int, userId: Int) {
        return this.ticketDAL.unregisterForPostOfficeUpdates(postId, userId)
    }

    @Async
    override fun notifyNextInLine(ticketId: Int, ticketNumber: Int) {
        val token = this.ticketDAL.getNextInLine(ticketId, ticketNumber)
        if (token != null) {
            val req = PushNotificationRequest("Turn", "You are next!", "Ticket")
            req.setToken(token)
            pushNotificationService.sendPushNotificationToToken(req)
        }
    }

    @Async
    fun notifyTurn(ticketId: Int, ticketNumber: Int) {
        val token = this.ticketDAL.getTurnToken(ticketId, ticketNumber)
        if (token != null) {
            val req = PushNotificationRequest("Turn", "Your turn!", "Ticket")
            req.setToken(token)
            pushNotificationService.sendPushNotificationToToken(req, createTurnDataMap())
        }
        //todo remove observer??
    }

    //todo an unsubscribe method

    @Async
    fun notifyObservers(queueState: QueueState, type: String) {
        val set = this.ticketDAL.getTicketObserversTokens(queueState.queueId)
        val queuesSet = this.ticketDAL.getPostOfficeObserversTokens(queueState.postId)
        val map = createQueueStateDataMap(queueState, type)
        for (obs in set) {
            val req = PushNotificationRequest("Update", type, "Ticket")
            req.setToken(obs)
            pushNotificationService.sendDataPushNotificationToToken(req, map)
        }

        for (obs in queuesSet) {
            val req = PushNotificationRequest("Update", type, "Ticket")
            req.setToken(obs)
            pushNotificationService.sendDataPushNotificationToToken(req, map)
        }
    }

    private fun createQueueStateDataMap(queueState: QueueState, type: String): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map["type"] = type

        map["letter"] = queueState.letter
        map["stateNumber"] = queueState.stateNumber.toString()
        map["attendedNumber"] = queueState.attendedNumber.toString()
        map["queueId"] = queueState.queueId.toString()
        map["name"] = queueState.name
        map["forecast"] = queueState.forecast.roundToInt().toString()
        return map
    }

    private fun createTurnDataMap(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map["type"] = "your_turn"
        return map
    }

    /*
    private fun createDataMap(ticket: Ticket, type: String): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map["type"] = type
        map["ticket_id"] = ticket.id.toString()
        map["ticket_letter"] = ticket.letter
        map["ticket_number"] = ticket.number.toString()
        map["ticket_service"] = ticket.serviceId.toString()
        map["ticket_name"] = ticket.name
        return map
    }*/
}