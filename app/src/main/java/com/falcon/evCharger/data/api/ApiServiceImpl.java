package com.falcon.evCharger.data.api;


import com.falcon.evCharger.data.httpclient.HttpClient;
import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.response.LoginDataResponse;
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
}
