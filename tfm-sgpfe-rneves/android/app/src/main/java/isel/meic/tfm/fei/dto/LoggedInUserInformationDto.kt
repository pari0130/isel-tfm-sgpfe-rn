package isel.meic.tfm.fei.dto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class LoggedInUserInformationDto(
    val token: String,
    val name: String,
    val username: String,
    val password: String,
    val userId: Int
)