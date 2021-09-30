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
data class SessionDto(val valid: Boolean) : Parcelable {
    companion object CREATOR : Parcelable.Creator<SessionDto> {
        override fun newArray(size: Int): Array<SessionDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): SessionDto = SessionDto(source)
    }

    constructor(source: Parcel) : this(
        valid = source.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeByte((if (valid) 1 else 0).toByte())
        }
    }

    override fun describeContents(): Int = 0
}