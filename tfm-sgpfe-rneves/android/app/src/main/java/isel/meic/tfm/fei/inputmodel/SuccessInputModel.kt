package isel.meic.tfm.fei.inputmodel

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.SuccessDto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class SuccessInputModel(@JsonProperty("properties") val properties: SuccessDto) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(parcel: Parcel) : this(
        parcel.readTypedObject(SuccessDto.CREATOR)!!
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedObject(properties, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SuccessInputModel> {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun createFromParcel(parcel: Parcel): SuccessInputModel = SuccessInputModel(parcel)
        override fun newArray(size: Int): Array<SuccessInputModel?> = arrayOfNulls(size)
    }
}