package isel.meic.tfm.fei.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.model.Service
import isel.meic.tfm.fei.model.ServicePostOffice

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - August 2020
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServiceViewModel(application: FEIApplication, private val repo: FeiRepository) :
    AndroidViewModel(application) {

    val services: MutableLiveData<List<Service>> = MutableLiveData()
    val servicePostOffices: MutableLiveData<List<ServicePostOffice>> = MutableLiveData()

    //For testing only
    /*fun addToService(){
        val list = ArrayList<Service>()
        list.add(Service(1, "Service1", ""))
        list.add(Service(2, "Service2", ""))
        list.add(Service(3, "Service3", ""))

        services.value = list
    }*/

    fun getServices(token: String?) {
        repo.getServices(token, { services.value = it }, { onError(it) })
    }

    fun getServicePostOffices(serviceId: Int, token: String?) {
        repo.getServicePostOffice(serviceId, token, { servicePostOffices.value = it }, { onError(it) })
    }

    private fun onError(error: VolleyError) {
        //todo
        Toast.makeText(getApplication(), "ServiceViewModel - Error!", Toast.LENGTH_LONG).show()
    }
}