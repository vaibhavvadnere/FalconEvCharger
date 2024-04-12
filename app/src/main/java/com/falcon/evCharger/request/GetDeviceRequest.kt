package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetDeviceRequest(

    @SerializedName("Device_ID") @Expose var Device_ID: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Device_ID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetDeviceRequest> {
        override fun createFromParcel(parcel: Parcel): GetDeviceRequest {
            return GetDeviceRequest(parcel)
        }

        override fun newArray(size: Int): Array<GetDeviceRequest?> {
            return arrayOfNulls(size)
        }
    }
}
