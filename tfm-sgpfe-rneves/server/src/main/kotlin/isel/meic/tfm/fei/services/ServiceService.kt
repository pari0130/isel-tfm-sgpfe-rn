package isel.meic.tfm.fei.services

import isel.meic.tfm.fei.contract.dal.IServiceDAL
import isel.meic.tfm.fei.contract.dal.ISessionDAL
import isel.meic.tfm.fei.contract.dal.IUserDAL
import isel.meic.tfm.fei.contract.services.IServiceService
import isel.meic.tfm.fei.entities.Queue
import isel.meic.tfm.fei.entities.ServicePostOffice
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
class ServiceService @Autowired constructor(private val serviceDAL: IServiceDAL, private val sessionDAL: ISessionDAL) : IServiceService {

    override fun getServices(): Collection<isel.meic.tfm.fei.entities.Service> {
        return serviceDAL.getServices()
    }

    override fun getServicePostOffices(serviceId: Int): Collection<ServicePostOffice> {
        return serviceDAL.getServicePostOffices(serviceId)
    }

    override fun getService(id: Int, accessToken: String): isel.meic.tfm.fei.entities.Service? {
        if(id != 0)
            return serviceDAL.getService(id)
        val userId = sessionDAL.getUserIdFromAccessToken(accessToken)
        return serviceDAL.getServiceForUser(userId)
    }

    override fun getServicePostOffice(id: Int): ServicePostOffice? {
        return serviceDAL.getServicePostOffice(id)
    }

    override fun getPostOfficeQueues(postOfficeId: Int, accessToken: String): Collection<Queue> {
        if(postOfficeId != 0)
            return serviceDAL.getPostOfficeQueues(postOfficeId)
        val userId = sessionDAL.getUserIdFromAccessToken(accessToken)
        return serviceDAL.getPostOfficeQueuesForUser(userId)
    }

    override fun getPostOfficeQueue(id: Int): Queue? {
        return serviceDAL.getPostOfficeQueue(id)
    }

    override fun updateService(id: Int, name: String, description: String): Boolean {
        return serviceDAL.updateService(id, name, description)
    }

    override fun updatePostOffice(
        id: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        address: String
    ): Boolean {
        return serviceDAL.updatePostOffice(id, latitude, longitude, description, address)
    }

    override fun updateQueue(
        name: String,
        description: String,
        letter: String,
        activeServers: Int,
        type: Int,
        maxAvailable: Int,
        id: Int,
        tolerance: Boolean
    ): Boolean {
        return serviceDAL.updateQueue(
            name,
            description,
            letter,
            activeServers,
            type,
            maxAvailable,
            id,
            tolerance
        )
    }

    override fun createService(name: String, description: String): Boolean {
        return serviceDAL.createService(name, description)
    }

    override fun createPostOffice(
        serviceId: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        address: String
    ): Boolean {
        return serviceDAL.createPostOffice(serviceId, latitude, longitude, description, address)
    }

    override fun createPostOfficeQueue(
        name: String,
        description: String,
        letter: String,
        type: Int,
        activeServers: Int,
        maxAvailable: Int,
        servicePostOfficeId: Int,
        tolerance: Boolean
    ): Boolean {
        return serviceDAL.createPostOfficeQueue(
            name,
            description,
            letter,
            type,
            activeServers,
            maxAvailable,
            servicePostOfficeId,
            tolerance
        )
    }

    override fun deleteService(id: Int): Boolean {
        return serviceDAL.deleteService(id)
    }

    override fun deletePostOffice(id: Int): Boolean {
        return serviceDAL.deletePostOffice(id)
    }

    override fun deleteQueue(id: Int): Boolean {
        return serviceDAL.deleteQueue(id)
    }
}