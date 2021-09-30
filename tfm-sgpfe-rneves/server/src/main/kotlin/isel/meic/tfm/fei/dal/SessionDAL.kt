package isel.meic.tfm.fei.dal

import isel.meic.tfm.fei.contract.dal.ISessionDAL
import isel.meic.tfm.fei.dal.sqlqueries.SessionQueries
import isel.meic.tfm.fei.entities.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.Timestamp
import java.sql.Types

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Repository
class SessionDAL @Autowired constructor(private var dataSourceTransactionManager: DataSourceTransactionManager) :
    ISessionDAL {

    companion object {
        private const val ONE_DAY = 24 * 60 * 60 * 1000
    }

    private var jdbcTemplate: JdbcTemplate? = null

    private fun getJdbcTemplate(): JdbcTemplate {
        if (jdbcTemplate == null)
            jdbcTemplate =
                JdbcTemplate(SingleConnectionDataSource(dataSourceTransactionManager.dataSource!!.connection, false))
        return jdbcTemplate!!
    }

    @CachePut(value = ["session"], key = "#userId")
    override fun createSession(userId: Int, token: String, role: String, name: String): Session? {
        val affectedRows = getJdbcTemplate().update(
            SessionQueries.INSERT_SESSION,
            arrayOf(userId, token),
            intArrayOf(Types.INTEGER, Types.VARCHAR)
        )
        return if (affectedRows != 0) Session(
            userId,
            token,
            Timestamp(System.currentTimeMillis()),
            role,
            name
        ) else null
    }

    override fun isSessionValid(token: String): Session? {
        val session = getSession(token)
        if (session != null && session.token == token && System.currentTimeMillis() - ONE_DAY < session.createdAt.time) {
            return session
        }
        invalidateSession(token)
        return null
    }

    @Cacheable(value = ["session"], key = "#token")
    fun getSession(token: String): Session? {
        try {
            return getJdbcTemplate().queryForObject(
                SessionQueries.SELECT_SESSION_BY_TOKEN,
                SessionRowMapper(),
                token
            )
        } catch (ignored: Throwable) {
        }
        return null
    }

    @CacheEvict(value = ["session"], key = "#token")
    override fun invalidateSession(token: String): Boolean {
        val affectedRows = getJdbcTemplate().update(
            SessionQueries.UPDATE_SESSION,
            arrayOf(token),
            intArrayOf(Types.VARCHAR)
        )
        return affectedRows != 0
    }

    override fun getUserIdFromAccessToken(token: String): Int {
        val session = getSession(token)
        return session?.userId ?: -1
    }

    inner class SessionRowMapper : RowMapper<Session> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Session? {
            return Session(
                rs.getInt(2),
                rs.getString(3),
                rs.getTimestamp(4),
                rs.getString(5),
                ""
            )
        }
    }
}