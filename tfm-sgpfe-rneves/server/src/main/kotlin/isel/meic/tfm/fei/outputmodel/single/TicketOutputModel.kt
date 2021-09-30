package isel.meic.tfm.fei.outputmodel.single

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.resource.BaseResource

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Represents the OutputModel for the Ticket entity
 */
@Siren4JEntity(entityClass = ["ticket"])
class TicketOutputModel : BaseResource {

    val ticketId: Int
    val ticketLetter: String
    val ticketNumber : Int
    val queueId: Int
    val name : String

    //todo remove the JsonProperty - i don't need it when it's a output model. i think
    @JsonCreator
    constructor(@JsonProperty("ticketId") ticketId: Int,
                @JsonProperty("ticketLetter") ticketLetter: String,
                @JsonProperty("ticketNumber") ticketNumber: Int,
                @JsonProperty("queueId") queueId: Int,
                @JsonProperty("name") name : String) {
        this.ticketId = ticketId
        this.ticketLetter = ticketLetter
        this.ticketNumber = ticketNumber
        this.queueId = queueId
        this.name = name
    }
}