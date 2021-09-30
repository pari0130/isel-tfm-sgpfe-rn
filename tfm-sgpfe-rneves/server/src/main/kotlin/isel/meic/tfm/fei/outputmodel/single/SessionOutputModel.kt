package isel.meic.tfm.fei.outputmodel.single

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.api.client.util.DateTime
import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.resource.BaseResource
import java.sql.Timestamp

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Siren4JEntity(entityClass = ["session"])
class SessionOutputModel : BaseResource {

    val authenticated: Boolean
    val userId: Int
    val token: String
    val createdAt: Timestamp?
    val role: String
    val name: String

    @JsonCreator
    constructor(
        @JsonProperty("authenticated") authenticated: Boolean,
        @JsonProperty("userId") userId: Int,
        @JsonProperty("token") token: String,
        @JsonProperty("createdAt") createdAt: Timestamp?,
        @JsonProperty("role") role: String,
        @JsonProperty("name") name: String
    ) {
        this.authenticated = authenticated
        this.userId = userId
        this.token = token
        this.createdAt = createdAt
        this.role = role
        this.name = name
    }
}
