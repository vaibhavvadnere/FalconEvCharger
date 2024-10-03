package com.falcon.evCharger.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UnitsData(
    @SerializedName("unitName") @Expose var unitName: String? = null,
    @SerializedName("selected") @Expose var selected: Boolean? = false,
    @SerializedName("unit") @Expose var unit: String? = "0"
)