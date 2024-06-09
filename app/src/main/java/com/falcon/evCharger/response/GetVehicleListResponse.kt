package com.falcon.evCharger.response

data class GetVehicleListResponse(
    val Result: Boolean,
    val User_List: List<UserList>
)