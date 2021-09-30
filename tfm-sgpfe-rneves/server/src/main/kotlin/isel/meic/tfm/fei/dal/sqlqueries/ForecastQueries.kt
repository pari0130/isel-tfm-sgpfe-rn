package isel.meic.tfm.fei.dal.sqlqueries

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ForecastQueries {

    companion object {
        const val SELECT_FORECAST = "SELECT * FROM FORECAST WHERE queueId = ?;"

        //todo add update and insert
    }
}