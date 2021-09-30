package isel.meic.tfm.fei.services

import isel.meic.tfm.fei.contract.dal.IUserDAL
import isel.meic.tfm.fei.contract.services.IUserService
import isel.meic.tfm.fei.dto.UserDTO
import isel.meic.tfm.fei.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Service
class UserService @Autowired constructor(private val userDAL: IUserDAL) : IUserService {

    override fun login(user: UserDTO): Session? {
        return this.userDAL.login(user)
    }

    override fun registerFcmToken(userId: Int, token: String): Boolean {
        return this.userDAL.registerFcmToken(userId, token)
    }

    override fun logout(token: String): Boolean {
        return this.userDAL.logout(token)
    }

    override fun isSessionValid(token: String): Session? {
        return this.userDAL.isSessionValid(token)
    }

    override fun updateUserLocation(latitude: Double, longitude: Double, userId: Int): Boolean {
        return this.userDAL.updateUserLocation(latitude, longitude, userId)
    }

    override fun getUserLocation(userId: Int): Location? {
        return this.userDAL.getUserLocation(userId)
    }

    override fun editUserInformation(
        userId: Int,
        name: String,
        phone: Int,
        notification: Boolean
    ): Boolean {
        return this.userDAL.editUserInformation(userId, name, phone, notification)
    }

    override fun getUserInformation(userId: Int): UserInformation? {
        return this.userDAL.getUserInformation(userId)
    }

    override fun registerUser(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        role: String,
        notification: Boolean
    ): Pair<Boolean, UserExists?> {
        return this.userDAL.registerUser(username, password, name, email, phone, role, notification)
    }

    override fun changePassword(userId: Int, oldPassword: String, newPassword: String): Boolean {
        return this.userDAL.changePassword(userId, oldPassword, newPassword)
    }

    override fun servingQueue(queueId: Int): Boolean {
        return this.userDAL.servingQueue(queueId)
    }

    override fun leaveQueue(queueId: Int): Boolean {
        return this.userDAL.leaveQueue(queueId)
    }

    override fun getUserIdFromAccessToken(token: String): Int {
        return this.userDAL.getUserIdFromAccessToken(token)
    }

    override fun getRoles(): Collection<Role> {
        return this.userDAL.getRoles()
    }
}