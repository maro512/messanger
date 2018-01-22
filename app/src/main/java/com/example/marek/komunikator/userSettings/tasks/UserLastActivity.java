package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.userSettings.activities.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marek on 21.01.18.
 */

public class UserLastActivity extends ResponseTask {
    public UserLastActivity(Response response, MyBaseActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            JSONObject json = new JSONObject(response.getBody());
            if (json.has("friendRequests")){
                JSONArray friendRequests = json.getJSONArray("friendRequests");

                for (int i = 0; i < friendRequests.length(); i++){
                    System.out.println(friendRequests.getJSONObject(i));
                }
                //TODO
            }
            if (json.has("messageUsersName")){
                JSONArray messageUsersName = json.getJSONArray("messageUsersName");

                for (int i = 0; i < messageUsersName.length(); i++) {
                    //TODO
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        activity.resetDataAfterResponse();
        ((LoginActivity)activity).showUserList();
    }
}
