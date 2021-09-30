package isel.meic.tfm.fei.entities

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves 39643
 * @mentor: Paulo Pereira
 *
 * Represents the entry of the Db Ticket table
 */
data class Ticket(val id: Int, var number: Int, val letter : String, val serviceId: Int, val name: String, val postOfficeId : Int)
