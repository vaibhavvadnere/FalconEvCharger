package com.falcon.evCharger.response

import android.os.Parcel
import android.os.Parcelable

data class UserDeviceDetails(
    val Active: Int,
    val Date: String?,
    val ID: Int,
    val MAC_ID: String?,
    val Model_No: String?,
    val Programed_By: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Active)
        parcel.writeString(Date)
        parcel.writeInt(ID)
        parcel.writeString(MAC_ID)
        parcel.writeString(Model_No)
        parcel.writeString(Programed_By)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDeviceDetails> {
        override fun createFromParcel(parcel: Parcel): UserDeviceDetails {
            return UserDeviceDetails(parcel)
        }

        override fun newArray(size: Int): Array<UserDeviceDetails?> {
            return arrayOfNulls(size)
        }
    }
}