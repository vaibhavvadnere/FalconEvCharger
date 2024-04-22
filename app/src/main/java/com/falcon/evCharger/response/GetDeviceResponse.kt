package com.falcon.evCharger.response

import android.os.Parcel
import android.os.Parcelable

data class GetDeviceResponse(
    val Result: Boolean,
    val User_Details: UserDeviceDetails,
    val Message: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        TODO("User_Details"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (Result) 1 else 0)
        parcel.writeString(Message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetDeviceResponse> {
        override fun createFromParcel(parcel: Parcel): GetDeviceResponse {
            return GetDeviceResponse(parcel)
        }

        override fun newArray(size: Int): Array<GetDeviceResponse?> {
            return arrayOfNulls(size)
        }
    }
}