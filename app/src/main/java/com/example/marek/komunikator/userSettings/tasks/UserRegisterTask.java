package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.User;
import com.example.marek.komunikator.userSettings.activities.UserActivity;

public class UserRegisterTask extends ResponseTask {

    public UserRegisterTask(Response response, UserActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {


        return true;
    }
}
