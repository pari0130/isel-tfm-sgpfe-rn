package isel.meic.tfm.fei.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 *
 * <Description>
 */

//TODO to delete maybe, i don't think i need this //use them like normal data classes

@Entity(tableName = "ticket")
data class Ticket(
    @ColumnInfo(name = "ticket_id") @PrimaryKey val ticketId: Int,
    @ColumnInfo(name = "ticketLetter") val ticketLetter: String,
    @ColumnInfo(name = "ticketNumber") val ticketNumber: Int,
    @ColumnInfo(name = "queueId") val queueId: Int,
    @ColumnInfo(name = "name") val name: String
) : Serializable

@Entity(tableName = "ticket_attended")
data class TicketAttended(
    @ColumnInfo(name = "ticket_id") @PrimaryKey val ticketId: Int,
    @ColumnInfo(name = "ticketLetter") val ticketLetter: String,
    @ColumnInfo(name = "ticketNumber") val ticketNumber: Int,
    @ColumnInfo(name = "queueId") val serviceId: Int,
    @ColumnInfo(name = "name") val name: String
)

@Entity(tableName = "service")
data class Service(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
) : Serializable

@Entity(tableName = "servicePostOffice")
data class ServicePostOffice(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "serviceId") val serviceId: Int,
    @ColumnInfo(name = "address") val address: String
) : Serializable

@Entity(tableName = "queueState")
data class QueueState(
    @ColumnInfo(name = "queueId") @PrimaryKey val queueId: Int,
    @ColumnInfo(name = "stateNumber") val stateNumber: Int,
    @ColumnInfo(name = "attendedNumber") val attendedNumber: Int,
    @ColumnInfo(name = "letter") val letter: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "forecast") var forecast: Int
) : Serializable

@Entity(tableName = "UserInformation")
data class UserInformation(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: Int,
    @ColumnInfo(name = "notification") val notification: Boolean
) : Serializable