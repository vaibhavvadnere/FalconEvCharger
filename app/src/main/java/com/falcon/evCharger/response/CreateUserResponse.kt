package com.falcon.evCharger.response

data class CreateUserResponse(
    val Result: Boolean,
    val User_Details: User,
    val Message: String
)