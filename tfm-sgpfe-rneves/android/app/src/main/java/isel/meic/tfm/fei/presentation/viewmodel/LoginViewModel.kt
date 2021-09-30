package isel.meic.tfm.fei.presentation.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.VolleyError
import com.google.gson.Gson
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.dto.LoggedInUserInformationDto
import isel.meic.tfm.fei.dto.UserDto
import isel.meic.tfm.fei.inputmodel.user.SessionInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInputModel
import isel.meic.tfm.fei.presentation.data.LoginRepository
import isel.meic.tfm.fei.presentation.data.model.LoggedInUser
import isel.meic.tfm.fei.presentation.ui.login.LoggedInUserView
import isel.meic.tfm.fei.presentation.ui.login.LoginFormState
import isel.meic.tfm.fei.presentation.ui.login.LoginResult
import isel.meic.tfm.fei.security.utils.SecurityUtils
import isel.meic.tfm.fei.utils.Constants

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
class LoginViewModel(private val loginRepository: LoginRepository, private val context: Context) :
    ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private val _loggedInUser = MutableLiveData<LoggedInUser>()
    val loggedInUser: LiveData<LoggedInUser> = _loggedInUser

    private fun loginResult(username: String, password: String, result: UserInputModel) {
        if (result.properties.authenticated) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.properties.name))
            _loggedInUser.value = LoggedInUser(
                result.properties.userId,
                result.properties.token,
                result.properties.name
            )
            persistLoggedUser(username, password, result.properties)
            loginRepository.sendRegistrationFcmToken(
                result.properties.userId,
                result.properties.token,
                { /* TODO deal with the result */ },
                { /* TODO deal with the error */ }
            )
        } else
            _loginResult.value = LoginResult(error = R.string.login_failed)
    }

    private fun isValidResult(loggedIn: LoggedInUserInformationDto, result: SessionInputModel) {
        if (result.properties.valid) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = loggedIn.name))
            _loggedInUser.value = LoggedInUser(
                loggedIn.userId,
                loggedIn.token,
                loggedIn.name
            )
            loginRepository.sendRegistrationFcmToken(
                loggedIn.userId,
                loggedIn.token,
                { /* TODO deal with the result */ },
                { /* TODO deal with the error */ })
        } else {
            //re-authenticate
            loginRepository.login(
                loggedIn.username,
                loggedIn.password,
                { loginResult(loggedIn.username, loggedIn.password, it) },
                { loginFailed(it) })
        }
    }

    //todo improve this
    private fun persistLoggedUser(username: String, password: String, result: UserDto) {
        val gson = Gson()
        val sharedPreferences =
            context.getSharedPreferences(Constants.FEI_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val loggedInUserInformation =
            LoggedInUserInformationDto(result.token, result.name, username, password, result.userId)
        //todo encrypt the information
        val toPersist = gson.toJson(loggedInUserInformation)
        editor.putString(Constants.LOGGED_IN_USER, toPersist).apply()
    }

    private fun removePersistedLoggedUser() {
        val sharedPreferences =
            context.getSharedPreferences(Constants.FEI_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(Constants.LOGGED_IN_USER).apply()
    }

    private fun loginFailed(error: VolleyError) {
        _loginResult.value = LoginResult(error = R.string.login_failed)
        //todo
    }

    fun login(username: String, password: String) {
        val hashedPassword = SecurityUtils.computeSHA256Hash(password)
        loginRepository.login(
            username,
            hashedPassword,
            { loginResult(username, hashedPassword, it) },
            { loginFailed(it) })
    }

    fun logout(token: String) {
        loginRepository.logout(token)
        removePersistedLoggedUser()
    }

    fun isTokenValid(loggedIn: LoggedInUserInformationDto) {
        loginRepository.isValid(
            loggedIn.token,
            { isValidResult(loggedIn, it) },
            { loginFailed(it) })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        //return password > 5
        return true
    }
}
