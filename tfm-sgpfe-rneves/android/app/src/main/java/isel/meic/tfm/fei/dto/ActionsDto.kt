package isel.meic.tfm.fei.dto

import android.os.Parcel
import android.os.Parcelable

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class ActionsDto(
    val name: String,
    val method: String,
    val href: String,
    val title: String,
    val type: String
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<ActionsDto> {
        override fun newArray(size: Int): Array<ActionsDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): ActionsDto = ActionsDto(source)
    }

    constructor(source: Parcel) : this(
        name = source.readString()!!,
        method = source.readString()!!,
        href = source.readString()!!,
        title = source.readString()!!,
        type = source.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeString(name)
            writeString(method)
            writeString(href)
            writeString(title)
            writeString(type)
        }
    }

    override fun describeContents(): Int = 0
}