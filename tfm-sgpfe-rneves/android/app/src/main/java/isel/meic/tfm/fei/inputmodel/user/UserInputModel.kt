package isel.meic.tfm.fei.inputmodel.user

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import isel.meic.tfm.fei.dto.ActionsDto
import isel.meic.tfm.fei.dto.LinksDto
import isel.meic.tfm.fei.dto.UserDto

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class UserInputModel(
    @JsonProperty("properties") val properties: UserDto/*,
    @JsonProperty("links") val links: Collection<LinksDto>?, //do i need this
    @JsonProperty("actions") val actions: Collection<ActionsDto>? //do i need this*/
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(parcel: Parcel) : this(
        parcel.readTypedObject(UserDto.CREATOR)!!
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedObject(properties, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInputModel> {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun createFromParcel(parcel: Parcel): UserInputModel = UserInputModel(parcel)
        override fun newArray(size: Int): Array<UserInputModel?> = arrayOfNulls(size)
    }
}