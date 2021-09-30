package isel.meic.tfm.fei.presentation.data

import com.android.volley.VolleyError
import isel.meic.tfm.fei.inputmodel.SuccessInputModel
import isel.meic.tfm.fei.inputmodel.user.SessionInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInputModel
import isel.meic.tfm.fei.presentation.data.model.LoggedInUser

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository private constructor(private val dataSource: LoginDataSource) {

    companion object {
        private var instance: LoginRepository? = null

        fun getInstance(dataSource: LoginDataSource): LoginRepository {
            if (instance == null)
                instance = LoginRepository(dataSource)
            return instance!!
        }
    }

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout(token: String) {
        user = null
        dataSource.logout(token)
    }

    fun login(
        username: String,
        password: String,
        success: (UserInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        dataSource.login(username, password, success, onError)
    }

    fun sendRegistrationFcmToken(
        userId: Int, token: String, success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        dataSource.sendRegistrationFcmToken(userId, token, success, onError)
    }

    fun isValid(
        token: String,
        success: (SessionInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        dataSource.isValid(token, success, onError)
    }

    fun sendLocation(
        latitude: Double,
        longitude: Double,
        userId: Int,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        dataSource.sendLocation(latitude, longitude, userId, token, url, success, onError)
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}
