package isel.meic.tfm.fei.presentation.data

import com.android.volley.VolleyError
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.contract.IUserController
import isel.meic.tfm.fei.controller.UserController
import isel.meic.tfm.fei.dto.UserDto
import isel.meic.tfm.fei.inputmodel.SuccessInputModel
import isel.meic.tfm.fei.inputmodel.user.SessionInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInputModel
import isel.meic.tfm.fei.model.Ticket
import isel.meic.tfm.fei.presentation.data.model.LoggedInUser
import isel.meic.tfm.fei.webapi.UserApi
import java.io.IOException

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Class that handles authentication w/ login credentials and retrieves user information.
 * //TODO change the info to the FEIRepository
 */
class LoginDataSource private constructor(application: FEIApplication) {

    private val userController: IUserController = UserController(UserApi(application))

    companion object {
        private var instance: LoginDataSource? = null

        fun getInstance(application: FEIApplication): LoginDataSource {
            if (instance == null)
                instance = LoginDataSource(application)
            return instance!!
        }
    }

    fun login(
        username: String,
        password: String,
        success: (UserInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userController.login(username, password, null, success, onError)
    }

    fun sendRegistrationFcmToken(
        userId: Int, token: String, success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userController.sendRegistrationFcmToken(userId, token, null, success, onError)
    }

    fun logout(token: String) {
        userController.logout(token, null, {}, {})
    }

    fun isValid(
        token: String,
        success: (SessionInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userController.isValid(token, null, success, onError)
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
        userController.sendLocation(latitude, longitude, userId, token, url, success, onError)
    }
}

