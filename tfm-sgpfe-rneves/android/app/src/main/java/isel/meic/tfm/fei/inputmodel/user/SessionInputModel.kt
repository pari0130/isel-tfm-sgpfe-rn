package isel.meic.tfm.fei.inputmodel.user

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.SessionDto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class SessionInputModel(
    @JsonProperty("properties") val properties: SessionDto
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(parcel: Parcel) : this(
        parcel.readTypedObject(SessionDto.CREATOR)!!
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedObject(properties, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SessionInputModel> {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun createFromParcel(parcel: Parcel): SessionInputModel = SessionInputModel(parcel)
        override fun newArray(size: Int): Array<SessionInputModel?> = arrayOfNulls(size)
    }
}