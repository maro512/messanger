package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.User;
import com.example.marek.komunikator.userSettings.activities.UserActivity;

public class UserChangePasswordTask extends ResponseTask {


    public UserChangePasswordTask(Response response, UserActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }
}
