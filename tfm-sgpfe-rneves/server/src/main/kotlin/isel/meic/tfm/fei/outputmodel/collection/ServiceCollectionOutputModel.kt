package isel.meic.tfm.fei.outputmodel.collection

import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.annotations.Siren4JProperty
import com.google.code.siren4j.component.Entity
import com.google.code.siren4j.converter.ReflectingConverter
import com.google.code.siren4j.resource.BaseResource
import isel.meic.tfm.fei.outputmodel.single.ServiceOutputModel
import isel.meic.tfm.fei.utils.Utils

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Siren4JEntity(entityClass = ["collection", "service"])
class ServiceCollectionOutputModel : BaseResource {

    @Siren4JProperty
    val services: Collection<Entity>

    constructor(services: Collection<ServiceOutputModel>) {
        this.services =
            services.map(ReflectingConverter.newInstance()::toEntity).map { Utils.removeSirenClassFromJson(it) }
    }
}