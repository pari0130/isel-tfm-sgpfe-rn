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
data class SuccessDto(val success: Boolean) : Parcelable {
    companion object CREATOR : Parcelable.Creator<SuccessDto> {
        override fun newArray(size: Int): Array<SuccessDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): SuccessDto = SuccessDto(source)
    }

    constructor(source: Parcel) : this(
        success = source.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeByte((if (success) 1 else 0).toByte())
        }
    }

    override fun describeContents(): Int = 0
}