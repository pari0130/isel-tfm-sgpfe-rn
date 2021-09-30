package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class QueueState(
    val stateNumber: Int,
    val attendedNumber: Int,
    val letter: String,
    val queueId: Int,
    val name: String,
    val postId : Int,
    val forecast: Double
)