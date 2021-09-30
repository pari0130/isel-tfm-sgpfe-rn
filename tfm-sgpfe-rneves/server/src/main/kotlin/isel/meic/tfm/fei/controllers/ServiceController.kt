package isel.meic.tfm.fei.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.code.siren4j.component.Entity
import com.google.code.siren4j.converter.ReflectingConverter
import isel.meic.tfm.fei.contract.services.IServiceService
import isel.meic.tfm.fei.dto.PostOfficeDTO
import isel.meic.tfm.fei.dto.QueueDTO
import isel.meic.tfm.fei.dto.ServiceDTO
import isel.meic.tfm.fei.entities.Queue
import isel.meic.tfm.fei.entities.Service
import isel.meic.tfm.fei.entities.ServicePostOffice
import isel.meic.tfm.fei.interceptor.ControlAccessInterceptor
import isel.meic.tfm.fei.outputmodel.collection.QueueCollectionOutputModel
import isel.meic.tfm.fei.outputmodel.collection.ServiceCollectionOutputModel
import isel.meic.tfm.fei.outputmodel.collection.ServicePostOfficeCollectionOutputModel
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
 *
 * <Description>
 */
@RestController
@CrossOrigin
@RequestMapping("/service")
class ServiceController @Autowired constructor(private val serviceService: IServiceService) {

    private val log = LoggerFactory.getLogger(ServiceController::class.java)

    companion object {
        fun adaptServiceToOutputModel(service: Service): ServiceOutputModel {
            return ServiceOutputModel(service.id, service.name, service.description)
        }

        fun adaptServicePostOfficesToOutputModel(servicePostOffice: ServicePostOffice): ServicePostOfficeOutputModel {
            return ServicePostOfficeOutputModel(
                servicePostOffice.id,
                servicePostOffice.serviceId,
                servicePostOffice.latitude,
                servicePostOffice.longitude,
                servicePostOffice.description,
                servicePostOffice.address
            )
        }

        fun adaptServicePostOfficeQueuesToOutputModel(queue: Queue): QueueOutputModel {
            return QueueOutputModel(
                queue.id, queue.name,
                queue.activeServers,
                queue.type,
                queue.maxAvailable,
                queue.servicePostOfficeId,
                queue.description,
                queue.letter,
                queue.tolerance
            )
        }
    }

