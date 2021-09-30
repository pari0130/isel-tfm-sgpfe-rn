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
@Siren4JEntity(entityClass = ["register"])
class RegisterOutputModel : BaseResource {

    val success: Boolean
    val username: Boolean
    val email: Boolean

    @JsonCreator
    constructor(
        @JsonProperty("success") success: Boolean,
        @JsonProperty("username") username: Boolean,
        @JsonProperty("email") email: Boolean
    ) {
        this.success = success
        this.username = username
        this.email = email
    }
}