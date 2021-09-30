package isel.meic.tfm.fei.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.presentation.data.model.RegisterUser
import isel.meic.tfm.fei.security.utils.SecurityUtils

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
class RegisterViewModel(application: FEIApplication, private val repo: FeiRepository) :
    AndroidViewModel(application) {

    private val _userRegisterResult: MutableLiveData<RegisterUser> = MutableLiveData()
    val userRegister: LiveData<RegisterUser> = _userRegisterResult
    val userRegisterFailed: MutableLiveData<Boolean> = MutableLiveData()

    fun register(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        notification: Boolean,
    ) {
        repo.register(
            username,
            SecurityUtils.computeSHA256Hash(password),
            name,
            email,
            phone,
            notification,
            {
                _userRegisterResult.value = RegisterUser(
                    it.properties.success,
                    it.properties.username,
                    it.properties.email,
                )
            }, {
                onError(it)
                userRegisterFailed.postValue(true)
            }
        )
    }

    private fun onError(error: VolleyError) {
        //todo
        Toast.makeText(getApplication(), "RegisterViewModel - Error!", Toast.LENGTH_LONG).show()
    }
}