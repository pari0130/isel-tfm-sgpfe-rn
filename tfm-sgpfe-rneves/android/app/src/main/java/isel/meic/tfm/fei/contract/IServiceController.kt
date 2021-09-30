package isel.meic.tfm.fei.contract

import com.android.volley.VolleyError
import isel.meic.tfm.fei.inputmodel.service.ServicePostOfficesInputModel
import isel.meic.tfm.fei.inputmodel.service.ServicesInputModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface IServiceController {
    fun getServices(url : String?, token: String?, success: (ServicesInputModel) -> Unit, onError: (VolleyError) -> Unit)
    fun getServicePostOffices(serviceId : Int, url : String?, token: String?, success: (ServicePostOfficesInputModel) -> Unit, onError: (VolleyError) -> Unit)
}