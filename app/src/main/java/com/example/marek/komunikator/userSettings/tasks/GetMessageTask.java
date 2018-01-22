package com.example.marek.komunikator.userSettings.tasks;

import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.Message;
import com.example.marek.komunikator.model.MessageList;
import com.example.marek.komunikator.userSettings.tasks.ResponseTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by marek on 21.01.18.
 */

public class GetMessageTask extends ResponseTask {
    public GetMessageTask(Response response, MyBaseActivity activity) {
        super(response, activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            JSONArray json = new JSONArray(response.getBody());
            for (int i = 0; i < json.length(); i++) {
                JSONObject o = json.getJSONObject(i);
                String content = "";
                boolean isMy = false;
                String date = null;

                if (o.has("message")){
                    content = o.getString("message");
                }
                if (o.has("userName")){
                    String userName = o.getString("userName");
                    isMy = !userName.equals(MessageList.friendName);
                }
                if (o.has("date")){
                    date = o.getString("date");
                }

                Message message = new Message(content, null);
                message.setSendingDateFromTimestamp(date);
                message.setMy(isMy);

                MessageList.addToList(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
