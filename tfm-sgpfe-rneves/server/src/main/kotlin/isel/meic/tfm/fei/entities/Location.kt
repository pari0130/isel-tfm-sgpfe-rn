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
data class Location(val id: Int, val latitude: Double, val longitude: Double, val updateAt: Timestamp, val userId: Int)
