package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.ActivityManager;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.User;
import com.example.marek.komunikator.userSettings.activities.UserActivity;


public class UserLoginRegisterTask extends ResponseTask {

    public UserLoginRegisterTask(Response response, UserActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (response.getJWT() != null){
            ActivityManager.JWT = response.getJWT();
            return true;
        }
        return false;
    }
}
