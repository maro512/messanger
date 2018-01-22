package com.example.marek.komunikator.userSettings.tasks;

import android.os.AsyncTask;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.User;
import com.example.marek.komunikator.userSettings.activities.UserActivity;

public abstract class ResponseTask extends AsyncTask<Void, Void, Boolean> {
    protected final Response response;
    protected MyBaseActivity activity;

    ResponseTask(Response response, MyBaseActivity activity) {
        this.response = response;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        activity.resetDataAfterResponse();
        activity.executeAfterResponse(success);
    }

    @Override
    protected void onCancelled() {
        activity.resetDataAfterResponse();
    }
}
