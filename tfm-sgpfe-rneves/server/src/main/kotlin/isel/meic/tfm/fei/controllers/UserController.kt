package isel.meic.tfm.fei.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.code.siren4j.component.Entity
import com.google.code.siren4j.converter.ReflectingConverter
import com.sun.org.apache.xpath.internal.operations.Bool
import isel.meic.tfm.fei.contract.services.IUserService
import isel.meic.tfm.fei.dto.*
import isel.meic.tfm.fei.entities.Role
import isel.meic.tfm.fei.entities.Session
import isel.meic.tfm.fei.entities.UserExists
import isel.meic.tfm.fei.entities.UserInformation
import isel.meic.tfm.fei.interceptor.ControlAccessInterceptor
import isel.meic.tfm.fei.outputmodel.collection.RoleCollectionOutputModel
import isel.meic.tfm.fei.outputmodel.single.*
import isel.meic.tfm.fei.utils.Constants
import isel.meic.tfm.fei.utils.Utils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
@CrossOrigin
@RestController
class UserController @Autowired constructor(private val userService: IUserService) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    init {
        log.info("User controller was constructed")
    }

    companion object {
        fun adaptSessionResponseToOutputModel(session: Session?): SessionOutputModel {
            return SessionOutputModel(
                session != null,
                session?.userId ?: 0,
                session?.token ?: "",
                session?.createdAt,
                session?.role ?: "NONE",
                session?.name ?: "NONE"
            )
        }

        fun adaptUserInformationToOutputModel(userInformation: UserInformation): UserInformationOutputModel {
            return UserInformationOutputModel(
                userInformation.id,
                userInformation.username,
                userInformation.nif,
                userInformation.phone,
                userInformation.notification,
                userInformation.name
            )
        }

        fun adaptRoleToOutputModel(role: Role): RoleOutputModel {
            return RoleOutputModel(role.role)
        }

        fun adaptRegisterToOutputModel(success: Boolean, userExists: UserExists?): RegisterOutputModel {
            return RegisterOutputModel(
                success,
                userExists?.username ?: false,
                userExists?.email ?: false
            )
        }
    }

    @RequestMapping(value = ["/login"], method = [RequestMethod.POST], produces = [Constants.MediaType.JSON_SIREN])
    fun login(@RequestBody body: String): HttpEntity<Entity> {
        val userParams = ObjectMapper().readValue(body, UserDTO::class.java)
        val result = adaptSessionResponseToOutputModel(userService.login(userParams))
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/fcmtoken"], method = [RequestMethod.PUT], produces = [Constants.MediaType.JSON_SIREN])
    fun fcmTokenRegistration(@RequestBody body: String): HttpEntity<Entity> {
        val fcmParams = ObjectMapper().readValue(body, FCMTokenRegistration::class.java)
        userService.registerFcmToken(fcmParams.userId, fcmParams.fcm_token)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/isValid"], method = [RequestMethod.GET], produces = [Constants.MediaType.JSON_SIREN])
    fun isValid(@RequestHeader(ControlAccessInterceptor.AUTHORIZATION) authorization: String): HttpEntity<Entity> {
        val accessToken = authorization.removePrefix(ControlAccessInterceptor.BEARER_PREFIX)
        val result = SessionValidOutputModel(userService.isSessionValid(accessToken) != null)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/logout"], method = [RequestMethod.POST], produces = [Constants.MediaType.JSON_SIREN])
    fun logout(@RequestHeader(ControlAccessInterceptor.AUTHORIZATION) authorization: String): HttpEntity<Entity> {
        val accessToken = authorization.removePrefix(ControlAccessInterceptor.BEARER_PREFIX)
        userService.logout(accessToken)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/location"], method = [RequestMethod.PUT], produces = [Constants.MediaType.JSON_SIREN])
    fun updateLocation(@RequestBody body: String): HttpEntity<Entity> {
        val locationParams = ObjectMapper().readValue(body, LocationDTO::class.java)
        //the userId can be obtained using the token
        userService.updateUserLocation(locationParams.latitude, locationParams.longitude, locationParams.userId)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/account"], method = [RequestMethod.PUT], produces = [Constants.MediaType.JSON_SIREN])
    fun updateUserInformation(@RequestBody body: String): HttpEntity<Entity> {
        val userInfoParams = ObjectMapper().readValue(body, UserInformationDTO::class.java)
        userService.editUserInformation(
            userInfoParams.id,
            userInfoParams.name,
            userInfoParams.phone,
            userInfoParams.notification
        )
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/account"], method = [RequestMethod.GET], produces = [Constants.MediaType.JSON_SIREN])
    fun getUserInformation(@RequestHeader(ControlAccessInterceptor.AUTHORIZATION) authorization: String): HttpEntity<Entity> {
        val accessToken = authorization.removePrefix(ControlAccessInterceptor.BEARER_PREFIX)
        val userId = userService.getUserIdFromAccessToken(accessToken)
        if (userId == -1)
            return ResponseEntity.notFound().build()
        val result = adaptUserInformationToOutputModel(userService.getUserInformation(userId)!!)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST], produces = [Constants.MediaType.JSON_SIREN])
    fun register(@RequestBody body: String): HttpEntity<Entity> {
        val userParams = ObjectMapper().readValue(body, UserRegisterDTO::class.java)
        val register = userService.registerUser(
            userParams.username,
            userParams.password,
            userParams.name,
            userParams.email,
            userParams.phone,
            isel.meic.tfm.fei.controlAccess.Role.USER.name,
            userParams.notification
        )
        val result = adaptRegisterToOutputModel(register.first, register.second)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/roles"], method = [RequestMethod.GET], produces = [Constants.MediaType.JSON_SIREN])
    fun getRoles(): HttpEntity<Entity> {
        val roles = userService.getRoles().map { adaptRoleToOutputModel(it) }
        val result = RoleCollectionOutputModel(roles)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/serving"], method = [RequestMethod.PUT], produces = [Constants.MediaType.JSON_SIREN])
    fun updateServingQueue(@RequestBody body: String): HttpEntity<Entity> {
        val servingParams = ObjectMapper().readValue(body, ServingDTO::class.java)
        val result: Boolean = if (servingParams.serving)
            userService.servingQueue(servingParams.queueId)
        else userService.leaveQueue(servingParams.queueId)
        val resultOM = SuccessOutputModel(result)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(resultOM)))
    }
}