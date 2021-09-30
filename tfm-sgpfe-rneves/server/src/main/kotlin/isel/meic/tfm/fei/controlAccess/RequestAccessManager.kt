package isel.meic.tfm.fei.controlAccess

import isel.meic.tfm.fei.contract.dal.IUserDAL
import isel.meic.tfm.fei.contract.services.IUserService
import isel.meic.tfm.fei.entities.PathControlAccess
import isel.meic.tfm.fei.exception.PathNotFoundException
import java.util.regex.Pattern

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class RequestAccessManager(val userDAL: IUserDAL, private val pathControlAccess: Collection<PathControlAccess>) {

    fun hasAccess(role: String, requestParams: RequestParams): Boolean {
        pathControlAccess.forEach {
            val pathMatchesPattern = requestParams.path.matches(Pattern.compile(it.regex).toRegex())
            if ((it.method == requestParams.method).and(pathMatchesPattern)) {
                if (it.roles.contains(Role.NONE.name).or(it.roles.contains(role))) return true
                return false
            }
        }
        return false
        //return true
    }

    fun hasAccessToService() {

    }

    fun hasAccessToPostOffice() {

    }
}