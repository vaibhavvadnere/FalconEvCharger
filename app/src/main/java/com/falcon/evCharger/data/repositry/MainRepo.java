package com.falcon.evCharger.data.repositry;


import com.falcon.evCharger.data.api.ApiHelper;
import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.request.VerifyOtpRequest;
import com.falcon.evCharger.response.CreateUserResponse;
import com.falcon.evCharger.response.GetDeviceResponse;
import com.falcon.evCharger.response.GetStartChargingResponse;
import com.falcon.evCharger.response.GetTransactionHistoryResponse;
import com.falcon.evCharger.response.GetVehicleListResponse;
import com.falcon.evCharger.response.LoginDataResponse;
import com.falcon.evCharger.response.StopChargingResponse;
import com.falcon.evCharger.response.UserDetailsResponse;
import com.falcon.evCharger.response.WalletResponse;
import com.iSay1.roamstick.data.model.request.GetDeviceRequest;
import com.iSay1.roamstick.data.model.request.GetStartChargingRequest;
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest;
import com.iSay1.roamstick.data.model.request.GetUserDataRequest;
import com.iSay1.roamstick.data.model.request.GetVehicleListRequest;
import com.iSay1.roamstick.data.model.request.LoginRequest;
import com.iSay1.roamstick.data.model.request.StopChargingRequest;

import retrofit2.Call;

public class MainRepo {
    private final ApiHelper mApiHelper;

    public MainRepo(ApiHelper mApiHelper) {
        this.mApiHelper = mApiHelper;
    }


    public Call<Post> creteUser(String phnNumber) {
        return mApiHelper.createPost(phnNumber);
    }

    public Call<LoginDataResponse> loginUser(LoginRequest user) {
        return mApiHelper.loginUser(user);
    }

    public Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return mApiHelper.verifyOtp(verifyOtpRequest);
    }

    public Call<GetDeviceResponse> getDevice(GetDeviceRequest deviceId) {
        return mApiHelper.getDevice(deviceId);
    }

    public Call<GetVehicleListResponse> getVehicleList(GetVehicleListRequest vehicleList) {
        return mApiHelper.getVehicleList(vehicleList);
    }

    public Call<GetStartChargingResponse> getStartCharging(GetStartChargingRequest startChargingRequest) {
        return mApiHelper.getStartCharging(startChargingRequest);
    }

    public Call<StopChargingResponse> stopCharging(StopChargingRequest stopChargingRequest) {
        return mApiHelper.stopCharging(stopChargingRequest);
    }

    public Call<GetTransactionHistoryResponse> getTransactionHistory(GetTransactionHistoryRequest getTransactionHistoryRequest) {
        return mApiHelper.getTransactionHistory(getTransactionHistoryRequest);
    }

    public Call<UserDetailsResponse> getUserDetails(GetUserDataRequest getUserDataRequest) {
        return mApiHelper.getUserDetails(getUserDataRequest);
    }
    public Call<WalletResponse> getWalletDetails(GetTransactionHistoryRequest getTransactionHistoryRequest) {
        return mApiHelper.getWalletDetails(getTransactionHistoryRequest);
    }
}
