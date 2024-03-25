package com.falcon.evCharger.data.repositry;


import com.falcon.evCharger.data.api.ApiHelper;
import com.falcon.evCharger.data.model.response.Post;
import com.falcon.evCharger.response.LoginDataResponse;
import com.iSay1.roamstick.data.model.request.LoginRequest;

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

}
