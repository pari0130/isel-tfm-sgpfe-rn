package isel.meic.tfm.fei.contract.dal

import isel.meic.tfm.fei.entities.QueueState
import isel.meic.tfm.fei.entities.QueueType
import isel.meic.tfm.fei.entities.Ticket

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface ITicketDAL {

    fun getCurrentTicketState(ticketId: Int): Ticket?
    fun getQueueState(queueId:Int) : QueueState?
    fun getServiceTickets(postOfficeId: Int): Collection<Ticket>
    fun getPostOfficeQueueStates(postOfficeId: Int): Collection<QueueState>
    fun takeTicket(ticketId: Int, userId: Int): QueueState?
    fun registerForUpdates(queueId: Int, userId: Int)
    fun registerForPostOfficeUpdates(postOfficeId: Int, userId : Int)
    fun getTicketObservers(ticketId: Int) : Collection<Int>
    fun getPostOfficeObservers(postOfficeId: Int) : Collection<Int>

    fun unregisterForUpdates(queueId: Int, userId: Int)
    fun unregisterForPostOfficeUpdates(postOfficeId: Int, userId: Int)

    fun getTicketObserversTokens(ticketId: Int) : Collection<String>
    fun getPostOfficeObserversTokens(postOfficeId: Int) : Collection<String>

    //todo maybe add the serviceId too
    fun getNextInLine(ticketId: Int, ticketNumber: Int) : String?
    fun getTurnToken(ticketId: Int, ticketNumber: Int) : String?

    fun getQueueTypes() : Collection<QueueType>
    fun deleteQueue(id: Int) : Boolean

    /********************************************** for external service **********************************************/

    //for external service //maybe return a boolean or some object saying if there is someone waiting in line
    fun ticketAttended(ticketId: Int) : Boolean
    fun getPostOfficeBeingAttendedTickets(postOfficeId: Int): Collection<Ticket>
    fun getPostOfficeBeingAttendedTicketsForUser(userId: Int): Collection<Ticket>
}