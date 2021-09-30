package isel.meic.tfm.fei.inputmodel.user

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.RegisterDto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
class RegisterInputModel(
    @JsonProperty("properties") val properties: RegisterDto
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(parcel: Parcel) : this(
        parcel.readTypedObject(RegisterDto.CREATOR)!!
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedObject(properties, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterInputModel> {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun createFromParcel(parcel: Parcel): RegisterInputModel = RegisterInputModel(parcel)
        override fun newArray(size: Int): Array<RegisterInputModel?> = arrayOfNulls(size)
    }
}