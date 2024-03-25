package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("Phone_Number") @Expose var Phone_Number: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Phone_Number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginRequest> {
        override fun createFromParcel(parcel: Parcel): LoginRequest {
            return LoginRequest(parcel)
        }

        override fun newArray(size: Int): Array<LoginRequest?> {
            return arrayOfNulls(size)
        }
    }
}
