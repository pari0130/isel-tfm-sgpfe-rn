package isel.meic.tfm.fei.contract.dal

import isel.meic.tfm.fei.entities.Session

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 */
interface ISessionDAL {
    fun createSession(userId: Int, token: String, role: String, name: String): Session?
    fun isSessionValid(token: String): Session?
    fun invalidateSession(token: String): Boolean
    fun getUserIdFromAccessToken(token: String): Int
}