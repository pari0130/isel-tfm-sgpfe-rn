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
@Siren4JEntity(entityClass = ["error"])
class ErrorOutputModel : BaseResource {

    val errorId: Int
    val errorMessage: String

    @JsonCreator
    constructor(@JsonProperty("errorId") errorId: Int,
                @JsonProperty("errorMessage") errorMessage: String) {
        this.errorId = errorId
        this.errorMessage = errorMessage
    }
}