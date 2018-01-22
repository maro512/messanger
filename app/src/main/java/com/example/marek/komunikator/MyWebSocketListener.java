package com.example.marek.komunikator;

import android.util.Base64;

import com.example.marek.komunikator.controllers.config.Controller;
import com.example.marek.komunikator.controllers.config.ControllerHandler;
import com.example.marek.komunikator.controllers.config.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by marek on 18.01.18.
 */

public class MyWebSocketListener extends WebSocketListener {
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        byte[] out = Base64.decode(text, Base64.DEFAULT);

        doSth(out);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        byte[] b = bytes.toByteArray();
        byte[] out = Base64.decode(b, Base64.DEFAULT);

        doSth(out);

    }

    private void doSth(byte[] bytes) {
        String decoded = "";

        for (byte b : bytes){
            decoded += (char) b;
        }

        JSONObject json;
//
//        try {
//            json = new JSONObject(decoded);
//            String JWT = (String) json.get("JWT");
//            String data = (String) json.get("data");
//            String apiPath = (String) json.get("apiPath");
//            Response response = new Response(apiPath, data, JWT);
////            ControllerHandler.proceed(response);
//            Controller c = ControllerHandler.getController(response);
//            if (c != null) {
//                c.proceed();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
