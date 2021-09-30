package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class Queue(
    val id: Int,
    val name : String,
    val activeServers: Int,
    val letter: String,
    val description: String,
    val type: Int,
    val maxAvailable: Int,
    val servicePostOfficeId: Int,
    val tolerance: Boolean
)