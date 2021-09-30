package isel.meic.tfm.fei.presentation.ui.login

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
