package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class ServicePostOffice(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val serviceId: Int,
    val address: String
)