package isel.meic.tfm.fei.dto

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
data class LinksDto(
    @JsonProperty("rel") val rel: List<String>,
    @JsonProperty("href") val href: String
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<LinksDto> {
        override fun newArray(size: Int): Array<LinksDto?> = arrayOfNulls(size)
        override fun createFromParcel(source: Parcel): LinksDto = LinksDto(source)
    }

    constructor(source: Parcel) : this(
        rel = source.createStringArrayList()!!,
        href = source.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeStringList(rel.toMutableList())
            writeString(href)
        }
    }

    override fun describeContents(): Int = 0
}