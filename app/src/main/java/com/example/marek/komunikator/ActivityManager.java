package com.example.marek.komunikator;

import android.app.Activity;
import android.app.Application;

import com.example.marek.komunikator.contacts.ContactsActivity;
import com.example.marek.komunikator.controllers.config.Response;
import com.example.marek.komunikator.messages.MessageActivity;
import com.example.marek.komunikator.model.ContactList;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by marek on 20.01.18.
 */

public class ActivityManager extends Application {
    private Activity crrActivity;
    public static WebSocket webSocket;
    public static String JWT;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.1.21:7777/chat").build();
        WebSocketListener listener = new MyWebSocketListener();

        webSocket = client.newWebSocket(request, listener);
    }

    public Activity getCrrActivity() {
        return crrActivity;
    }

    public void setCrrActivity(Activity crrActivity) {
        this.crrActivity = crrActivity;
    }

    private class MyWebSocketListener extends WebSocketListener {
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            doSth(text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            byte[] bytes1 = bytes.toByteArray();
            String text = "";

            for (byte b : bytes1){
                text += (char) b;
            }

            doSth(text);

        }

        private void doSth(String bytes) {
            Response response = new Response(bytes);
            //TODO: for all activities
            switch (response.getApiPath()){
                case "/userStatus":
                    userStatusResponse(response);
                    break;
                case "/messageNotification":
                    if(crrActivity instanceof MessageActivity){
                        ((MyBaseActivity) crrActivity).onResponse(response);
                    } else {
                        messageNotificationResponse(response);
                    }
                    break;
                case "/friendAccepted":
                    break;
                default:
                    ((MyBaseActivity) crrActivity).onResponse(response);
                    break;
            }


        }

        private void output(final String text){
            crrActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    private void messageNotificationResponse(Response response) {
        try {
            JSONObject json = new JSONObject(response.getBody());
            final String name = json.getString("userName");
            crrActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ContactList.changeContactNewMessages(name, true);
                    if (crrActivity instanceof ContactsActivity){
                        ((ContactsActivity) crrActivity).updateContacts();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void userStatusResponse(Response response) {
        try {
            JSONObject json = new JSONObject(response.getBody());
            final String name = json.getString("nick");
            final boolean status = json.getBoolean("status");
            crrActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ContactList.changeContactStatus(name, status);
                    if (crrActivity instanceof ContactsActivity){
                        ((ContactsActivity) crrActivity).updateContacts();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
