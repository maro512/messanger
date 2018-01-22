package com.example.marek.komunikator.userSettings.tasks;

import android.webkit.ConsoleMessage;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.ContactList;
import com.example.marek.komunikator.model.Message;
import com.example.marek.komunikator.model.MessageList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by marek on 22.01.18.
 */

public class NewMessageTask extends ResponseTask {
    public NewMessageTask(Response response, MyBaseActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            JSONObject body = new JSONObject(response.getBody());
            if (body.has("userName")){
                String userName = body.getString("userName");
                if (userName.equals(MessageList.friendName)){
                    String content = "";
                    String date = null;

                    if (body.has("message")){
                        content = body.getString("message");
                    }
                    if (body.has("date")){
                        date = body.getString("date");
                    }

                    Message message = new Message(content, null);
                    message.setSendingDateFromTimestamp(date);
                    message.setMy(false);
                    MessageList.addToList(message);

                    return true;
                } else {
                    ContactList.changeContactNewMessages(userName, true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
