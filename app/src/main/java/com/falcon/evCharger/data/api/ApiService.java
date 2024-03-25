package com.falcon.evCharger.data.api;

import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.response.LoginDataResponse;
import com.iSay1.roamstick.data.model.request.LoginRequest;

import retrofit2.Call;

public interface ApiService {

    Call<Post> createPost(String phone_number);

    Call<LoginDataResponse> loginUser(LoginRequest loginRequest);
}


