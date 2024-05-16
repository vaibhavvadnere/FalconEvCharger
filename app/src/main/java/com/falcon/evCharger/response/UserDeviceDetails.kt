package com.falcon.evCharger.response

import android.os.Parcel
import android.os.Parcelable

data class UserDeviceDetails(
    val ID: Int,
    val Soc_ID: Int,
    val Device_ID: String?,
    val Society_Name: String?,
    val Mobile_No: String?,
    val OTP: String?,
    val Location_Lat: String?,
    val Location_Lang: String?,
    val Installation_Date: String?,
    val Installed_By_Name: String?,
    val Active: Int,
    val MAC_ID: String?,
    val Date: String?,
    val Model_No: String?,
    val Programed_By: String?,
    val QRCode: String?,
    val Device_Name: String?,
    val Type_Of_Supply: String?,
    val Max_Power: Int,
    val ChargesPerUnit: Double
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ID)
        parcel.writeInt(Soc_ID)
        parcel.writeString(Device_ID)
        parcel.writeString(Society_Name)
        parcel.writeString(Mobile_No)
        parcel.writeString(OTP)
        parcel.writeString(Location_Lat)
        parcel.writeString(Location_Lang)
        parcel.writeString(Installation_Date)
        parcel.writeString(Installed_By_Name)
        parcel.writeInt(Active)
        parcel.writeString(MAC_ID)
        parcel.writeString(Date)
        parcel.writeString(Model_No)
        parcel.writeString(Programed_By)
        parcel.writeString(QRCode)
        parcel.writeString(Device_Name)
        parcel.writeString(Type_Of_Supply)
        parcel.writeValue(Max_Power)
        parcel.writeValue(ChargesPerUnit)
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