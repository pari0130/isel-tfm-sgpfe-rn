package isel.meic.tfm.fei.model

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Dao
interface TicketDAO {
    @Query("SELECT * FROM ticket")
    fun getAll(): List<Ticket>

    @Query("SELECT * FROM ticket WHERE ticket_id = :ticketId")
    fun findById(ticketId: Int): Ticket

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tickets: Ticket)

    @Delete
    fun delete(ticket: Ticket)

    @Update(onConflict = OnConflictStrategy.REPLACE) //todo add the query to update only part of the data
    fun update(ticket: Ticket) : Int

    //same as findById but returns a liveData
    @Query("SELECT * FROM ticket WHERE ticket_id = :ticketId")
    fun getById(ticketId: Int): LiveData<Ticket>
}

@Dao
interface TicketAttendedDAO {
    @Query("SELECT * FROM ticket_attended")
    fun getAll(): List<TicketAttended>

    @Query("SELECT * FROM ticket_attended WHERE ticket_id = :ticketId")
    fun findById(ticketId: Int): TicketAttended

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tickets: TicketAttended)

    @Delete
    fun delete(ticket: TicketAttended)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(ticket: TicketAttended) : Int
}

@Database(entities = [Ticket::class, TicketAttended::class], version = 1)
abstract class FeiDatabase : RoomDatabase() {
    abstract fun ticketDAO(): TicketDAO
    abstract fun ticketAttendedDAO(): TicketAttendedDAO
}