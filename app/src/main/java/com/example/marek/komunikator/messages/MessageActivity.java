package com.example.marek.komunikator.messages;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marek.komunikator.ActivityManager;
import com.example.marek.komunikator.MyBaseActivity;
import com.example.marek.komunikator.R;
import com.example.marek.komunikator.controllers.config.Request;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.model.Message;
import com.example.marek.komunikator.model.MessageList;
import com.example.marek.komunikator.userSettings.tasks.NewMessageTask;
import com.example.marek.komunikator.userSettings.tasks.ResponseTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class MessageActivity extends MyBaseActivity {
    private ArrayAdapter<Message> messageAdapter;
    protected ResponseTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        EditText et = (EditText) findViewById(R.id.editText);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                addToList(v.getText().toString());
                v.setText(null);
                return false;
            }
        });



        List<Message> list = MessageList.getMessageList();

        messageAdapter = new MessageAdapter(this, list);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(messageAdapter);

    }

    @Override
    public void onResponse(Response response) {
        if (response.getApiPath().equals("/messageNotification")){
            mAuthTask = new NewMessageTask(response, this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void resetDataAfterResponse() {
        mAuthTask = null;
    }

    @Override
    public void executeAfterResponse(boolean success) {
        if (success){
            messageAdapter.notifyDataSetChanged();
        }
    }

    private void addToList(String text){
        Request request = new Request("/sendMessage");

        try {
            String body = null;
            if (text != null) {
                body = new JSONObject().put("userNick", MessageList.friendName)
                        .put("message", text).toString();
            }
            request.setBody(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActivityManager.webSocket.send(request.getRequest());
        Message msg = new Message(text, new Date());
        messageAdapter.add(msg);
    }

}
