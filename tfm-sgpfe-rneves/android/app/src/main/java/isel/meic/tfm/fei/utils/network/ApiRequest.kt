package isel.meic.tfm.fei.utils.network

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONObject

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 */

class ApiRequest {
    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}

class GetRequest<T>(
    url: String,
    request: JSONObject?,
    private val type: Class<T>,
    private val token: String?,
    success: Response.Listener<T>,
    error: Response.ErrorListener
) : JsonRequest<T>(Method.GET, url, request.toString(), success, error) {

    override fun getHeaders(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        if (token != null) {
            params["authorization"] = "Bearer $token"
        }
        return params
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val dto = ApiRequest.mapper.readValue(response?.data, type)
            Response.success(dto, null)
        } catch (e: Exception) {
            Response.error(VolleyError(response))
        }
    }
}

class PostRequest<T>(
    url: String,
    request: JSONObject?,
    private val type: Class<T>,
    private val token: String?,
    success: Response.Listener<T>,
    error: Response.ErrorListener
) : JsonRequest<T>(Method.POST, url, request.toString(), success, error) {

    override fun getHeaders(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        if (token != null) {
            params["authorization"] = "Bearer $token"
        }
        return params
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val dto = ApiRequest.mapper.readValue(response?.data, type)
            Response.success(dto, null)
        } catch (e: Exception) {
            Response.error(VolleyError(response))
        }
    }
}

class PostRequestWithoutType(
    url: String,
    request: JSONObject?,
    private val token: String?,
    success: Response.Listener<Any>,
    error: Response.ErrorListener
) : JsonRequest<Any>(Method.POST, url, request.toString(), success, error) {

    override fun getHeaders(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        if (token != null) {
            params["authorization"] = "Bearer $token"
        }
        return params
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<Any> {
        return try {
            Response.success(null, null)
        } catch (e: Exception) {
            Response.error(VolleyError(response))
        }
    }
}

class PutRequest<T>(
    url: String,
    request: JSONObject?,
    private val token: String?,
    private val type: Class<T>,
    success: Response.Listener<T>,
    error: Response.ErrorListener
) : JsonRequest<T>(Method.PUT, url, request.toString(), success, error) {

    override fun getHeaders(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        if (token != null) {
            params["authorization"] = "Bearer $token"
        }
        return params
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val dto = ApiRequest.mapper.readValue(response?.data, type)
            Response.success(dto, null)
        } catch (e: Exception) {
            Response.error(VolleyError(response))
        }
    }
}