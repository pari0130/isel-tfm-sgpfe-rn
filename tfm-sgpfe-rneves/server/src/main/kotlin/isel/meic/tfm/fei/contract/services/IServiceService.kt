package isel.meic.tfm.fei.contract.services

import isel.meic.tfm.fei.entities.Queue
import isel.meic.tfm.fei.entities.Service
import isel.meic.tfm.fei.entities.ServicePostOffice

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
interface IServiceService {

    fun getServices(): Collection<Service>
    fun getServicePostOffices(serviceId: Int): Collection<ServicePostOffice>
    fun getService(id: Int, accessToken : String): Service?
    fun getServicePostOffice(id: Int): ServicePostOffice?
    fun getPostOfficeQueues(postOfficeId: Int, accessToken : String): Collection<Queue>
    fun getPostOfficeQueue(id: Int): Queue?

    fun updateService(id: Int, name: String, description: String): Boolean
    fun updatePostOffice(
        id: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        address: String
    ): Boolean

    fun updateQueue(
        name: String,
        description: String,
        letter: String,
        activeServers: Int,
        type: Int,
        maxAvailable: Int,
        id: Int,
        tolerance: Boolean
    ): Boolean

    fun createService(name: String, description: String): Boolean
    fun createPostOffice(
        serviceId: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        address: String
    ): Boolean

    fun createPostOfficeQueue(
        name: String,
        description: String,
        letter: String,
        type: Int,
        activeServers: Int,
        maxAvailable: Int,
        servicePostOfficeId: Int,
        tolerance: Boolean
    ): Boolean

    fun deleteService(id: Int): Boolean
    fun deletePostOffice(id: Int): Boolean
    fun deleteQueue(id: Int): Boolean
}