package com.falcon.evCharger.data.api;

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

public interface ApiService {

    Call<Post> createPost(String phone_number);

    Call<LoginDataResponse> loginUser(LoginRequest loginRequest);

    Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest);

    Call<GetDeviceResponse> getDevice(GetDeviceRequest deviceId);

    Call<GetVehicleListResponse> getVehicleList(GetVehicleListRequest vehicleListRequest);

    Call<GetStartChargingResponse> getStartCharging(GetStartChargingRequest getStartChargingRequest);

    Call<StopChargingResponse> stopCharging(StopChargingRequest stopChargingRequest);

    Call<GetTransactionHistoryResponse> getTransactionHistory(GetTransactionHistoryRequest getTransactionHistoryRequest);

    Call<UserDetailsResponse> getUserDetails(GetUserDataRequest getUserDataRequest);

    Call<WalletResponse> getWalletDetails(GetTransactionHistoryRequest getTransactionHistoryRequest);
}


