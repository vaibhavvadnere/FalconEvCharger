package com.falcon.evCharger.response

data class HistoryResponse(
    val Amount_Added_By_User: Int,
    val Amount_After_Used: Int,
    val Amount_Before_Used: Int,
    val Amount_Used: Int,
    val Date_Time: String,
    val Device_ID: String,
    val End_Time: String,
    val Minimum_Balance: Int,
    val Mobile_No: String,
    val Society_ID: String,
    val Start_Time: String,
    val Ticket_ID: String,
    val Total_Duration: String,
    val Transaction_Ref_No: String,
    val Unit_Added_By_User: Int,
    val Unit_After_Charging: Int,
    val Unit_Before_Charging: Int,
    val Unit_Consumed: Int,
    val Unit_Rate: Int,
    val User_ID: String,
    val Vehicle_No: String
)