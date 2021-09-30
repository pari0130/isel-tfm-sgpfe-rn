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
data class UserRegisterDTO(
    @JsonProperty("name") val name: String,
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("phone") val phone: Int,
    //@JsonProperty("role") val role: String,
    @JsonProperty("notification") val notification: Boolean
)