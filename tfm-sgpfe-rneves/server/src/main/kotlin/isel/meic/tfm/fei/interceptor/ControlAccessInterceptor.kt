package isel.meic.tfm.fei.interceptor

import isel.meic.tfm.fei.contract.dal.IUserDAL
import isel.meic.tfm.fei.contract.services.IUserService
import isel.meic.tfm.fei.controlAccess.RequestAccessManager
import isel.meic.tfm.fei.controlAccess.RequestParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Provides access control to the received requests
 */
class ControlAccessInterceptor constructor(
    private val userDAL: IUserDAL,
    private val requestAccessManager: RequestAccessManager
) : HandlerInterceptor {

    companion object {
        const val AUTHORIZATION = "authorization"
        const val BEARER_PREFIX = "Bearer "
        private const val NONE = "NONE"
    }

    //TODO COMPLETE THIS. ONLY TEMPORARY
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val method = request.method
        if (method == RequestMethod.OPTIONS.toString())
            return true
        //val queryString = request.queryString

        //var path = if(queryString.isNullOrEmpty()) request.servletPath else String.format("%s?%s", request.servletPath, queryString)
        val path = request.servletPath

        var accessToken: String? = request.getHeader(AUTHORIZATION)
        accessToken = accessToken?.removePrefix(BEARER_PREFIX)

        //THIS IS TEMPORARY //TODO DELETE
        /*if (path.contains("login") || path.contains("isValid") || path.contains("notify") || path == "/" || path.contains(
                "roles"
            ) || path.contains("register")
        )
            return true*/
        if (accessToken != null) {

            val session = userDAL.isSessionValid(accessToken)
            if (session != null && requestAccessManager.hasAccess(session.role, RequestParams(method, path)))
                return true
        } else if (requestAccessManager.hasAccess(NONE, RequestParams(method, path)))
            return true

        //TODO verify is user has permission to make request

        response.status = HttpStatus.UNAUTHORIZED.value()
        return false
    }

    private fun noAuthenticationNeeded(path: String) {

    }
}