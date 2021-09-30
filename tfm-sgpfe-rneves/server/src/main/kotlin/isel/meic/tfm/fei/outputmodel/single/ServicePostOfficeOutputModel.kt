package isel.meic.tfm.fei.outputmodel.single

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.resource.BaseResource

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Siren4JEntity(entityClass = ["service_postoffice"])
class ServicePostOfficeOutputModel : BaseResource {
    val id: Int
    val serviceId: Int
    val latitude: Double
    val longitude: Double
    val description: String
    val address: String

    @JsonCreator
    constructor(
        @JsonProperty("id") id: Int,
        @JsonProperty("serviceId") serviceId: Int,
        @JsonProperty("latitude") latitude: Double,
        @JsonProperty("longitude") longitude: Double,
        @JsonProperty("description") description: String,
        @JsonProperty("address") address: String
    ) {
        this.id = id
        this.serviceId = serviceId
        this.latitude = latitude
        this.longitude = longitude
        this.description = description
        this.address = address
    }
}