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
@Siren4JEntity(entityClass = ["userInformation"])
class UserInformationOutputModel : BaseResource {
    val id: Int
    val username: String
    val name: String
    val nif: Int
    val phone: Int
    val notification: Boolean

    @JsonCreator
    constructor(
        @JsonProperty("id") id: Int,
        @JsonProperty("username") username: String,
        @JsonProperty("nif") nif: Int,
        @JsonProperty("phone") phone: Int,
        @JsonProperty("notification") notification: Boolean,
        @JsonProperty("name") name: String
    ) {
        this.id = id
        this.username = username
        this.nif = nif
        this.phone = phone
        this.name = name
        this.notification = notification
    }
}