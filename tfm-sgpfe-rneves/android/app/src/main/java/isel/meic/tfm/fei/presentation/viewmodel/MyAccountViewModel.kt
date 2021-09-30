package isel.meic.tfm.fei.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.model.UserInformation

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 *     //TODO change the mutable to immutable and create get method for them
 */
class MyAccountViewModel(application: FEIApplication, private val repo: FeiRepository) :
    AndroidViewModel(application) {

    val userInformation: MutableLiveData<UserInformation> = MutableLiveData()
    val userInformationUpdated: MutableLiveData<Boolean> = MutableLiveData()
    val userInformationUpdateFailed: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserInformation(token: String) {
        repo.getUserInformation(token, {
            userInformation.value = UserInformation(
                it.properties.id,
                it.properties.username,
                it.properties.name,
                it.properties.phone,
                it.properties.notification
            )
        }, { onError(it) })
    }

    fun editUserInformation(
        id: Int,
        name: String,
        phone: Int,
        notifications: Boolean,
        token: String
    ) {
        repo.sendUserInformation(
            id,
            name,
            phone,
            notifications,
            token,
            { userInformationUpdated.postValue(it.properties.success) },
            {
                onError(it);
                userInformationUpdateFailed.postValue(true)
            })
    }

    private fun onError(error: VolleyError) {
        //todo
        Toast.makeText(getApplication(), "MyAccountViewModel - Error!", Toast.LENGTH_LONG).show()
    }
}