package isel.meic.tfm.fei.dto

import android.os.Parcel
import android.os.Parcelable

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class RegisterDto(val success: Boolean, val username: Boolean, val email: Boolean) :
    Parcelable {
    companion object CREATOR : Parcelable.Creator<RegisterDto> {
        override fun newArray(size: Int): Array<RegisterDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): RegisterDto = RegisterDto(source)
    }

    constructor(source: Parcel) : this(
        success = source.readByte() != 0.toByte(),
        username = source.readByte() != 0.toByte(),
        email = source.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeByte((if (success) 1 else 0).toByte())
            writeByte((if (username) 1 else 0).toByte())
            writeByte((if (email) 1 else 0).toByte())
        }
    }

    override fun describeContents(): Int = 0
}