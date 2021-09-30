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
data class ServiceDto(val id: Int, val name: String, val description: String) : Parcelable {

    companion object CREATOR : Parcelable.Creator<ServiceDto> {
        override fun newArray(size: Int): Array<ServiceDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): ServiceDto = ServiceDto(source)
    }

    constructor(source: Parcel) : this(
        id = source.readInt(),
        name = source.readString()!!,
        description = source.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeInt(id)
            writeString(name)
            writeString(description)
        }
    }

    override fun describeContents(): Int = 0
}