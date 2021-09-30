package isel.meic.tfm.fei.outputmodel.collection

import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.annotations.Siren4JProperty
import com.google.code.siren4j.component.Entity
import com.google.code.siren4j.converter.ReflectingConverter
import isel.meic.tfm.fei.outputmodel.single.ServicePostOfficeOutputModel
import isel.meic.tfm.fei.utils.Utils

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Siren4JEntity(entityClass = ["collection", "service_postoffice"])
class ServicePostOfficeCollectionOutputModel {

    @Siren4JProperty
    val servicesPostOffices: Collection<Entity>

    constructor(services: Collection<ServicePostOfficeOutputModel>) {
        this.servicesPostOffices =
            services.map(ReflectingConverter.newInstance()::toEntity).map { Utils.removeSirenClassFromJson(it) }
    }
}