package isel.meic.tfm.fei.outputmodel.collection

import com.google.code.siren4j.annotations.Siren4JEntity
import com.google.code.siren4j.annotations.Siren4JProperty
import com.google.code.siren4j.component.Entity
import com.google.code.siren4j.converter.ReflectingConverter
import com.google.code.siren4j.resource.BaseResource
import isel.meic.tfm.fei.outputmodel.single.QueueOutputModel
import isel.meic.tfm.fei.utils.Utils

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Siren4JEntity(entityClass = ["collection", "queues"])
class QueueCollectionOutputModel : BaseResource {

    @Siren4JProperty
    val queues: Collection<Entity>

    constructor(queues: Collection<QueueOutputModel>) {
        this.queues =
            queues.map(ReflectingConverter.newInstance()::toEntity).map { Utils.removeSirenClassFromJson(it) }
    }
}