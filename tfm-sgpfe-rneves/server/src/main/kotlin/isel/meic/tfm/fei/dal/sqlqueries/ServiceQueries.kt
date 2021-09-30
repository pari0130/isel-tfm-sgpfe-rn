package isel.meic.tfm.fei.dal.sqlqueries

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServiceQueries {
    companion object {

        const val SELECT_SERVICES = "SELECT * FROM SERVICE WHERE state = 1::BIT;"
        const val SELECT_SERVICE_POST_OFFICES = "SELECT * FROM SERVICE_POSTOFFICE WHERE serviceid = ? AND state = 1::BIT;"
        const val SELECT_SERVICE = "SELECT * FROM SERVICE WHERE id = ? WHERE state = 1::BIT;"
        const val SELECT_SERVICE_FOR_USER = "SELECT S.id, S.name, S.description, S.state from SERVICE AS S INNER JOIN USER_SERVICE as US ON (S.id = US.serviceid) WHERE US.userid = ? AND S.state = 1::BIT;"
        const val SELECT_SERVICE_POST_OFFICE = "SELECT * FROM SERVICE_POSTOFFICE WHERE id = ? WHERE state = 1::BIT;"

        const val SELECT_QUEUES = "SELECT * FROM QUEUE WHERE servicepostofficeid = ? AND state = 1::BIT;"
        const val SELECT_QUEUES_FOR_USER = "SELECT Q.id, Q.activeservers, Q.letter, Q.name, Q.description, Q.type, Q.maxavailable, Q.servicepostofficeid, Q.tolerance, Q.state FROM QUEUE AS Q INNER JOIN user_post_office AS UPO ON (Q.servicepostofficeid= UPO.servicepostofficeid) WHERE UPO.userid = ? AND Q.state = 1::BIT;"
        const val SELECT_QUEUE = "SELECT * FROM QUEUE WHERE id = ? AND state = 1::BIT;"

        const val UPDATE_SERVICE = "UPDATE SERVICE SET name = ?, description = ? WHERE id = ?;"
        const val UPDATE_SERVICE_POST_OFFICE =
            "UPDATE SERVICE_POSTOFFICE SET latitude = ?, longitude = ?, description = ?, address = ? WHERE id = ?;"
        const val UPDATE_QUEUE = "UPDATE QUEUE SET activeservers = ?, letter = ?, name = ?, description = ?, type = ?, maxavailable = ?, tolerance = ? WHERE id = ?"

        const val INSERT_SERVICE = "INSERT INTO SERVICE(name, description) VALUES (?,?);"
        const val INSERT_POST_OFFICE =
            "INSERT INTO SERVICE_POSTOFFICE (latitude, longitude, description, serviceid, address) VALUES \n" +
                    "(?, ?, ?, ?, ?);"
        const val INSERT_POST_OFFICE_QUEUE =
            "INSERT INTO QUEUE (name, description, letter, type, activeServers, maxAvailable, servicePostOfficeId, tolerance) VALUES \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?);"

        const val DELETE_SERVICE = "UPDATE SERVICE SET state = 0::BIT WHERE id = ?;"
        const val DELETE_SERVICE_POSTOFFICE = "UPDATE SERVICE_POSTOFFICE SET state = 0::BIT WHERE id = ?;"
        const val DELETE_SERVICE_POSTOFFICE_QUEUE = "UPDATE QUEUE SET state = 0::BIT WHERE id = ?;"
    }
}