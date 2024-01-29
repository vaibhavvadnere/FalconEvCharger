package com.falcon.evCharger.data.api;


import com.falcon.evCharger.data.httpclient.HttpClient;
import com.falcon.evCharger.data.model.response.Post;

import retrofit2.Call;

public class ApiServiceImpl implements ApiService {

    @Override
    public Call<Post> createPost(String phone_number) {
        return HttpClient.getHttpApi().createPost(phone_number);
    }


}
