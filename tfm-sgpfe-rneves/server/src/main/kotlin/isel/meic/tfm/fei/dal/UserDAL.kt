package isel.meic.tfm.fei.dal

import isel.meic.tfm.fei.contract.dal.IUserDAL
import isel.meic.tfm.fei.dal.sqlqueries.UserQueries
import isel.meic.tfm.fei.dto.UserDTO
import isel.meic.tfm.fei.entities.*
import isel.meic.tfm.fei.security.SecurityUtils
import isel.meic.tfm.fei.security.SessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.ResultSet
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
class UserDAL @Autowired constructor(private var dataSourceTransactionManager: DataSourceTransactionManager) :
    IUserDAL {

    private var jdbcTemplate: JdbcTemplate? = null

    private fun getJdbcTemplate(): JdbcTemplate {
        if (jdbcTemplate == null)
            jdbcTemplate =
                JdbcTemplate(SingleConnectionDataSource(dataSourceTransactionManager.dataSource!!.connection, false))
        return jdbcTemplate!!
    }

    override fun login(user: UserDTO): Session? {
        try {
            val result = getJdbcTemplate().queryForObject(
                UserQueries.SELECT_USER_FOR_LOGIN,
                UserRowMapper(),
                user.username,
            )
            if (result != null && result.password == SecurityUtils.computePasswordWithEncodedSalt(
                    result.salt,
                    user.password
                )
            ) {
                return SessionManager.createSession(result.userId, result.role, result.name)
            }
        } catch (ignored: Throwable) {
        }
        return null
    }

    override fun registerFcmToken(userId: Int, token: String): Boolean {
        val affectedRows = getJdbcTemplate().update(
            UserQueries.UPDATE_USER_FCM_TOKEN,
            arrayOf(token, userId),
            intArrayOf(Types.VARCHAR, Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun logout(token: String): Boolean {
        return SessionManager.invalidateSession(token)
    }

    override fun isSessionValid(token: String): Session? {
        return SessionManager.isSessionValid(token)
    }

    override fun updateUserLocation(latitude: Double, longitude: Double, userId: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            UserQueries.UPDATE_USER_LOCATION,
            arrayOf(latitude, longitude, userId),
            intArrayOf(Types.DOUBLE, Types.DOUBLE, Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun getUserLocation(userId: Int): Location? {
        return getJdbcTemplate().queryForObject(
            UserQueries.SELECT_USER_LOCATION,
            LocationRowMapper(),
            userId
        )
    }

    override fun editUserInformation(
        userId: Int,
        name: String,
        phone: Int,
        notification: Boolean
    ): Boolean {
        val affectedRows = getJdbcTemplate().update(
            UserQueries.UPDATE_USER_INFORMATION,
            arrayOf(name, phone, notification, userId),
            intArrayOf(Types.VARCHAR, Types.INTEGER, Types.BOOLEAN, Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun getUserInformation(userId: Int): UserInformation? {
        return getJdbcTemplate().queryForObject(
            UserQueries.SELECT_USER_INFORMATION,
            UserInformationRowMapper(),
            userId
        )
    }

    override fun registerUser(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        role: String,
        notification: Boolean
    ): Pair<Boolean, UserExists?> {
        var user: UserInfoExists? = null
        try {
            user = getJdbcTemplate().queryForObject(
                UserQueries.VERIFY_IF_EXISTS,
                UserExistsRowMapper(),
                username, email
            )
        } catch (ignored: EmptyResultDataAccessException) {
        }
        if (user == null) {
            val encodedSalt = SecurityUtils.computeSHA256Hash(SecurityUtils.generateRandomSalt())
            val keyHolder = GeneratedKeyHolder()

            val affectedRows = getJdbcTemplate().update({
                val ps: PreparedStatement = it
                    .prepareStatement(UserQueries.INSERT_USER, arrayOf("id"))
                ps.setString(1, username)
                ps.setString(2, SecurityUtils.computePasswordWithEncodedSalt(encodedSalt, password))
                ps.setString(3, encodedSalt)
                ps.setString(4, name)
                ps.setString(5, email)
                ps.setInt(6, 123456789) //TODO remove this later
                ps.setInt(7, phone)
                ps.setBoolean(8, notification)
                ps
            }, keyHolder)

            val id = keyHolder.key?.toInt()
            getJdbcTemplate().update(
                UserQueries.INSERT_USER_ROLE,
                arrayOf(
                    id,
                    role
                ), intArrayOf(
                    Types.INTEGER,
                    Types.VARCHAR,
                )
            )
            return Pair(affectedRows != 0, null)
        }
        return Pair(false, UserExists(username == user.username, email == user.email))
    }

    override fun changePassword(userId: Int, oldPassword: String, newPassword: String): Boolean {
        val result = getJdbcTemplate().queryForObject(
            UserQueries.SELECT_USER_BY_USER_ID,
            UserRowMapper(),
            userId
        )
        if (result != null && result.password == SecurityUtils.computePasswordWithEncodedSalt(
                result.salt,
                oldPassword
            )
        ) {
            val encodedSalt = SecurityUtils.computeSHA256Hash(SecurityUtils.generateRandomSalt())
            val password = SecurityUtils.computePasswordWithEncodedSalt(encodedSalt, newPassword)
            val affectedRows = getJdbcTemplate().update(
                UserQueries.CHANGE_USER_PASSWORD,
                arrayOf(
                    password,
                    encodedSalt,
                    userId
                ), intArrayOf(
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.INTEGER
                )
            )
            return affectedRows != 0
        }
        return false
    }

    override fun servingQueue(queueId: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            UserQueries.SERVING_QUEUE,
            arrayOf(
                queueId
            ), intArrayOf(
                Types.INTEGER
            )
        )
        return affectedRows != 0
    }

    override fun leaveQueue(queueId: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            UserQueries.LEAVE_QUEUE,
            arrayOf(
                queueId
            ), intArrayOf(
                Types.INTEGER
            )
        )
        return affectedRows != 0
    }

    override fun getUserIdFromAccessToken(token: String): Int {
        return SessionManager.getUserIdFromAccessToken(token)
    }

    override fun getRoles(): Collection<Role> {
        return getJdbcTemplate().query(
            UserQueries.SELECT_ROLES,
            RoleRowMapper()
        )
    }

    override fun getAllPathControlAccess(): Collection<PathControlAccess> {
        return getJdbcTemplate().query(
            UserQueries.SELECT_PATH_CONTROL_ACCESS,
            PathControlAccessRowMapper()
        )
    }

    //todo try catch
    inner class UserRowMapper : RowMapper<User> {
        override fun mapRow(rs: ResultSet, rowNum: Int): User? {
            return User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
            )
        }
    }

    inner class LocationRowMapper : RowMapper<Location> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Location? {
            return Location(
                rs.getInt(1),
                rs.getDouble(2),
                rs.getDouble(3),
                rs.getTimestamp(4),
                rs.getInt(5)
            )
        }
    }

    inner class UserInformationRowMapper : RowMapper<UserInformation> {
        override fun mapRow(rs: ResultSet, rowNum: Int): UserInformation? {
            return UserInformation(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getBoolean(6)
            )
        }
    }

    data class UserInfoExists(val username: String, val email: String)

    inner class UserExistsRowMapper : RowMapper<UserInfoExists> {
        override fun mapRow(rs: ResultSet, rowNum: Int): UserInfoExists? {
            return UserInfoExists(
                rs.getString(1),
                rs.getString(2),
            )
        }
    }

    inner class RoleRowMapper : RowMapper<Role> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Role? {
            return Role(
                rs.getString(1)
            )
        }
    }

    inner class PathControlAccessRowMapper : RowMapper<PathControlAccess> {
        override fun mapRow(rs: ResultSet, rowNum: Int): PathControlAccess? {
            return PathControlAccess(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3).split(" ")
            )
        }
    }
}