    init {
        log.info("Service controller was constructed")
    }

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.GET], produces = [Constants.MediaType.JSON_SIREN])
    @ResponseBody
    fun getServices(): HttpEntity<Entity> {
        val services = serviceService.getServices().map(Companion::adaptServiceToOutputModel)
        val serviceCollectionOutputModel = ServiceCollectionOutputModel(services)
        return ResponseEntity.ok(
            Utils.removeSirenClassFromJson(
                ReflectingConverter.newInstance().toEntity(
                    serviceCollectionOutputModel
                )
            )
        )
    }

    @RequestMapping(value = ["/{serviceId}"], method = [RequestMethod.GET], produces = [Constants.MediaType.JSON_SIREN])
    @ResponseBody
    fun getService(
        @PathVariable("serviceId") serviceId: Int,
        @RequestHeader(ControlAccessInterceptor.AUTHORIZATION) authorization: String
    ): HttpEntity<Entity> {
        val accessToken = authorization.removePrefix(ControlAccessInterceptor.BEARER_PREFIX)
        val service = serviceService.getService(serviceId, accessToken)
        if (service != null)
            return ResponseEntity.ok(
                Utils.removeSirenClassFromJson(
                    ReflectingConverter.newInstance().toEntity(
                        adaptServiceToOutputModel(service)
                    )
                )
            )

        val errorPair = Constants.ErrorType.SERVICE_DOES_NOT_EXIST
        val error = ErrorOutputModel(errorPair.first, errorPair.second)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(error)))
    }

    @RequestMapping(
        value = ["/{serviceId}/postoffices"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getServicePostOffices(@PathVariable("serviceId") serviceId: Int): HttpEntity<Entity> {
        val servicePostOffices =
            serviceService.getServicePostOffices(serviceId).map(Companion::adaptServicePostOfficesToOutputModel)
        val servicePostOfficeCollectionOutputModel = ServicePostOfficeCollectionOutputModel(servicePostOffices)
        return ResponseEntity.ok(
            Utils.removeSirenClassFromJson(
                ReflectingConverter.newInstance().toEntity(
                    servicePostOfficeCollectionOutputModel
                )
            )
        )
    }

    @RequestMapping(
        value = ["/postoffice/{postOfficeId}"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getPostOffice(@PathVariable("postOfficeId") postOfficeId: Int): HttpEntity<Entity> {
        val servicePostOffice = serviceService.getServicePostOffice(postOfficeId)
        if (servicePostOffice != null)
            return ResponseEntity.ok(
                Utils.removeSirenClassFromJson(
                    ReflectingConverter.newInstance().toEntity(
                        adaptServicePostOfficesToOutputModel(servicePostOffice)
                    )
                )
            )

        val errorPair = Constants.ErrorType.SERVICE_DOES_NOT_EXIST
        val error = ErrorOutputModel(errorPair.first, errorPair.second)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(error)))
    }

    @RequestMapping(
        value = ["/postoffice/{postOfficeId}/queues"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getPostOfficeQueues(
        @PathVariable("postOfficeId") postOfficeId: Int,
        @RequestHeader(ControlAccessInterceptor.AUTHORIZATION) authorization: String
    ): HttpEntity<Entity> {
        val accessToken = authorization.removePrefix(ControlAccessInterceptor.BEARER_PREFIX)
        val queues =
            serviceService.getPostOfficeQueues(postOfficeId, accessToken)
                .map(Companion::adaptServicePostOfficeQueuesToOutputModel)
        val queueCollectionOutputModel = QueueCollectionOutputModel(queues)
        return ResponseEntity.ok(
            Utils.removeSirenClassFromJson(
                ReflectingConverter.newInstance().toEntity(
                    queueCollectionOutputModel
                )
            )
        )
    }

    @RequestMapping(
        value = ["/postoffice/queue/{queueId}"],
        method = [RequestMethod.GET],
        produces = [Constants.MediaType.JSON_SIREN]
    )
    @ResponseBody
    fun getQueue(@PathVariable("queueId") queueId: Int): HttpEntity<Entity> {
        val queue = serviceService.getPostOfficeQueue(queueId)
        if (queue != null)
            return ResponseEntity.ok(
                Utils.removeSirenClassFromJson(
                    ReflectingConverter.newInstance().toEntity(
                        adaptServicePostOfficeQueuesToOutputModel(queue)
                    )
                )
            )

        val errorPair = Constants.ErrorType.QUEUE_DOES_NOT_EXIST
        val error = ErrorOutputModel(errorPair.first, errorPair.second)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(error)))
    }

    /***************************************************** POST *******************************************************/

    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    fun createService(@RequestBody body: String): HttpEntity<Entity> {
        val newService = ObjectMapper().readValue(body, ServiceDTO::class.java)
        serviceService.createService(newService.name, newService.description)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/postoffice"], method = [RequestMethod.POST])
    fun createServicePostOffice(@RequestBody body: String): HttpEntity<Entity> {
        val newPostOffice = ObjectMapper().readValue(body, PostOfficeDTO::class.java)
        serviceService.createPostOffice(
            newPostOffice.serviceId,
            newPostOffice.latitude,
            newPostOffice.longitude,
            newPostOffice.description,
            newPostOffice.address
        )
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/postoffice/queue"], method = [RequestMethod.POST])
    fun createServicePostOfficeQueue(@RequestBody body: String): HttpEntity<Entity> {
        val newQueue = ObjectMapper().readValue(body, QueueDTO::class.java)
        serviceService.createPostOfficeQueue(
            newQueue.name,
            newQueue.description,
            newQueue.letter,
            newQueue.type,
            newQueue.activeServers,
            newQueue.maxAvailable,
            newQueue.servicePostOfficeId,
            newQueue.tolerance
        )
        //TODO verify is success

        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    /***************************************************** PUT ********************************************************/

    /***************************************** Update *****************************************/

    @RequestMapping(value = ["/{id}/update"], method = [RequestMethod.PUT])
    fun updateService(@PathVariable("id") id: Int, @RequestBody body: String): HttpEntity<Entity> {
        val serviceDTO = ObjectMapper().readValue(body, ServiceDTO::class.java)
        serviceService.updateService(id, serviceDTO.name, serviceDTO.description)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/postoffice/{id}/update"], method = [RequestMethod.PUT])
    fun updateServicePostOffice(@PathVariable("id") id: Int, @RequestBody body: String): HttpEntity<Entity> {
        val postOfficeDTO = ObjectMapper().readValue(body, PostOfficeDTO::class.java)
        serviceService.updatePostOffice(
            id,
            postOfficeDTO.latitude,
            postOfficeDTO.longitude,
            postOfficeDTO.description,
            postOfficeDTO.address
        )
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/postoffice/queue/{id}/update"], method = [RequestMethod.PUT])
    fun updateServicePostOfficeQueue(@PathVariable("id") id: Int, @RequestBody body: String): HttpEntity<Entity> {
        val queueDTO = ObjectMapper().readValue(body, QueueDTO::class.java)
        serviceService.updateQueue(
            queueDTO.name,
            queueDTO.description,
            queueDTO.letter,
            queueDTO.activeServers,
            queueDTO.type,
            queueDTO.maxAvailable,
            id,
            queueDTO.tolerance
        )
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    /***************************************** Delete *****************************************/

    @RequestMapping(value = ["/{id}/delete"], method = [RequestMethod.PUT])
    fun deleteService(@PathVariable("id") id: Int): HttpEntity<Entity> {
        serviceService.deleteService(id)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["/postoffice/{id}/delete"], method = [RequestMethod.PUT])
    fun deleteServicePostOffice(@PathVariable("id") id: Int): HttpEntity<Entity> {
        serviceService.deletePostOffice(id)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }

    @RequestMapping(value = ["postoffice/queue/{id}/delete"], method = [RequestMethod.PUT])
    fun deleteServicePostOfficeQueue(@PathVariable("id") id: Int): HttpEntity<Entity> {
        serviceService.deleteQueue(id)
        //TODO verify is success
        val result = SuccessOutputModel(true)
        return ResponseEntity.ok(Utils.removeSirenClassFromJson(ReflectingConverter.newInstance().toEntity(result)))
    }
}