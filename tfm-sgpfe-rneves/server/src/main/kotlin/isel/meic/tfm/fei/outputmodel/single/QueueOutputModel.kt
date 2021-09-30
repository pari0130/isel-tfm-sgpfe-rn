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
 * <Description>
 */
@Siren4JEntity(entityClass = ["queue"])
class QueueOutputModel : BaseResource {

    val id: Int
    val name: String
    val activeServers: Int
    val letter: String
    val description: String
    val type: Int
    val maxAvailable: Int
    val servicePostOfficeId: Int
    val tolerance: Boolean

    @JsonCreator
    constructor(
        @JsonProperty("id") id: Int,
        @JsonProperty("name") name: String,
        @JsonProperty("activeServers") activeServers: Int,
        @JsonProperty("type") type: Int,
        @JsonProperty("maxAvailable") maxAvailable: Int,
        @JsonProperty("servicePostOfficeId") servicePostOfficeId: Int,
        @JsonProperty("description") description: String,
        @JsonProperty("letter") letter: String,
        @JsonProperty("tolerance") tolerance: Boolean
    ) {
        this.id = id
        this.name = name
        this.activeServers = activeServers
        this.type = type
        this.maxAvailable = maxAvailable
        this.servicePostOfficeId = servicePostOfficeId
        this.description = description
        this.letter = letter
        this.tolerance = tolerance
    }
}