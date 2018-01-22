package com.example.marek.komunikator.controllers.config;

import android.util.Base64;

import com.example.marek.komunikator.ActivityManager;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.JsonObject;

public class Request {
    private String apiPath;
    private String body;
    private String JWT;

    public Request(String apiPath) {
        this.apiPath = apiPath;
        JWT = ActivityManager.JWT;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String makeString(){
        try {
            JSONObject json = new JSONObject().put("apiPath", apiPath);

            if (body != null) {
                JSONObject b = new JSONObject(body);
                json.put("data", b);
            }
            if (JWT != null) {
                json.put("JWT", JWT);
            }
            return json.toString().replace("\\", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getRequest(){
        System.out.println(makeString());
        byte[] bytes = makeString().getBytes();

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
