package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetStartChargingRequest(

    @SerializedName("Device_ID") @Expose var Device_ID: String? = null,
    @SerializedName("Vehicle_No") @Expose var Vehicle_No: String? = null,
    @SerializedName("Unit") @Expose var Unit: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Device_ID)
        parcel.writeString(Vehicle_No)
        parcel.writeString(Unit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetStartChargingRequest> {
        override fun createFromParcel(parcel: Parcel): GetStartChargingRequest {
            return GetStartChargingRequest(parcel)
        }

        override fun newArray(size: Int): Array<GetStartChargingRequest?> {
            return arrayOfNulls(size)
        }
    }
}
