package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.controllers.config.Response;

/**
 * Created by marek on 22.01.18.
 */

public class AddFriendTask extends ResponseTask {
    public AddFriendTask(Response response, MyBaseActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return (response.getBody().contains("status"));
    }
}
