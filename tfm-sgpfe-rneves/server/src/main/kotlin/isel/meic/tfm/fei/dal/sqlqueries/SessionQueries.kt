package isel.meic.tfm.fei.dal.sqlqueries

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class SessionQueries {
    companion object {
        const val SELECT_SESSION_BY_TOKEN = "SELECT S.id, S.userid, S.token, S.createdat, UR.role FROM SESSION AS S INNER JOIN USER_ROLE as UR ON (S.userid = UR.userid) WHERE S.token = ?;"
        const val UPDATE_SESSION = "UPDATE SESSION SET token = null WHERE token = ?;"
        const val INSERT_SESSION = "INSERT INTO SESSION (userId, token) VALUES (?,?)"
    }
}