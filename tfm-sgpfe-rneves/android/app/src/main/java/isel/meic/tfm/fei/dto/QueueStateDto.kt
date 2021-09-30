package isel.meic.tfm.fei.dto

import android.os.Parcel
import android.os.Parcelable

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class QueueStateDto(
    val letter: String,
    val stateNumber: Int,
    val attendedNumber: Int,
    val queueId: Int,
    val name: String,
    val forecast: Int
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<QueueStateDto> {
        override fun newArray(size: Int): Array<QueueStateDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): QueueStateDto = QueueStateDto(source)
    }

    constructor(source: Parcel) : this(
        letter = source.readString()!!,
        stateNumber = source.readInt(),
        attendedNumber = source.readInt(),
        queueId = source.readInt(),
        name = source.readString()!!,
        forecast = source.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeString(letter)
            writeInt(stateNumber)
            writeInt(attendedNumber)
            writeInt(queueId)
            writeString(name)
            writeInt(forecast)
        }
    }

    override fun describeContents(): Int = 0
}