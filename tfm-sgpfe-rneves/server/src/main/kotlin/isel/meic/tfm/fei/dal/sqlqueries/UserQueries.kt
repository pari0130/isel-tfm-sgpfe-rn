package isel.meic.tfm.fei.dal.sqlqueries

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class UserQueries {

    companion object{
        const val SELECT_USER_FOR_LOGIN = "SELECT U.id, U.username, U.password, U.salt, UR.role, U.name FROM FEI_USER as U INNER JOIN USER_ROLE as UR ON (U.id = UR.userId) WHERE username = ?;"
        const val SELECT_USER_LOCATION = "SELECT * FROM LOCATION WHERE userId = ?;"
        const val SELECT_USER_BY_USER_ID = "SELECT U.id, U.username, U.password, U.salt, UR.role, U.name FROM FEI_USER as U INNER JOIN USER_ROLE as UR ON (U.id = UR.userId) WHERE U.id = ?;"


        const val SELECT_USER_INFORMATION = "SELECT id, username, name, nif, phone, notifications FROM FEI_USER WHERE id = ?;"
        const val UPDATE_USER_INFORMATION = "UPDATE FEI_USER SET name = ?, phone = ?, notifications = ? WHERE id = ?"
        
        const val UPDATE_USER_LOCATION = "UPDATE LOCATION SET latitude = ?, longitude = ?, updatedAt = NOW() WHERE userId = ?;"
        const val UPDATE_USER_FCM_TOKEN = "UPDATE FEI_USER SET fcm_token = ? WHERE id = ?;"
        const val CHANGE_USER_PASSWORD = "UPDATE FEI_USER SET password = ? AND salt = ? WHERE id = ?;"

        const val SERVING_QUEUE = "UPDATE QUEUE SET activeservers = activeservers + 1 WHERE id = ?;"
        const val LEAVE_QUEUE = "UPDATE QUEUE SET activeservers = activeservers - 1 WHERE id = ?;"

        const val VERIFY_IF_EXISTS = "select username, email from fei_user where username = ? or email = ?;"
        const val INSERT_USER = "INSERT INTO FEI_USER (username, password, salt, name, email, nif, phone, notifications) VALUES (?,?,?,?,?,?,?,?);"
        const val INSERT_USER_ROLE = "INSERT INTO USER_ROLE (userid, role) VALUES (?,?);"

        const val SELECT_ROLES = "select role from ROLES;"
        const val SELECT_PATH_CONTROL_ACCESS = "select * from path_control_access;"
    }
}