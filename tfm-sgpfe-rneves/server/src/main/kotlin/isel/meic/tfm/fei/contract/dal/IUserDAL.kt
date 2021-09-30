package isel.meic.tfm.fei.contract.dal

import isel.meic.tfm.fei.dto.UserDTO
import isel.meic.tfm.fei.entities.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface IUserDAL {
    fun login(user: UserDTO): Session?
    fun registerFcmToken(userId: Int, token: String): Boolean
    fun logout(token: String): Boolean
    fun isSessionValid(token: String): Session?

    fun updateUserLocation(latitude: Double, longitude: Double, userId: Int): Boolean
    fun getUserLocation(userId: Int): Location?

    fun editUserInformation(userId: Int, name: String, phone: Int, notification: Boolean): Boolean
    fun getUserInformation(userId: Int): UserInformation?

    fun registerUser(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        role: String,
        notification: Boolean
    ): Pair<Boolean, UserExists?>

    fun changePassword(userId: Int, oldPassword: String, newPassword: String): Boolean

    fun servingQueue(queueId: Int): Boolean
    fun leaveQueue(queueId: Int): Boolean

    fun getUserIdFromAccessToken(token: String): Int

    fun getRoles() : Collection<Role>

    fun getAllPathControlAccess(): Collection<PathControlAccess>
}