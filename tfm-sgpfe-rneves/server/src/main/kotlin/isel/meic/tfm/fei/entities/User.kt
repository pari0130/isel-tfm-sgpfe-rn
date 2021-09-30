package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 */
data class User(val userId : Int, val username: String, val password: String, val salt : String, val role: String, val name: String)
