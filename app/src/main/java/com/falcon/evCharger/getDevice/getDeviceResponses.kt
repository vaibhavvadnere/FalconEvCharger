package com.falcon.evCharger.getDevice

import com.falcon.evCharger.response.GetStartChargingResponse
import com.falcon.evCharger.response.GetVehicleListResponse

sealed class getDeviceResponses(){
    data class VehicleListResponse(val vehicleListResponse: GetVehicleListResponse?) :getDeviceResponses()
    data class StartChargingResponse(val startChargingResponse: GetStartChargingResponse?): getDeviceResponses()
}