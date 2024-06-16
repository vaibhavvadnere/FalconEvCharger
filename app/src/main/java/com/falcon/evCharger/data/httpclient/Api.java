package com.falcon.evCharger.data.httpclient;


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
}
