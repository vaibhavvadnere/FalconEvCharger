package com.falcon.evCharger.data.api;

import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.request.VerifyOtpRequest;
import com.falcon.evCharger.response.CreateUserResponse;
import com.falcon.evCharger.response.GetDeviceResponse;
import com.falcon.evCharger.response.GetStartChargingResponse;
import com.falcon.evCharger.response.GetVehicleListResponse;
import com.falcon.evCharger.response.LoginDataResponse;
import com.iSay1.roamstick.data.model.request.GetDeviceRequest;
import com.iSay1.roamstick.data.model.request.GetStartChargingRequest;
import com.iSay1.roamstick.data.model.request.GetVehicleListRequest;
import com.iSay1.roamstick.data.model.request.LoginRequest;

import retrofit2.Call;

public interface ApiService {

    Call<Post> createPost(String phone_number);

    Call<LoginDataResponse> loginUser(LoginRequest loginRequest);

    Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest);

    Call<GetDeviceResponse> getDevice(GetDeviceRequest deviceId);
    Call<GetVehicleListResponse> getVehicleList(GetVehicleListRequest vehicleListRequest);
    Call<GetStartChargingResponse> getStartCharging(GetStartChargingRequest getStartChargingRequest);
}


