package isel.meic.tfm.fei.dto

import android.os.Parcel
import android.os.Parcelable

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Temporary
 */
data class UserDto(val token: String, val authenticated: Boolean, val userId: Int, val name: String) : Parcelable{

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readByte() != 0.toByte(),
        source.readInt(),
        source.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeString(token)
            writeByte((if (authenticated) 1 else 0).toByte())
            writeInt(userId)
            writeString(name)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDto> {
        override fun createFromParcel(parcel: Parcel): UserDto = UserDto(parcel)
        override fun newArray(size: Int): Array<UserDto?> = arrayOfNulls(size)
    }
}