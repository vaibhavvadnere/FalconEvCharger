package com.falcon.evCharger.data.api;


import com.falcon.evCharger.data.httpclient.HttpClient;
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

public class ApiServiceImpl implements ApiService {

    @Override
    public Call<Post> createPost(String phone_number) {
        return HttpClient.getHttpApi().createPost(phone_number);
    }

    @Override
    public Call<LoginDataResponse> loginUser(LoginRequest loginRequest) {
        return HttpClient.getHttpApi().loginUser(loginRequest);
    }

    @Override
    public Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return HttpClient.getHttpApi().verifyOtp(verifyOtpRequest);
    }

    @Override
    public Call<GetDeviceResponse> getDevice(GetDeviceRequest getDeviceRequest) {
        return HttpClient.getHttpApi().getDevice(getDeviceRequest);
    }

    @Override
    public Call<GetVehicleListResponse> getVehicleList(GetVehicleListRequest vehicleListRequest) {
        return HttpClient.getHttpApi().getVehicleList(vehicleListRequest);
    }

    @Override
    public Call<GetStartChargingResponse> getStartCharging(GetStartChargingRequest startChargingRequest) {
        return HttpClient.getHttpApi().getStartCharging(startChargingRequest);
    }
}
