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
data class QueueDTO(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("letter") val letter: String,
    @JsonProperty("type") val type: Int,
    @JsonProperty("activeServers") val activeServers: Int,
    @JsonProperty("maxAvailable") val maxAvailable: Int,
    @JsonProperty("servicePostOfficeId") val servicePostOfficeId: Int,
    @JsonProperty("tolerance") val tolerance: Boolean
)
