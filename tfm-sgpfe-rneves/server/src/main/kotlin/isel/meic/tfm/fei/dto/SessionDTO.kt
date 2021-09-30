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
 * TODO delete this - not being used
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionDTO(@JsonProperty("userId") val userId: Int, @JsonProperty("token") val token: String)
