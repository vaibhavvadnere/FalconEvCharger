package com.falcon.evCharger.data.httpclient;


import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.response.LoginDataResponse;
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

}
