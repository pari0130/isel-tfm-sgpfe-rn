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
data class QueuesStatesInputModel(@JsonProperty("properties") val properties: QueueStatesCollection) :
    Parcelable {

    data class QueueStatesCollection(@JsonProperty("queueStates") val queueStates: Collection<QueueStateDetails>) :
        Parcelable {

        data class QueueStateDetails(
            @JsonProperty("properties") val queueDto: QueueStateDto,
            @JsonProperty("links") val links: Collection<LinksDto>?,
            @JsonProperty("actions") val actions: Collection<ActionsDto>?
        ) : Parcelable {

            companion object {
                @JvmField
                @Suppress("unused")
                var CREATOR = object : Parcelable.Creator<QueueStateDetails> {
                    override fun newArray(size: Int): Array<QueueStateDetails?> = arrayOfNulls(size)

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun createFromParcel(source: Parcel): QueueStateDetails =
                        QueueStateDetails(
                            source
                        )
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            constructor(source: Parcel) : this(
                queueDto = source.readTypedObject(QueueStateDto.CREATOR)!!,
                //queueDto = source.readParcelable<TicketDto>(TicketDto::class.java.classLoader), //todo test this seeing that the readTypedObject requires API 23+
                links = mutableListOf<LinksDto>().apply {
                    source.readTypedList(this, LinksDto.CREATOR)
                },
                actions = mutableListOf<ActionsDto>().apply {
                    source.readTypedList(this, ActionsDto.CREATOR)
                })

            @RequiresApi(Build.VERSION_CODES.M)
            override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest!!.apply {
                    writeTypedObject(queueDto, 0)
                    //writeParcelable(queueDto, 0) //todo test this seeing that the writeTypedObject requires API 23+
                    writeTypedList(links?.toMutableList())
                    writeTypedList(actions?.toMutableList())
                }
            }

            override fun describeContents(): Int = 0
        }

        companion object {
            @JvmField
            @Suppress("unused")
            var CREATOR = object : Parcelable.Creator<QueueStatesCollection> {
                override fun newArray(size: Int): Array<QueueStatesCollection?> = arrayOfNulls(size)
                override fun createFromParcel(source: Parcel): QueueStatesCollection =
                    QueueStatesCollection(
                        source
                    )
            }
        }

        constructor(source: Parcel) : this(queueStates = mutableListOf<QueueStateDetails>().apply {
            source.readTypedList(
                this,
                QueueStateDetails.CREATOR
            )
        })

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest!!.apply {
                writeTypedList(queueStates.toMutableList())
            }
        }

        override fun describeContents(): Int = 0
    }

    companion object {
        @JvmField
        @Suppress("unused")
        var CREATOR = object : Parcelable.Creator<QueuesStatesInputModel> {
            override fun newArray(size: Int): Array<QueuesStatesInputModel?> = arrayOfNulls(size)

            @RequiresApi(Build.VERSION_CODES.M)
            override fun createFromParcel(source: Parcel): QueuesStatesInputModel =
                QueuesStatesInputModel(source)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(source: Parcel) : this(properties = source.readTypedObject(QueueStatesCollection.CREATOR)!!)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeTypedObject(properties, 0)
        }
    }

    override fun describeContents(): Int = 0
}