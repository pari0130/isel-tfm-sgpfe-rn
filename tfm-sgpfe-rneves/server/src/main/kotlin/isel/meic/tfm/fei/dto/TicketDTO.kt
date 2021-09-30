package isel.meic.tfm.fei.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 * todo change it later to inputModel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TicketDTO(val id: Int, val number : String, val serviceId : Int)