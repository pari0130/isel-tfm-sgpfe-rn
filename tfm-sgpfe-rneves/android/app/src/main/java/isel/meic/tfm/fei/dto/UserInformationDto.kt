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
data class UserInformationDto(
    val id: Int,
    val username: String,
    val name: String,
    val phone: Int,
    val notification: Boolean
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readInt(),
        source.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeInt(id)
            writeString(username)
            writeString(name)
            writeInt(phone)
            writeByte((if (notification) 1 else 0).toByte())
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInformationDto> {
        override fun createFromParcel(parcel: Parcel): UserInformationDto =
            UserInformationDto(parcel)

        override fun newArray(size: Int): Array<UserInformationDto?> = arrayOfNulls(size)
    }
}