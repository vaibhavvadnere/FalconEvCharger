package com.falcon.evCharger.data.httpclient;


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
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("/api/Account/CreateUser")
    Call<Post> createPost(@Field("PhoneNumber") String phone_number);

    @POST("/API/Login")
    Call<LoginDataResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("api/Verify_OTP")
    Call<CreateUserResponse> verifyOtp(@Body VerifyOtpRequest verifyOtpRequest);

    @POST("api/Get_Device")
    Call<GetDeviceResponse> getDevice(@Body GetDeviceRequest Device_ID);

    @POST("api/Get_Vehicle_List")
    Call<GetVehicleListResponse> getVehicleList(@Body GetVehicleListRequest vehicleList);

    @POST("api/Start_Charging")
    Call<GetStartChargingResponse> getStartCharging(@Body GetStartChargingRequest startChargingRequest);

    @POST("api/Stop_Charging")
    Call<StopChargingResponse> stopCharging(@Body StopChargingRequest stopChargingRequest);

    @POST("api/getTransactionHistory")
    Call<GetTransactionHistoryResponse> getTransactionHistory(@Body GetTransactionHistoryRequest getTransactionHistoryRequest);

    @POST("api/getUserDetails")
    Call<UserDetailsResponse> getUserDetails(@Body GetUserDataRequest getUserDataRequest);

    @POST("api/getWaletBalance")
    Call<WalletResponse> getWalletDetails(@Body GetTransactionHistoryRequest getTransactionHistoryRequest);
}
