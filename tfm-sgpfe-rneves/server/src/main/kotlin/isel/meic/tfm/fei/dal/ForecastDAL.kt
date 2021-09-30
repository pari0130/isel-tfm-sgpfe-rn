package isel.meic.tfm.fei.dal

import isel.meic.tfm.fei.contract.dal.IForecastDAL
import isel.meic.tfm.fei.dal.sqlqueries.ForecastQueries
import isel.meic.tfm.fei.entities.Forecast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.stereotype.Repository
import java.sql.ResultSet

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Repository
class ForecastDAL @Autowired constructor(private var dataSourceTransactionManager: DataSourceTransactionManager) :
    IForecastDAL {

    private var jdbcTemplate: JdbcTemplate? = null

    private fun getJdbcTemplate(): JdbcTemplate {
        if (jdbcTemplate == null)
            jdbcTemplate =
                JdbcTemplate(SingleConnectionDataSource(dataSourceTransactionManager.dataSource!!.connection, false))
        return jdbcTemplate!!
    }

    @Cacheable(value = ["forecast"], key = "#queueId")
    override fun getForecastInformation(queueId: Int): Forecast {
        val forecast =
            getJdbcTemplate().queryForObject(ForecastQueries.SELECT_FORECAST, ForecastRowMapper(), queueId)
        //todo verify if not null
        return forecast!!
    }

    @CachePut(value = ["forecast"], key = "#queueId")
    override fun updateForecastInformation(queueId: Int, alpha: Double, limit: Int) {
        //todo use forecast as arg ... maybe
        TODO("Not yet implemented")
    }

    override fun updateDayOfTheWeekForecast(queueId: Int, forecast: Double) {
        TODO("Not yet implemented")
    }

    inner class ForecastRowMapper : RowMapper<Forecast> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Forecast? {
            return Forecast(
                rs.getInt(1),
                rs.getInt(2),
                rs.getDouble(3),
                rs.getInt(4),
                rs.getDouble(5)
            )
        }
    }
}