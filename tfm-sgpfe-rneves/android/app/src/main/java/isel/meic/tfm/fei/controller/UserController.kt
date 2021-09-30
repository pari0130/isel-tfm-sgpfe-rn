package isel.meic.tfm.fei.controller

import com.android.volley.VolleyError
import isel.meic.tfm.fei.contract.IUserController
import isel.meic.tfm.fei.inputmodel.SuccessInputModel
import isel.meic.tfm.fei.inputmodel.user.RegisterInputModel
import isel.meic.tfm.fei.inputmodel.user.SessionInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInformationInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInputModel
import isel.meic.tfm.fei.webapi.UserApi

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - December 2019
 * @mentor: Paulo Pereira
 *
 */
class UserController(private val userApi: UserApi) : IUserController {

    override fun login(
        username: String,
        password: String,
        url: String?,
        success: (UserInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.doLogin(username, password, url, success, onError)
    }

    override fun sendRegistrationFcmToken(
        userId: Int,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.sendRegistrationFcmToken(userId, token, url, success, onError)
    }

    override fun logout(
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.doLogout(token, url, success, onError)
    }

    override fun isValid(
        token: String,
        url: String?,
        success: (SessionInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.isValid(token, url, success, onError)
    }

    override fun sendLocation(
        latitude: Double,
        longitude: Double,
        userId: Int,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.sendLocation(latitude, longitude, userId, token, url, success, onError)
    }

    override fun getUserInformation(
        token: String,
        url: String?,
        success: (UserInformationInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.getUserInformation(token, url, success, onError)
    }

    override fun sendUserInformation(
        id: Int,
        name: String,
        phone: Int,
        notification: Boolean,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.sendUserInformation(
            id,
            name,
            phone,
            notification,
            token,
            url,
            success,
            onError
        )
    }

    override fun register(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        notification: Boolean,
        url: String?,
        success: (RegisterInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userApi.register(
            username,
            password,
            name,
            email,
            phone,
            notification,
            url,
            success,
            onError
        )
    }
}