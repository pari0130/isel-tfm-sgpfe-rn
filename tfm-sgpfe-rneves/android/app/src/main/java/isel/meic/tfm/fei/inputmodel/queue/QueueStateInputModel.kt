package isel.meic.tfm.fei.inputmodel.queue

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.ActionsDto
import isel.meic.tfm.fei.dto.LinksDto
import isel.meic.tfm.fei.dto.QueueStateDto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class QueueStateInputModel(
    @JsonProperty("properties") val properties: QueueStateDto,
    @JsonProperty("links") val links: Collection<LinksDto>?,
    @JsonProperty("actions") val actions: Collection<ActionsDto>?
) : Parcelable {

    companion object {
        @JvmField
        @Suppress("unused")
        var CREATOR = object : Parcelable.Creator<QueueStateInputModel> {
            override fun newArray(size: Int): Array<QueueStateInputModel?> = arrayOfNulls(size)

            @RequiresApi(Build.VERSION_CODES.M)
            override fun createFromParcel(source: Parcel): QueueStateInputModel =
                QueueStateInputModel(source)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(source: Parcel) : this(properties = source.readTypedObject(QueueStateDto.CREATOR)!!,
        links = mutableListOf<LinksDto>().apply {
            source.readTypedList(this, LinksDto.CREATOR)
        },
        actions = mutableListOf<ActionsDto>().apply {
            source.readTypedList(this, ActionsDto.CREATOR)
        })

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeTypedObject(properties, 0)
            writeTypedList(links?.toMutableList())
            writeTypedList(actions?.toMutableList())
        }
    }

    override fun describeContents(): Int = 0
}