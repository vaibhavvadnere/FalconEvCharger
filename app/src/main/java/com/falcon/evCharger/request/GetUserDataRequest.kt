package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetUserDataRequest(

    @SerializedName("Phone_Number") @Expose var PhoneNumber: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PhoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetUserDataRequest> {
        override fun createFromParcel(parcel: Parcel): GetUserDataRequest {
            return GetUserDataRequest(parcel)
        }

        override fun newArray(size: Int): Array<GetUserDataRequest?> {
            return arrayOfNulls(size)
        }
    }
}
