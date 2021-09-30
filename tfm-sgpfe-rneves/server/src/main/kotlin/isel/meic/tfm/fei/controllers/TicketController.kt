package isel.meic.tfm.fei.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.code.siren4j.component.Entity
import com.google.code.siren4j.converter.ReflectingConverter
import isel.meic.tfm.fei.contract.services.ITicketService
import isel.meic.tfm.fei.dto.SubscribeDTO
import isel.meic.tfm.fei.dto.UserIdDTO
import isel.meic.tfm.fei.entities.QueueState
import isel.meic.tfm.fei.entities.Ticket
import isel.meic.tfm.fei.interceptor.ControlAccessInterceptor
import isel.meic.tfm.fei.outputmodel.collection.QueueStateCollectionOutputModel
import isel.meic.tfm.fei.outputmodel.collection.TicketCollectionOutputModel
import isel.meic.tfm.fei.outputmodel.single.*
import isel.meic.tfm.fei.utils.Constants
import isel.meic.tfm.fei.utils.Utils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@CrossOrigin
@RestController
@RequestMapping("/ticket")
class TicketController @Autowired constructor(private val ticketService: ITicketService) {

    companion object {
        fun adaptTicketToOutputModel(ticket: Ticket): TicketOutputModel {
            return TicketOutputModel(ticket.id, ticket.letter, ticket.number, ticket.serviceId, ticket.name)
        }

        fun adaptServicePostOfficeQueueStateToOutputModel(queueState: QueueState): QueueStateOutputModel {
            return QueueStateOutputModel(
                queueState.stateNumber,
                queueState.letter,
                queueState.attendedNumber,
                queueState.queueId,
                queueState.name,
                queueState.forecast
            )
        }
    }

    private val log = LoggerFactory.getLogger(TicketController::class.java)

    init {
        log.info("Ticket controller was constructed")
    }

    @RequestMapping(value = ["/{queueid}"], method = [RequestMethod.GET], produces = [Constants.MediaType.JSON_SIREN])
    @ResponseBody
    fun getCurrentTicket(@PathVariable("queueid") ticketID: Int): HttpEntity<Entity> {
        val ticket = adaptTicketToOutputModel(ticketService.getCurrentTicketNumber(ticketID)!!)
        //todo verify if not null
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(ticket)))
    }

    //todo change service to query string -> ?service={servid}
    @RequestMapping(
        value = ["/postoffice/{postid}"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getPostOfficeTickets(@PathVariable("postid") postOfficeId: Int): HttpEntity<Entity> {
        val tickets = ticketService.getServiceTickets(postOfficeId).map(Companion::adaptTicketToOutputModel)
        val ticketsCollectionOM = TicketCollectionOutputModel(tickets)
        return ResponseEntity.ok(
            Utils.removeSirenClassFromJson(
                ReflectingConverter.newInstance().toEntity(ticketsCollectionOM)
            )
        )
    }

    //TODO change the value
    @RequestMapping(
        value = ["/postoffice/{postid}/states"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getPostOfficeTicketDetails(@PathVariable("postid") postOfficeId: Int): HttpEntity<Entity> {
        val queues = ticketService.getPostOfficeQueueStates(postOfficeId)
            .map(Companion::adaptServicePostOfficeQueueStateToOutputModel)
        val queueStateCollectionOM = QueueStateCollectionOutputModel(queues)
        return ResponseEntity.ok(
            Utils.removeSirenClassFromJson(
                ReflectingConverter.newInstance().toEntity(queueStateCollectionOM)
            )
        )
    }

    @RequestMapping(
        value = ["/{ticketId}/take"],
        method = [RequestMethod.POST],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    fun takeTicket(
        @PathVariable("ticketId") ticketID: Int,
        @RequestBody body: String
    ): HttpEntity<Entity> {
        val userIdParams = ObjectMapper().readValue(body, UserIdDTO::class.java)
        val ticket = ticketService.takeTicket(ticketID, userIdParams.id)
        if (ticket != null)
            return ResponseEntity.ok(
                Utils.removeSirenClassFromJson(
                    ReflectingConverter.newInstance().toEntity(
                        adaptServicePostOfficeQueueStateToOutputModel(ticket)
                    )
                )
            )

        val errorPair = Constants.ErrorType.TICKET_NOT_AVAILABLE
        val error = ErrorOutputModel(errorPair.first, errorPair.second)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(error)))
    }

    @RequestMapping(value = ["/subscribe"], method = [RequestMethod.POST])
    fun subscribeForUpdates(@RequestBody body: String): HttpEntity<Entity> {
        val subscribeParams = ObjectMapper().readValue(body, SubscribeDTO::class.java)
        ticketService.subscribeForUpdates(subscribeParams.id, subscribeParams.userId)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/postoffice/subscribe"], method = [RequestMethod.POST])
    fun subscribeForQueuesUpdates(@RequestBody body: String): HttpEntity<Entity> {
        val subscribeParams = ObjectMapper().readValue(body, SubscribeDTO::class.java)
        ticketService.subscribeForPostOfficeUpdates(subscribeParams.id, subscribeParams.userId)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/unsubscribe"], method = [RequestMethod.POST])
    fun unsubscribeForUpdates(@RequestBody body: String): HttpEntity<Entity> {
        val subscribeParams = ObjectMapper().readValue(body, SubscribeDTO::class.java)
        ticketService.unsubscribeForUpdates(subscribeParams.id, subscribeParams.userId)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/postoffice/unsubscribe"], method = [RequestMethod.POST])
    fun unsubscribeForQueuesUpdates(@RequestBody body: String): HttpEntity<Entity> {
        val subscribeParams = ObjectMapper().readValue(body, SubscribeDTO::class.java)
        ticketService.unsubscribeForPostOfficeUpdates(subscribeParams.id, subscribeParams.userId)
        return ResponseEntity.ok().build()
    }

    //******************* For external services

    @RequestMapping(value = ["/{ticketId}"], method = [RequestMethod.PUT], produces = [Constants.MediaType.JSON_SIREN])
    fun ticketAttended(@PathVariable("ticketId") ticketID: Int): HttpEntity<Entity> {
        val ticket = ticketService.ticketAttended(ticketID)
        //todo return proper response to the service
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(
        value = ["/postoffice/{postid}/battended"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getPostOfficeBeingAttendedTickets(
        @PathVariable("postid") postOfficeId: Int, @RequestHeader(
            ControlAccessInterceptor.AUTHORIZATION
        ) authorization: String
    ): HttpEntity<Entity> {
        val accessToken = authorization.removePrefix(ControlAccessInterceptor.BEARER_PREFIX)
        val tickets =
            ticketService.getPostOfficeBeingAttendedTickets(postOfficeId, accessToken).map(Companion::adaptTicketToOutputModel)
        val ticketsCollectionOM = TicketCollectionOutputModel(tickets)
        return ResponseEntity.ok(
            Utils.removeSirenClassFromJson(
                ReflectingConverter.newInstance().toEntity(ticketsCollectionOM)
            )
        )
    }
}