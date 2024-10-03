package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetTransactionHistoryRequest(

    @SerializedName("User_ID") @Expose var UserID: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(UserID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetTransactionHistoryRequest> {
        override fun createFromParcel(parcel: Parcel): GetTransactionHistoryRequest {
            return GetTransactionHistoryRequest(parcel)
        }

        override fun newArray(size: Int): Array<GetTransactionHistoryRequest?> {
            return arrayOfNulls(size)
        }
    }
}
