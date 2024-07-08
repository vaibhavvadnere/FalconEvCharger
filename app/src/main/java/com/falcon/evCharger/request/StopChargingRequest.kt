package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StopChargingRequest(

    @SerializedName("Device_ID") @Expose var Device_ID: String? = null,
    @SerializedName("Vehicle_No") @Expose var Vehicle_No: String? = null,
    @SerializedName("User_ID") @Expose var User_ID: String? = null,
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Device_ID)
        parcel.writeString(Vehicle_No)
        parcel.writeString(User_ID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StopChargingRequest> {
        override fun createFromParcel(parcel: Parcel): StopChargingRequest {
            return StopChargingRequest(parcel)
        }

        override fun newArray(size: Int): Array<StopChargingRequest?> {
            return arrayOfNulls(size)
        }
    }
}
