package com.falcon.evCharger.response

data class UserList(
    val Active: Int,
    val Address: String,
    val ID: Int,
    val Phone_Number: String,
    val Society_ID: Int,
    val User_ID: String,
    val User_Name: String,
    val Vehicle_No: String,
    val Vehicle_Type: String,
    var selected:Boolean
)