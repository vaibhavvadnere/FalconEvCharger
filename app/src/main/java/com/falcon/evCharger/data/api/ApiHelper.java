package com.falcon.evCharger.data.api;


import com.falcon.evCharger.data.model.response.Post;

import retrofit2.Call;

public class ApiHelper {
    private final ApiService mApiService;

    public ApiHelper(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Call<Post> createPost(String phone_number) {
        return mApiService.createPost(phone_number);
    }

}
