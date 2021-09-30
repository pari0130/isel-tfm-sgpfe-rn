package isel.meic.tfm.fei.dal

import isel.meic.tfm.fei.contract.dal.IServiceDAL
import isel.meic.tfm.fei.dal.sqlqueries.ServiceQueries
import isel.meic.tfm.fei.entities.Queue
import isel.meic.tfm.fei.entities.Service
import isel.meic.tfm.fei.entities.ServicePostOffice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.stereotype.Repository
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
class ServiceDAL @Autowired constructor(private var dataSourceTransactionManager: DataSourceTransactionManager) :
    IServiceDAL {

    private var jdbcTemplate: JdbcTemplate? = null

    private fun getJdbcTemplate(): JdbcTemplate {
        if (jdbcTemplate == null)
            jdbcTemplate =
                JdbcTemplate(SingleConnectionDataSource(dataSourceTransactionManager.dataSource!!.connection, false))
        return jdbcTemplate!!
    }

    override fun getServices(): Collection<Service> {
        return getJdbcTemplate().query(ServiceQueries.SELECT_SERVICES, ServiceRowMapper())
    }

    override fun getServicePostOffices(serviceId: Int): Collection<ServicePostOffice> {
        return getJdbcTemplate().query(
            ServiceQueries.SELECT_SERVICE_POST_OFFICES,
            ServicePostOfficeRowMapper(),
            serviceId
        )
    }

    override fun getService(id: Int): Service? {
        return getJdbcTemplate().queryForObject(ServiceQueries.SELECT_SERVICE, ServiceRowMapper(), id)
    }

    //TODO TEST
    override fun getServiceForUser(userId: Int): Service? {
        return getJdbcTemplate().queryForObject(ServiceQueries.SELECT_SERVICE_FOR_USER, ServiceRowMapper(), userId)
    }

    override fun getServicePostOffice(id: Int): ServicePostOffice? {
        return getJdbcTemplate().queryForObject(
            ServiceQueries.SELECT_SERVICE_POST_OFFICE,
            ServicePostOfficeRowMapper(),
            id
        )
    }

    override fun getPostOfficeQueues(postOfficeId: Int): Collection<Queue> {
        return getJdbcTemplate().query(ServiceQueries.SELECT_QUEUES, QueueRowMapper(), postOfficeId)
    }

    //TODO TEST
    override fun getPostOfficeQueuesForUser(userId: Int): Collection<Queue> {
        return getJdbcTemplate().query(ServiceQueries.SELECT_QUEUES_FOR_USER, QueueRowMapper(), userId)
    }

    override fun getPostOfficeQueue(id: Int): Queue? {
        return getJdbcTemplate().queryForObject(
            ServiceQueries.SELECT_QUEUE,
            QueueRowMapper(),
            id
        )
    }

    override fun updateService(id: Int, name: String, description: String): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.UPDATE_SERVICE,
            arrayOf(name, description, id),
            intArrayOf(Types.VARCHAR, Types.VARCHAR, Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun updatePostOffice(
        id: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        address: String
    ): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.UPDATE_SERVICE_POST_OFFICE,
            arrayOf(latitude, longitude, description, address, id),
            intArrayOf(Types.DOUBLE, Types.DOUBLE, Types.VARCHAR, Types.VARCHAR, Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun updateQueue(
        name: String,
        description: String,
        letter: String,
        activeServers: Int,
        type: Int,
        maxAvailable: Int,
        id: Int,
        tolerance: Boolean
    ): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.UPDATE_QUEUE,
            arrayOf(activeServers, letter, name, description, type, maxAvailable, tolerance, id),
            intArrayOf(
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER,
                Types.BOOLEAN,
                Types.INTEGER
            )
        )
        return affectedRows != 0
    }

    override fun createService(name: String, description: String): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.INSERT_SERVICE,
            arrayOf(name, description),
            intArrayOf(Types.VARCHAR, Types.VARCHAR)
        )
        return affectedRows != 0
    }

    override fun createPostOffice(
        serviceId: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        address: String
    ): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.INSERT_POST_OFFICE,
            arrayOf(latitude, longitude, description, serviceId, address),
            intArrayOf(Types.DOUBLE, Types.DOUBLE, Types.VARCHAR, Types.INTEGER, Types.VARCHAR)
        )
        return affectedRows != 0
    }

    override fun createPostOfficeQueue(
        name: String,
        description: String,
        letter: String,
        type: Int,
        activeServers: Int,
        maxAvailable: Int,
        servicePostOfficeId: Int,
        tolerance: Boolean
    ): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.INSERT_POST_OFFICE_QUEUE,
            arrayOf(name, description, letter, type, activeServers, maxAvailable, servicePostOfficeId, tolerance),
            intArrayOf(
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.BOOLEAN
            )
        )
        return affectedRows != 0
    }

    override fun deleteService(id: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.DELETE_SERVICE,
            arrayOf(id),
            intArrayOf(Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun deletePostOffice(id: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.DELETE_SERVICE_POSTOFFICE,
            arrayOf(id),
            intArrayOf(Types.INTEGER)
        )
        return affectedRows != 0
    }

    override fun deleteQueue(id: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            ServiceQueries.DELETE_SERVICE_POSTOFFICE_QUEUE,
            arrayOf(id),
            intArrayOf(Types.INTEGER)
        )
        return affectedRows != 0
    }

    //todo try catch
    inner class ServiceRowMapper : RowMapper<Service> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Service? {
            return Service(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)
            )
        }
    }

    inner class ServicePostOfficeRowMapper : RowMapper<ServicePostOffice> {
        override fun mapRow(rs: ResultSet, rowNum: Int): ServicePostOffice? {
            return ServicePostOffice(
                rs.getInt(1),
                rs.getDouble(2),
                rs.getDouble(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getString(6)
            )
        }
    }

    inner class QueueRowMapper : RowMapper<Queue> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Queue? {
            return Queue(
                rs.getInt(1),
                rs.getString(4),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getInt(8),
                rs.getBoolean(9)
            )
        }
    }
}