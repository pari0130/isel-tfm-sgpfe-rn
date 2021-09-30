package isel.meic.tfm.fei.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class ServingDTO(
    @JsonProperty("queueId") val queueId: Int,
    @JsonProperty("serving") val serving: Boolean
)
