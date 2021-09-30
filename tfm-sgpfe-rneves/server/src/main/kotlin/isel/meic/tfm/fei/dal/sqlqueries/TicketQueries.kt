package isel.meic.tfm.fei.dal.sqlqueries

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class TicketQueries {
    companion object {

        //TODO fix this

        //const val SELECT_SERVICE_TICKET = "SELECT * FROM QUEUE WHERE servicePostOfficeId = ? ORDER BY letter;"
        const val SELECT_SERVICE_TICKET =
            "SELECT qs.id, qs.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid FROM queue_state AS qs INNER JOIN queue AS q ON (qs.queueid = q.id) WHERE servicepostofficeid = ?\n" +
                    "\n ORDER BY letter;"

        const val SELECT_POST_OFFICE_QUEUE_STATES =
            "SELECT qs.number, qa.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid, \n" +
                    "(SELECT COALESCE(SUM(forecast), 0.0) as forecast FROM ticket_user WHERE queueid = qs.queueid AND qs.number >= number AND qa.number <= number)\n" +
                    "FROM queue_state AS qs INNER JOIN queue_attended AS qa ON (qs.queueid = qa.queueid) \n" +
                    "INNER JOIN queue AS q ON (qs.queueid = q.id) \n" +
                    "WHERE servicepostofficeid = ?  \n" +
                    "GROUP BY qs.number, qa.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid ORDER BY qs.letter;"

        const val SELECT_POST_OFFICE_QUEUE_STATE =
            "SELECT qs.number, qa.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid,\n" +
                    "(SELECT COALESCE(SUM(forecast), 0.0) as forecast FROM ticket_user WHERE queueid = qs.queueid AND qs.number >= number AND qa.number <= number)\n" +
                    "FROM queue_state AS qs INNER JOIN queue_attended AS qa ON (qs.queueid = qa.queueid) \n" +
                    "INNER JOIN queue AS q ON (qs.queueid = q.id) WHERE q.id = ?  \n" +
                    "group by qs.number, qa.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid;"

        const val SELECT_TICKET = "SELECT * FROM QUEUE WHERE id = ?;"
        const val SELECT_ACTIVE_SERVERS_FOR_TICKET = "SELECT activeservers FROM QUEUE WHERE id = ?;"

        const val SELECT_TICKET_STATE =
            "SELECT qs.id, qs.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid FROM QUEUE_STATE AS qs INNER JOIN queue AS q ON (qs.queueid = q.id) WHERE qs.id = ?;"
        const val SELECT_TICKET_ATTENDED =
            "SELECT qa.id, qa.number, qa.letter, qa.queueid, q.name, q.servicepostofficeid FROM QUEUE_ATTENDED AS qa INNER JOIN queue AS q ON (qa.queueid = q.id) WHERE q.id = ?;"
        const val SELECT_QUEUE_TYPES = "SELECT * FROM QUEUE_TYPE WHERE id = ?"

        const val SELECT_TICKET_ATTENDED_BY_QUEUE_ID = "SELECT id, number FROM QUEUE_ATTENDED WHERE queueid = ?;"

        const val SELECT_FORECAST_FOR_QUEUE =
            "select sum(forecast) from ticket_user where queueid = ? and number >=? and number <=?;"

        const val UPDATE_QUEUE_STATE = "UPDATE QUEUE_STATE SET number = number + 1 WHERE id = ?"
        const val UPDATE_QUEUE_ATTENDED = "UPDATE QUEUE_ATTENDED SET number = number + 1 WHERE id = ?"


        const val UPDATE_TICKET_USER_FORECAST = "UPDATE TICKET_USER SET forecast = ? WHERE ID = ?"


        const val INSERT_TICKET_USER = "INSERT INTO TICKET_USER(queueid, number, userId) VALUES (?,?,?);"

        const val SELECT_TICKET_USER_BY_QUEUE_ID = "SELECT id, duration, attended FROM\n" +
                "(SELECT id, duration, attended FROM ticket_user WHERE attended = 1::BIT AND queueid = ? ORDER BY createdat DESC limit ?) as one\n" +
                "UNION \n" +
                "(SELECT id, duration, attended FROM ticket_user WHERE attended = 0::BIT AND queueid = ?) ORDER BY id;"


        const val DELETE_QUEUE = "UPDATE QUEUE SET state = 0::BIT WHERE id = ?;"

        /********************************************** for observers **********************************************/

        const val SELECT_QUEUE_OBSERVERS = "SELECT DISTINCT(userId) FROM QUEUE_OBSERVERS WHERE queueId = ?;"
        const val INSERT_QUEUE_OBSERVER = "INSERT INTO QUEUE_OBSERVERS(queueId, userId) VALUES (?,?);"
        const val DELETE_QUEUE_OBSERVER = "DELETE QUEUE_OBSERVERS WHERE queueId = ? AND userId = ?;"

        const val SELECT_POST_OFFICE_OBSERVERS = "SELECT DISTINCT(userId) FROM POST_OFFICE_OBSERVERS WHERE postId = ?;"
        const val INSERT_POST_OFFICE_OBSERVER = "INSERT INTO POST_OFFICE_OBSERVERS(postId, userId) VALUES (?,?);"
        const val DELETE_POST_OFFICE_OBSERVER = "DELETE POST_OFFICE_OBSERVERS WHERE postId = ? AND userId = ?;"

        const val SELECT_QUEUE_OBSERVERS_TOKENS =
            "SELECT DISTINCT(u.fcm_token) FROM QUEUE_OBSERVERS AS qo INNER JOIN FEI_USER as u ON (qo.userId = u.id) WHERE queueId = ?;"
        const val SELECT_POST_OFFICE_OBSERVERS_TOKENS =
            "SELECT DISTINCT(u.fcm_token) FROM POST_OFFICE_OBSERVERS AS poo INNER JOIN FEI_USER as u ON (poo.userId = u.id) WHERE postId = ?;"

        const val SELECT_TURN_TOKEN = "SELECT FU.fcm_token from FEI_USER AS FU \n" +
                "INNER JOIN TICKET_USER AS TU ON FU.id = TU.id \n" +
                "WHERE TU.queueid = ? and TU.number = ?;"
        /******************************************** for external service ********************************************/

        const val SELECT_POST_OFFICE_BEING_ATTENDED_TICKET =
            "SELECT qs.id, qs.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid FROM queue_attended AS qs INNER JOIN queue AS q ON (qs.queueid = q.id) WHERE servicepostofficeid = ?\n" +
                    "\n ORDER BY letter;"

        const val SELECT_POST_OFFICE_BEING_ATTENDED_TICKET_FOR_USER = "SELECT qs.id, qs.number, qs.letter, qs.queueid, q.name, q.servicepostofficeid FROM queue_attended AS qs INNER JOIN queue AS q ON (qs.queueid = q.id) INNER JOIN USER_POST_OFFICE as UPO ON (q.servicepostofficeid = UPO.servicepostofficeid) WHERE UPO.userid = ? ORDER BY letter;"

    }
}