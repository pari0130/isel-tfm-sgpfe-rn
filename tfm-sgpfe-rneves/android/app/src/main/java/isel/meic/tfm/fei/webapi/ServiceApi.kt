package isel.meic.tfm.fei.webapi

import com.android.volley.VolleyError
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.inputmodel.service.ServicePostOfficesInputModel
import isel.meic.tfm.fei.inputmodel.service.ServicesInputModel
import isel.meic.tfm.fei.utils.network.GetRequest

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServiceApi(private val application: FEIApplication) {

    fun getServices(
        url: String?,
        token: String?,
        success: (ServicesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/service"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                ServicesInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }

    fun getServicePostOffices(
        serviceId: Int,
        url: String?,
        token: String?,
        success: (ServicePostOfficesInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val restOfUrl = "/service/$serviceId/postoffices"
        val completeUrl = if (url != null) url + restOfUrl else application.getUrl() + restOfUrl

        application.addToRequestQueue(
            GetRequest(
                completeUrl,
                null,
                ServicePostOfficesInputModel::class.java,
                token,
                { success(it) },
                { onError(it) })
        )
    }
}