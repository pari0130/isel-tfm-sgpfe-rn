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
data class PostOfficeDTO(
    @JsonProperty("description") val description: String,
    @JsonProperty("latitude") val latitude: Double,
    @JsonProperty("longitude") val longitude: Double,
    @JsonProperty("address") val address: String,
    @JsonProperty("serviceId") val serviceId: Int
)
