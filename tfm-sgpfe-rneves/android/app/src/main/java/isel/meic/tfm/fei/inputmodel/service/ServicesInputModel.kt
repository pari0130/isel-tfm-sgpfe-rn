package isel.meic.tfm.fei.inputmodel.service

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.ActionsDto
import isel.meic.tfm.fei.dto.LinksDto
import isel.meic.tfm.fei.dto.ServiceDto
/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServicesInputModel(@JsonProperty("properties") val properties: ServiceCollection) :
    Parcelable {

    data class ServiceCollection(@JsonProperty("services") val services: Collection<ServiceDetails>) :
        Parcelable {

        data class ServiceDetails(
            @JsonProperty("properties") val serviceDto: ServiceDto,
            @JsonProperty("links") val links: Collection<LinksDto>?,
            @JsonProperty("actions") val actions: Collection<ActionsDto>?
        ) : Parcelable {

            companion object {
                @JvmField
                @Suppress("unused")
                var CREATOR = object : Parcelable.Creator<ServiceDetails> {
                    override fun newArray(size: Int): Array<ServiceDetails?> = arrayOfNulls(size)

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun createFromParcel(source: Parcel): ServiceDetails =
                        ServiceDetails(
                            source
                        )
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            constructor(source: Parcel) : this(
                serviceDto = source.readTypedObject(ServiceDto.CREATOR)!!,
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
                    writeTypedObject(serviceDto, 0)
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
            var CREATOR = object : Parcelable.Creator<ServiceCollection> {
                override fun newArray(size: Int): Array<ServiceCollection?> = arrayOfNulls(size)
                override fun createFromParcel(source: Parcel): ServiceCollection =
                    ServiceCollection(
                        source
                    )
            }
        }

        constructor(source: Parcel) : this(services = mutableListOf<ServiceDetails>().apply {
            source.readTypedList(
                this,
                ServiceDetails.CREATOR
            )
        })

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest!!.apply {
                writeTypedList(services.toMutableList())
            }
        }

        override fun describeContents(): Int = 0
    }

    companion object {
        @JvmField
        @Suppress("unused")
        var CREATOR = object : Parcelable.Creator<ServicesInputModel> {
            override fun newArray(size: Int): Array<ServicesInputModel?> = arrayOfNulls(size)

            @RequiresApi(Build.VERSION_CODES.M)
            override fun createFromParcel(source: Parcel): ServicesInputModel =
                ServicesInputModel(source)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(source: Parcel) : this(properties = source.readTypedObject(ServiceCollection.CREATOR)!!)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.apply {
            writeTypedObject(properties, 0)
        }
    }

    override fun describeContents(): Int = 0
}