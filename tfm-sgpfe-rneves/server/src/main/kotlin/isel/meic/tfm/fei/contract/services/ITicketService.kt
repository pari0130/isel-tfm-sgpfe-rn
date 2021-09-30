package isel.meic.tfm.fei.contract.services

import isel.meic.tfm.fei.entities.QueueState
import isel.meic.tfm.fei.entities.Ticket

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface ITicketService {

    //NOTE: for now the identifiers are all Ints. Maybe change in the future to more complex id structure

    //********************** for the clients **********************

    fun takeTicket(ticketId: Int, userId: Int): QueueState?

    //for this one maybe add a service point
    fun getCurrentTicketNumber(ticketId: Int): Ticket?

    fun exchangeTicket(userId: Int, serviceId: Int): Ticket

    fun cancelTicket(ticketId: Int)

    fun getServiceTickets(serviceId: Int): Collection<Ticket>

    fun getPostOfficeQueueStates(postOfficeId: Int): Collection<QueueState>

    fun subscribeForUpdates(ticketId: Int, userId: Int)

    fun subscribeForPostOfficeUpdates(postId: Int, userId: Int)

    fun unsubscribeForUpdates(ticketId: Int, userId: Int)

    fun unsubscribeForPostOfficeUpdates(postId: Int, userId: Int)

    fun notifyNextInLine(ticketId: Int, ticketNumber: Int)

    //********************** for external services **********************
    /*fun resetTicket(ticketId: Int)

    fun makeTicketUnavailable(ticketId: Int)*/

    fun ticketAttended(ticketId: Int)

    fun getPostOfficeBeingAttendedTickets(postId: Int, accessToken : String): Collection<Ticket>
}