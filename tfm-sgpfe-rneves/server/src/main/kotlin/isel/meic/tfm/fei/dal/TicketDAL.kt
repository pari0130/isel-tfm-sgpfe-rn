package isel.meic.tfm.fei.dal

import isel.meic.tfm.fei.contract.dal.IForecastDAL
import isel.meic.tfm.fei.contract.dal.ITicketDAL
import isel.meic.tfm.fei.dal.sqlqueries.TicketQueries
import isel.meic.tfm.fei.entities.QueueState
import isel.meic.tfm.fei.entities.QueueType
import isel.meic.tfm.fei.entities.Ticket
import isel.meic.tfm.fei.forecast.ExponentialForecast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.Types
import kotlin.streams.toList

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves 39643
 * @mentor: Paulo Pereira
 *
 * Data access layer with the operations to access Ticket entity from the DataSource
 */
@Repository
class TicketDAL @Autowired constructor(
    private var dataSourceTransactionManager: DataSourceTransactionManager,
    private var forecastDAL: IForecastDAL
) : ITicketDAL {

    private var jdbcTemplate: JdbcTemplate? = null

    private fun getJdbcTemplate(): JdbcTemplate {
        if (jdbcTemplate == null)
            jdbcTemplate =
                JdbcTemplate(SingleConnectionDataSource(dataSourceTransactionManager.dataSource!!.connection, false))
        return jdbcTemplate!!
    }

    //todo try catch
    override fun getCurrentTicketState(ticketId: Int): Ticket? {
        return getJdbcTemplate().queryForObject(
            TicketQueries.SELECT_TICKET_STATE,
            TicketRowMapper(),
            ticketId
        )
    }

    override fun getQueueState(queueId: Int): QueueState? {
        return getJdbcTemplate().queryForObject(
            TicketQueries.SELECT_POST_OFFICE_QUEUE_STATE,
            QueueStateRowMapper(),
            queueId
        )
    }

    //todo try catch -> DataAccessException //PostOfficeTickets
    override fun getServiceTickets(postOfficeId: Int): Collection<Ticket> {
        return getJdbcTemplate().query(TicketQueries.SELECT_SERVICE_TICKET, TicketRowMapper(), postOfficeId)
    }

    override fun getPostOfficeQueueStates(postOfficeId: Int): Collection<QueueState> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_POST_OFFICE_QUEUE_STATES,
            QueueStateRowMapper(),
            postOfficeId
        )
    }

    override fun takeTicket(ticketId: Int, userId: Int): QueueState? {
        val affectedRows =
            getJdbcTemplate().update(TicketQueries.UPDATE_QUEUE_STATE, arrayOf(ticketId), intArrayOf(Types.INTEGER))
        //TODO attention to this
        if (affectedRows != 0) {
            val ticket =
                getJdbcTemplate().queryForObject(
                    TicketQueries.SELECT_POST_OFFICE_QUEUE_STATE,
                    QueueStateRowMapper(),
                    ticketId
                )
            //todo verify if not null and do try catch
            getJdbcTemplate().update(
                TicketQueries.INSERT_TICKET_USER,
                arrayOf(ticketId, ticket?.stateNumber, userId),
                intArrayOf(Types.INTEGER, Types.INTEGER, Types.INTEGER)
            )
            //todo verify the affectedRows and do rollback if necessary
            calculateForecast(ticketId)
            return ticket
        }
        return null
    }

    //This should be in the Service TODO fix me
    @Async
    fun calculateForecast(queueId: Int) {
        val forecast = forecastDAL.getForecastInformation(queueId)
        //TODO use the past forecast for the first ticket removed
        val ticketUserList = getJdbcTemplate().query(
            TicketQueries.SELECT_TICKET_USER_BY_QUEUE_ID,
            TicketUserRowMapper(), queueId, forecast.limit, queueId
        )
        val queueActiveServers = getJdbcTemplate().queryForObject(
            TicketQueries.SELECT_ACTIVE_SERVERS_FOR_TICKET,
            Int::class.java, queueId
        )
        //todo obtain the 3 from database. maybe add a new column in forecast
        val withForecast = ticketUserList.stream()
            .filter { it.attended /*&& it.duration > forecast.forecast - 3 && it.duration < forecast.forecast + 3*/ }
            .map { it.duration }.toList()
        val numForecasts = ticketUserList.size - withForecast.size

        //try catch, verify if i have data too
        try {


            val result = ExponentialForecast.singleExponentialForecast(withForecast, forecast.alpha, numForecasts)
            val toUpdate = ArrayList<TicketUserForecast>()
            //todo verify array size
            for ((i, tu) in ticketUserList.withIndex()) {
                toUpdate.add(TicketUserForecast(tu.id, result[i].div(queueActiveServers)))
            }

            val updateCounts = getJdbcTemplate().batchUpdate(
                TicketQueries.UPDATE_TICKET_USER_FORECAST,
                toUpdate,
                toUpdate.size
            ) { ps, tuForecast ->
                ps.setDouble(1, tuForecast.forecast)
                ps.setInt(2, tuForecast.id)
            }
        } catch (ignored: java.lang.Exception) {

        }
        //todo return updateCount??
    }

    override fun registerForUpdates(queueId: Int, userId: Int) {
        getJdbcTemplate().update(
            TicketQueries.INSERT_QUEUE_OBSERVER,
            arrayOf(queueId, userId),
            intArrayOf(Types.INTEGER, Types.INTEGER)
        )
        //todo maybe add trigger that cleans older subscriptions
    }

    override fun registerForPostOfficeUpdates(postOfficeId: Int, userId: Int) {
        getJdbcTemplate().update(
            TicketQueries.INSERT_POST_OFFICE_OBSERVER,
            arrayOf(postOfficeId, userId),
            intArrayOf(Types.INTEGER, Types.INTEGER)
        )
        //todo maybe add trigger that cleans older subscriptions
    }

    override fun unregisterForUpdates(queueId: Int, userId: Int) {
        getJdbcTemplate().update(
            TicketQueries.DELETE_QUEUE_OBSERVER,
            arrayOf(queueId, userId),
            intArrayOf(Types.INTEGER, Types.INTEGER)
        )
    }

    override fun unregisterForPostOfficeUpdates(postOfficeId: Int, userId: Int) {
        getJdbcTemplate().update(
            TicketQueries.DELETE_POST_OFFICE_OBSERVER,
            arrayOf(postOfficeId, userId),
            intArrayOf(Types.INTEGER, Types.INTEGER)
        )
    }

    override fun getTicketObservers(ticketId: Int): Collection<Int> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_QUEUE_OBSERVERS,
            IntRowMapper(),
            ticketId
        )
    }

    override fun getPostOfficeObservers(postOfficeId: Int): Collection<Int> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_POST_OFFICE_OBSERVERS,
            IntRowMapper(),
            postOfficeId
        )
    }

    override fun getTicketObserversTokens(ticketId: Int): Collection<String> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_QUEUE_OBSERVERS_TOKENS,
            StringRowMapper(),
            ticketId
        )
    }

    override fun getPostOfficeObserversTokens(postOfficeId: Int): Collection<String> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_POST_OFFICE_OBSERVERS_TOKENS,
            StringRowMapper(), postOfficeId
        )
    }

    //todo in the future return the USER
    override fun getNextInLine(ticketId: Int, ticketNumber: Int): String? {
        val jdbcCall: SimpleJdbcCall = SimpleJdbcCall(getJdbcTemplate()).withFunctionName("GET_NEXT_IN_LINE")
        val params: SqlParameterSource = MapSqlParameterSource()
            .addValue("ticket_id", ticketId)
            .addValue("ticket_number", ticketNumber)
        var next: String? = null
        try {
            next = jdbcCall.executeFunction(String::class.java, params)
        } catch (e: Exception) {
        }
        return next
    }

    override fun getTurnToken(ticketId: Int, ticketNumber: Int): String? {
        var result: String? = null
        val collection = getJdbcTemplate().query(
            TicketQueries.SELECT_TURN_TOKEN,
            StringRowMapper(), ticketId, ticketNumber + 1
        )
        if (collection.size > 0)
            result = collection[0]
        return result
    }

    override fun getQueueTypes(): Collection<QueueType> {
        return getJdbcTemplate().query(TicketQueries.SELECT_QUEUE_TYPES, QueueTypesRowMapper())
    }

    override fun deleteQueue(id: Int): Boolean {
        val affectedRows = getJdbcTemplate().update(
            TicketQueries.DELETE_QUEUE,
            arrayOf(id),
            intArrayOf(Types.INTEGER)
        )
        return affectedRows != 0
    }

    /********************************************** for external service **********************************************/

    //todo make this async //todo remove serviceId
    override fun ticketAttended(ticketId: Int): Boolean { //todo return affectedRows
        val queue = getJdbcTemplate().queryForObject(
            TicketQueries.SELECT_TICKET_ATTENDED_BY_QUEUE_ID,
            QueueNumberRowMapper(),
            ticketId
        )
        if (queue != null) {
            updateTicketUser(ticketId, queue.number)
        }
        calculateForecast(ticketId)

        val affectedRows =
            getJdbcTemplate().update(TicketQueries.UPDATE_QUEUE_ATTENDED, arrayOf(ticketId), intArrayOf(Types.INTEGER))
        return affectedRows != 0
    }

    override fun getPostOfficeBeingAttendedTickets(postOfficeId: Int): Collection<Ticket> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_POST_OFFICE_BEING_ATTENDED_TICKET,
            TicketRowMapper(),
            postOfficeId
        )
    }

    override fun getPostOfficeBeingAttendedTicketsForUser(userId: Int): Collection<Ticket> {
        return getJdbcTemplate().query(
            TicketQueries.SELECT_POST_OFFICE_BEING_ATTENDED_TICKET_FOR_USER,
            TicketRowMapper(),
            userId
        )
    }


    @Async
    fun updateTicketUser(queueId: Int, number: Int) {
        val jdbcCall: SimpleJdbcCall = SimpleJdbcCall(getJdbcTemplate()).withProcedureName("SET_USER_TICKET_ATTENDED")
        val params: SqlParameterSource = MapSqlParameterSource()
            .addValue("queue_id", queueId)
            .addValue("t_number", number)
        try {
            jdbcCall.execute(params)
        } catch (e: Exception) {
            //TODO
        }
    }

    /************************************************** row mappers ***************************************************/

    //todo try catch
    inner class TicketRowMapper : RowMapper<Ticket> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Ticket? {
            return Ticket(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getInt(6)
            )
        }
    }

    inner class QueueStateRowMapper : RowMapper<QueueState> {
        override fun mapRow(rs: ResultSet, rowNum: Int): QueueState? {
            return QueueState(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getInt(6),
                rs.getDouble(7)
            )
        }
    }

    inner class QueueTypesRowMapper : RowMapper<QueueType> {
        override fun mapRow(rs: ResultSet, rowNum: Int): QueueType? {
            return QueueType(
                rs.getInt(1),
                rs.getString(2)
            )
        }
    }

    inner class QueueNumberRowMapper : RowMapper<QueueIDNumber> {
        override fun mapRow(rs: ResultSet, rowNum: Int): QueueIDNumber? {
            return QueueIDNumber(
                rs.getInt(1),
                rs.getInt(2)
            )
        }
    }

    inner class TicketUserRowMapper : RowMapper<TicketUser> {
        override fun mapRow(rs: ResultSet, rowNum: Int): TicketUser? {
            return TicketUser(
                rs.getInt(1),
                rs.getDouble(2),
                rs.getBoolean(3)
            )
        }
    }

    inner class IntRowMapper : RowMapper<Int> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Int {
            return rs.getInt(1)
        }
    }

    /*inner class DoubleRowMapper : RowMapper<Double> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Double {
            return rs.getDouble(1)
        }
    }*/

    inner class StringRowMapper : RowMapper<String> {
        override fun mapRow(rs: ResultSet, rowNum: Int): String {
            return rs.getString(1)
        }
    }

    /********************************************* auxiliary data classes *********************************************/
    //TODO Put this in another place

    data class QueueIDNumber(val id: Int, val number: Int)

    data class TicketUser(val id: Int, val duration: Double, val attended: Boolean)

    data class TicketUserForecast(val id: Int, val forecast: Double)
}