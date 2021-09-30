package isel.meic.tfm.fei.inputmodel.service

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.ActionsDto
import isel.meic.tfm.fei.dto.LinksDto
import isel.meic.tfm.fei.dto.ServiceDto
import isel.meic.tfm.fei.dto.ServicePostOfficeDto
import isel.meic.tfm.fei.inputmodel.ticket.TicketsInputModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServicePostOfficesInputModel(@JsonProperty("properties") val properties: ServicePostOfficesCollection) :
Parcelable {

    data class ServicePostOfficesCollection(@JsonProperty("servicesPostOffices") val servicePostOffices: Collection<ServicePostOfficesDetails>) :
        Parcelable {

        data class ServicePostOfficesDetails(
            @JsonProperty("properties") val servicePostOffices: ServicePostOfficeDto,
            @JsonProperty("links") val links: Collection<LinksDto>?,
            @JsonProperty("actions") val actions: Collection<ActionsDto>?
        ) : Parcelable {

            companion object {
                @JvmField
                @Suppress("unused")
                var CREATOR = object : Parcelable.Creator<ServicePostOfficesDetails> {
                    override fun newArray(size: Int): Array<ServicePostOfficesDetails?> = arrayOfNulls(size)

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun createFromParcel(source: Parcel): ServicePostOfficesDetails =
                        ServicePostOfficesDetails(
                            source
                        )
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            constructor(source: Parcel) : this(
                servicePostOffices = source.readTypedObject(ServicePostOfficeDto.CREATOR)!!,
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
                    writeTypedObject(servicePostOffices, 0)
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
            var CREATOR = object : Parcelable.Creator<ServicePostOfficesCollection> {
                override fun newArray(size: Int): Array<ServicePostOfficesCollection?> = arrayOfNulls(size)
                override fun createFromParcel(source: Parcel): ServicePostOfficesCollection =
                    ServicePostOfficesCollection(
                        source
                    )
            }
        }

        constructor(source: Parcel) : this(servicePostOffices = mutableListOf<ServicePostOfficesDetails>().apply {
            source.readTypedList(
                this,
                ServicePostOfficesDetails.CREATOR
            )
        })

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest!!.apply {
                writeTypedList(servicePostOffices.toMutableList())
            }
        }

        override fun describeContents(): Int = 0
    }

    companion object {
        @JvmField
        @Suppress("unused")
        var CREATOR = object : Parcelable.Creator<ServicePostOfficesInputModel> {
            override fun newArray(size: Int): Array<ServicePostOfficesInputModel?> = arrayOfNulls(size)

            @RequiresApi(Build.VERSION_CODES.M)
            override fun createFromParcel(source: Parcel): ServicePostOfficesInputModel =
                ServicePostOfficesInputModel(source)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(source: Parcel) : this(properties = source.readTypedObject(ServicePostOfficesCollection.CREATOR)!!)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeTypedObject(properties, 0)
        }
    }

    override fun describeContents(): Int = 0
}