package isel.meic.tfm.fei.entities

import java.sql.Timestamp

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class Session(val userId : Int, val token: String, val createdAt : Timestamp, val role : String, val name: String)