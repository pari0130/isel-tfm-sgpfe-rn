package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class Forecast(val id: Int, val queueId: Int, val alpha: Double, val limit: Int, val forecast: Double)