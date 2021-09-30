package isel.meic.tfm.fei.outputmodel.single

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.resource.BaseResource
import kotlin.math.roundToInt

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Siren4JEntity(entityClass = ["queueState"])
class QueueStateOutputModel : BaseResource {
    val letter: String
    val stateNumber: Int
    val attendedNumber: Int
    val queueId: Int
    val name: String
    val forecast: Int

    @JsonCreator
    constructor(
        @JsonProperty("stateNumber") stateNumber: Int,
        @JsonProperty("letter") letter: String,
        @JsonProperty("attendedNumber") attendedNumber: Int,
        @JsonProperty("queueId") queueId: Int,
        @JsonProperty("name") name: String,
        @JsonProperty("forecast") forecast: Double

    ) {
        this.stateNumber = stateNumber
        this.letter = letter
        this.attendedNumber = attendedNumber
        this.queueId = queueId
        this.name = name
        this.forecast = forecast.roundToInt()
    }
}