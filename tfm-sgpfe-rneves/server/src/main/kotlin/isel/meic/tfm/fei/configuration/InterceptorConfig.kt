package isel.meic.tfm.fei.configuration

import isel.meic.tfm.fei.contract.dal.IUserDAL
import isel.meic.tfm.fei.contract.services.IUserService
import isel.meic.tfm.fei.controlAccess.RequestAccessManager
import isel.meic.tfm.fei.interceptor.ControlAccessInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Configuration
class InterceptorConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var userDAL: IUserDAL

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(ControlAccessInterceptor(userDAL, RequestAccessManager(userDAL, userDAL.getAllPathControlAccess())))
    }
}