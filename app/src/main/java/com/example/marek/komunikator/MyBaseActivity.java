package com.example.marek.komunikator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.marek.komunikator.controllers.config.Response;

public abstract class MyBaseActivity extends AppCompatActivity {
    protected ActivityManager activityManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = (ActivityManager) this.getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityManager.setCrrActivity(this);
    }

    public abstract void onResponse(Response response);

    public abstract void resetDataAfterResponse();

    public abstract void executeAfterResponse(boolean success);

    private void clearReferences(){
        Activity crrActivity = activityManager.getCrrActivity();
        if (this.equals(crrActivity)){
            activityManager.setCrrActivity(null);
        }
    }
}
