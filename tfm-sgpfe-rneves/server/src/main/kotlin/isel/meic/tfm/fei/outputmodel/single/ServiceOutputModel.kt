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
@Siren4JEntity(entityClass = ["service"])
class ServiceOutputModel : BaseResource {

    val id: Int
    val name: String
    val description: String

    //todo remove the JsonProperty - i don't need it when it's a output model. i think
    @JsonCreator
    constructor(
        @JsonProperty("id") id: Int,
        @JsonProperty("name") name: String,
        @JsonProperty("description") description: String
    ) {
        this.id = id
        this.name = name
        this.description = description
    }
}