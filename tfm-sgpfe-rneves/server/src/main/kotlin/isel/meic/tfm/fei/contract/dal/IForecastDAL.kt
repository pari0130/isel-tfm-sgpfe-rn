package isel.meic.tfm.fei.contract.dal

import isel.meic.tfm.fei.entities.Forecast

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface IForecastDAL {
    fun getForecastInformation(queueId : Int) : Forecast
    fun updateForecastInformation(queueId: Int, alpha : Double, limit : Int)
    fun updateDayOfTheWeekForecast(queueId: Int, forecast: Double)
}