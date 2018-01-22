package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.Contact;
import com.example.marek.komunikator.model.ContactList;
import com.example.marek.komunikator.userSettings.activities.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marek on 21.01.18.
 */

public class UserFriendListTask extends ResponseTask {
    public UserFriendListTask(Response response, MyBaseActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            JSONArray json = new JSONArray(response.getBody());
            for (int i = 0; i < json.length(); i++){
                JSONObject o = json.getJSONObject(i);
                String nick = "";
                boolean status = false;
                if (o.has("nick")){
                    nick = o.getString("nick");
                }
                if (o.has("status")){
                    status = o.getBoolean("status");
                }
                Contact contact = new Contact(nick);
                contact.setActive(status);
                ContactList.addToList(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        activity.resetDataAfterResponse();
        ((LoginActivity)activity).sendLastActivityRequest();
    }
}
