package isel.meic.tfm.fei.controlAccess

import org.springframework.http.HttpMethod

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class RequestParams(val method: String, val path : String)