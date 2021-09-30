package isel.meic.tfm.fei.contract

import com.android.volley.VolleyError
import isel.meic.tfm.fei.inputmodel.SuccessInputModel
import isel.meic.tfm.fei.inputmodel.user.RegisterInputModel
import isel.meic.tfm.fei.inputmodel.user.SessionInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInformationInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInputModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface IUserController {

    fun login(
        username: String,
        password: String,
        url: String?,
        success: (UserInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun sendRegistrationFcmToken(
        userId: Int,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun logout(
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun isValid(
        token: String,
        url: String?,
        success: (SessionInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun sendLocation(
        latitude: Double,
        longitude: Double,
        userId: Int,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun getUserInformation(
        token: String,
        url: String?,
        success: (UserInformationInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun sendUserInformation(
        id: Int,
        name: String,
        phone: Int,
        notification: Boolean,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )

    fun register(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        notification : Boolean,
        url: String?,
        success: (RegisterInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    )
}