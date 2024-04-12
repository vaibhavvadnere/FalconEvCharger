package com.falcon.evCharger.response

data class GetDeviceResponse(
    val Result: Boolean,
    val User_Details: UserDetails,
    val Message: String
)