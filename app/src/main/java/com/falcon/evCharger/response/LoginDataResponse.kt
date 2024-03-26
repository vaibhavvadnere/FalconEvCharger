package com.falcon.evCharger.response

data class LoginDataResponse(
    val Result: Boolean,
    val User: User,
    val Message: String
)