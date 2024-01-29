package com.falcon.evCharger;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.falcon.evCharger.base.BaseActivity;
import com.falcon.evcharger.R;

public class EVMainActivity extends BaseActivity {
    public NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ev_main);

        navController = Navigation.findNavController(EVMainActivity.this, R.id.my_nav_host_fragment);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void networkIsAvailable() {

    }
}