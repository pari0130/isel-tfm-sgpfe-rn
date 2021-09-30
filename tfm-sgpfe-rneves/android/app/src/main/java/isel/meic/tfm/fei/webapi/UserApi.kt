package isel.meic.tfm.fei.webapi

import android.util.Log
import com.android.volley.VolleyError
import com.google.firebase.messaging.FirebaseMessaging
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.inputmodel.SuccessInputModel
import isel.meic.tfm.fei.inputmodel.user.RegisterInputModel
import isel.meic.tfm.fei.inputmodel.user.SessionInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInformationInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInputModel
import isel.meic.tfm.fei.utils.network.GetRequest
import isel.meic.tfm.fei.utils.network.PostRequest
import isel.meic.tfm.fei.utils.network.PutRequest
import org.json.JSONObject


/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - December 2019
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class UserApi(private val application: FEIApplication) {

    fun doLogin(
        username: String,
        password: String,
        url: String?,
        success: (UserInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/login"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        val params = HashMap<String, String>()
        params["username"] = username
        params["password"] = password

        application.addToRequestQueue(
            PostRequest(
                completeUrl,
                JSONObject(params as Map<*, *>),
                UserInputModel::class.java,
                null,
                { success(it) },
                { onError(it) })
        )
    }

    fun sendRegistrationFcmToken(
        userId: Int,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fcmToken = task.result
                val params = HashMap<String, String>()
                params["userId"] = userId.toString()
                params["fcm_token"] = fcmToken!!

                val restOfUrl = "/fcmtoken"
                val completeUrl =
                    if (url != null) url + restOfUrl else application.getUrl() + restOfUrl
                application.addToRequestQueue(
                    PutRequest(
                        completeUrl,
                        JSONObject(params as Map<*, *>),
                        token,
                        SuccessInputModel::class.java,
                        { success(it) },
                        { onError(it) })
                )
            }
        }
        //TODO delete this
        FirebaseMessaging.getInstance().subscribeToTopic("Ticket")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("USER_API", "failed to subscribe")
                }
            }
    }

    fun doLogout(
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/logout"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            PostRequest(
                completeUrl,
                null,
                SuccessInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    fun isValid(
        token: String,
        url: String?,
        success: (SessionInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/isValid"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                SessionInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
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
        val restOfUrl = "/location"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        val params = HashMap<String, Any>()
        params["latitude"] = latitude
        params["longitude"] = longitude
        params["userId"] = userId

        application.addToRequestQueue(
            PutRequest(
                completeUrl,
                JSONObject(params as Map<*, *>),
                token,
                SuccessInputModel::class.java,
                { success(it) },
                { onError(it) })
        )
    }

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
    ) {
        val restOfUrl = "/register"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        val params = HashMap<String, Any>()
        params["username"] = username
        params["password"] = password
        params["name"] = name
        params["email"] = email
        params["phone"] = phone
        params["notification"] = notification

        application.addToRequestQueue(
            PostRequest(
                completeUrl,
                JSONObject(params as Map<*, *>),
                RegisterInputModel::class.java,
                null,
                { success(it) },
                { onError(it) })
        )
    }

    fun getUserInformation(
        token: String,
        url: String?,
        success: (UserInformationInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/account"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                UserInformationInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    fun sendUserInformation(
        id: Int,
        name: String,
        phone: Int,
        notification: Boolean,
        token: String,
        url: String?,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/account"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        val params = HashMap<String, Any>()
        params["id"] = id
        params["name"] = name
        params["phone"] = phone
        params["notification"] = notification

        application.addToRequestQueue(
            PutRequest(
                completeUrl,
                JSONObject(params as Map<*, *>),
                token,
                SuccessInputModel::class.java,
                { success(it) },
                { onError(it) })
        )
    }
}