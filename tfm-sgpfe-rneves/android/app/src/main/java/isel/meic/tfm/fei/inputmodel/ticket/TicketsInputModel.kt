package isel.meic.tfm.fei.inputmodel.ticket

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.ActionsDto
import isel.meic.tfm.fei.dto.LinksDto
import isel.meic.tfm.fei.dto.TicketDto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
data class TicketsInputModel(@JsonProperty("properties") val properties: TicketsCollection) :
    Parcelable {

    data class TicketsCollection(@JsonProperty("tickets") val tickets: Collection<TicketDetails>) :
        Parcelable {

        data class TicketDetails(
            @JsonProperty("properties") val ticketDto: TicketDto,
            @JsonProperty("links") val links: Collection<LinksDto>?,
            @JsonProperty("actions") val actions: Collection<ActionsDto>?
        ) : Parcelable {

            companion object {
                @JvmField
                @Suppress("unused")
                var CREATOR = object : Parcelable.Creator<TicketDetails> {
                    override fun newArray(size: Int): Array<TicketDetails?> = arrayOfNulls(size)
                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun createFromParcel(source: Parcel): TicketDetails =
                        TicketDetails(
                            source
                        )
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            constructor(source: Parcel) : this(
                ticketDto = source.readTypedObject(TicketDto.CREATOR)!!,
                //ticketDto = source.readParcelable<TicketDto>(TicketDto::class.java.classLoader), //todo test this seeing that the readTypedObject requires API 23+
                links = mutableListOf<LinksDto>().apply {
                    source.readTypedList(this, LinksDto.CREATOR)
                },
                actions = mutableListOf<ActionsDto>().apply {
                    source.readTypedList(this, ActionsDto.CREATOR)
                })

            @RequiresApi(Build.VERSION_CODES.M)
            override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest!!.apply {
                    writeTypedObject(ticketDto, 0)
                    //writeParcelable(ticketDto, 0) //todo test this seeing that the writeTypedObject requires API 23+
                    writeTypedList(links?.toMutableList())
                    writeTypedList(actions?.toMutableList())
                }
            }

            override fun describeContents(): Int = 0
        }

        companion object {
            @JvmField
            @Suppress("unused")
            var CREATOR = object : Parcelable.Creator<TicketsCollection> {
                override fun newArray(size: Int): Array<TicketsCollection?> = arrayOfNulls(size)
                override fun createFromParcel(source: Parcel): TicketsCollection =
                    TicketsCollection(
                        source
                    )
            }
        }

        constructor(source: Parcel) : this(tickets = mutableListOf<TicketDetails>().apply {
            source.readTypedList(this,
                TicketDetails.CREATOR
            )
        })

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest!!.apply {
                writeTypedList(tickets.toMutableList())
            }
        }

        override fun describeContents(): Int = 0
    }

    companion object {
        @JvmField
        @Suppress("unused")
        var CREATOR = object : Parcelable.Creator<TicketsInputModel> {
            override fun newArray(size: Int): Array<TicketsInputModel?> = arrayOfNulls(size)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun createFromParcel(source: Parcel): TicketsInputModel =
                TicketsInputModel(source)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(source: Parcel) : this(properties = source.readTypedObject(TicketsCollection.CREATOR)!!)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeTypedObject(properties, 0)
        }
    }

    override fun describeContents(): Int = 0
}