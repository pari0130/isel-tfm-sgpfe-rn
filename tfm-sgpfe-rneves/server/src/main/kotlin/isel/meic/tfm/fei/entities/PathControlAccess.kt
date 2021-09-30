package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class PathControlAccess(val method: String, val regex: String, val roles: Collection<String>)