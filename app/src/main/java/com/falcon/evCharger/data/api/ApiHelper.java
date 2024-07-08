package com.falcon.evCharger.data.api;


import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.request.VerifyOtpRequest;
import com.falcon.evCharger.response.CreateUserResponse;
import com.falcon.evCharger.response.GetDeviceResponse;
import com.falcon.evCharger.response.GetStartChargingResponse;
import com.falcon.evCharger.response.GetVehicleListResponse;
import com.falcon.evCharger.response.LoginDataResponse;
import com.falcon.evCharger.response.StopChargingResponse;
import com.iSay1.roamstick.data.model.request.GetDeviceRequest;
import com.iSay1.roamstick.data.model.request.GetStartChargingRequest;
import com.iSay1.roamstick.data.model.request.GetVehicleListRequest;
import com.iSay1.roamstick.data.model.request.LoginRequest;
import com.iSay1.roamstick.data.model.request.StopChargingRequest;

import retrofit2.Call;

public class ApiHelper {
    private final ApiService mApiService;

    public ApiHelper(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Call<Post> createPost(String phone_number) {
        return mApiService.createPost(phone_number);
    }
    public Call<LoginDataResponse> loginUser(LoginRequest loginRequest) {
        return mApiService.loginUser(loginRequest);
    }

    public Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return mApiService.verifyOtp(verifyOtpRequest);
    }

    public Call<GetDeviceResponse> getDevice(GetDeviceRequest deviceId) {
        return mApiService.getDevice(deviceId);
    }

    public Call<GetVehicleListResponse> getVehicleList(GetVehicleListRequest vehicleListRequest) {
        return mApiService.getVehicleList(vehicleListRequest);
    }

    public Call<GetStartChargingResponse> getStartCharging(GetStartChargingRequest getStartChargingRequest) {
        return mApiService.getStartCharging(getStartChargingRequest);
    }

    public Call<StopChargingResponse> stopCharging(StopChargingRequest stopChargingRequest) {
        return mApiService.stopCharging(stopChargingRequest);
    }
}
