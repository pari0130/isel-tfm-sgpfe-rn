package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class UserInformation(
    val id: Int,
    val username: String,
    val name: String,
    val nif: Int,
    val phone: Int,
    val notification: Boolean
)