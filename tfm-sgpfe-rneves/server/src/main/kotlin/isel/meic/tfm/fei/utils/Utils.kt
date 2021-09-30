package isel.meic.tfm.fei.utils

import com.google.code.siren4j.component.Entity

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class Utils {

    companion object{
        fun removeSirenClassFromJson(entity: Entity) : Entity {
            entity.properties.remove(Constants.SIREN_CLASS)
            return entity
        }
    }
}