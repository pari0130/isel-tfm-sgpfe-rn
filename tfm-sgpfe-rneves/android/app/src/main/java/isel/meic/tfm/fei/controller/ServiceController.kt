package isel.meic.tfm.fei.controller

import com.android.volley.VolleyError
import isel.meic.tfm.fei.contract.IServiceController
import isel.meic.tfm.fei.inputmodel.service.ServicePostOfficesInputModel
import isel.meic.tfm.fei.inputmodel.service.ServicesInputModel
import isel.meic.tfm.fei.webapi.ServiceApi

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServiceController(private val serviceApi: ServiceApi) : IServiceController {
    override fun getServices(
        url: String?,
        token: String?,
        success: (ServicesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        serviceApi.getServices(url, token, success, onError)
    }

    override fun getServicePostOffices(
        serviceId: Int,
        url: String?,
        token: String?,
        success: (ServicePostOfficesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        serviceApi.getServicePostOffices(serviceId, url, token, success, onError)
    }
}