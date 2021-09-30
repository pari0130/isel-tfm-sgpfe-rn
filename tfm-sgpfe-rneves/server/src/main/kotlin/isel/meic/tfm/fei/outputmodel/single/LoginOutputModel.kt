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
 */
@Siren4JEntity(entityClass = ["login"])
class LoginOutputModel : BaseResource {

    val authenticated: Boolean
    val username: String
    val userid : Int

    @JsonCreator
    constructor(@JsonProperty("authenticated") authenticated: Boolean,
                @JsonProperty("username") username: String,
                @JsonProperty("userid") userid: Int) {
        this.authenticated = authenticated
        this.username = username
        this.userid = userid
    }
}