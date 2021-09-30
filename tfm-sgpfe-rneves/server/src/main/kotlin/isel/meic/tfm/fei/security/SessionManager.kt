package isel.meic.tfm.fei.security

import isel.meic.tfm.fei.contract.dal.ISessionDAL
import isel.meic.tfm.fei.entities.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Component
class SessionManager {

    @Autowired
    private val sessionA: ISessionDAL? = null

    @PostConstruct
    fun init() {
        sessionDAL = sessionA
    }

    companion object {
        private var sessionDAL: ISessionDAL? = null

        fun createSession(userId: Int, role: String, name: String): Session? {
            val token = UUID.randomUUID().toString()
            return sessionDAL?.createSession(userId, token, role, name)
        }

        fun isSessionValid(token: String): Session? {
            return sessionDAL?.isSessionValid(token)
        }

        fun invalidateSession(token: String): Boolean {
            return sessionDAL?.invalidateSession(token) ?: false
        }

        fun getUserIdFromAccessToken(token: String): Int {
            return sessionDAL?.getUserIdFromAccessToken(token) ?: -1
        }
    }
}
