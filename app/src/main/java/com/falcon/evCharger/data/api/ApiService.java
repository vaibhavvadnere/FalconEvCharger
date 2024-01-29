package com.falcon.evCharger.data.api;

import com.falcon.evCharger.data.model.response.Post;

import retrofit2.Call;

public interface ApiService {

    Call<Post> createPost(String phone_number);
}


