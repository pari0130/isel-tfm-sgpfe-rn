package isel.meic.tfm.fei.presentation.data.model

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class LoggedInUser(
    val userId: Int,
    val token: String,
    val name: String
)
