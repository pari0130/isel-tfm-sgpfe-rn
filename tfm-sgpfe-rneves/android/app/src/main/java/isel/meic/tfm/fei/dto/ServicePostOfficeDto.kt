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
data class ServicePostOfficeDto(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val serviceId: Int,
    val address: String
) :
    Parcelable {

    companion object CREATOR : Parcelable.Creator<ServicePostOfficeDto> {
        override fun newArray(size: Int): Array<ServicePostOfficeDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): ServicePostOfficeDto =
            ServicePostOfficeDto(source)
    }

    constructor(source: Parcel) : this(
        id = source.readInt(),
        latitude = source.readDouble(),
        longitude = source.readDouble(),
        description = source.readString()!!,
        serviceId = source.readInt(),
        address = source.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeInt(id)
            writeDouble(latitude)
            writeDouble(longitude)
            writeString(description)
            writeInt(serviceId)
            writeString(address)
        }
    }

    override fun describeContents(): Int = 0
}