package com.falcon.evCharger.response

data class GetTransactionHistoryResponse(
    val Response: List<HistoryResponse>,
    val Result: Boolean,
    val Message: String,
)