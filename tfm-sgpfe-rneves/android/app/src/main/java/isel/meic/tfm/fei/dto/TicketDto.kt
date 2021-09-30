package isel.meic.tfm.fei.dto

import android.os.Parcel
import android.os.Parcelable

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class TicketDto(
    val ticketId: Int,
    val ticketLetter: String,
    val ticketNumber: Int,
    val queueId: Int,
    val name: String
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<TicketDto> {
        override fun newArray(size: Int): Array<TicketDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): TicketDto = TicketDto(source)
    }

    constructor(source: Parcel) : this(
        ticketId = source.readInt(),
        ticketLetter = source.readString()!!,
        ticketNumber = source.readInt(),
        queueId = source.readInt(),
        name = source.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeInt(ticketId)
            writeString(ticketLetter)
            writeInt(ticketNumber)
            writeInt(queueId)
            writeString(name)
        }
    }

    override fun describeContents(): Int = 0
}