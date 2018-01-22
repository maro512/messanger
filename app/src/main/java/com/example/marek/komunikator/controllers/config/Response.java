package com.example.marek.komunikator.controllers.config;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marek on 18.01.18.
 */

public class Response {
    private String apiPath;
    private String body;
    private String JWT;

    public Response(String response) {
        byte[] bytes = Base64.decode(response, Base64.DEFAULT);

        String decoded = "";

        for (byte b : bytes){
            decoded += (char) b;
        }

        try {
            JSONObject json = new JSONObject(decoded);

            if (json.has("apiPath")){
                apiPath = json.getString("apiPath");
            }
            if (json.has("data")) {
                body = json.getString("data");
            }
            if (json.has("JWT")){
                JWT = json.getString("JWT");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getApiPath() {
        return apiPath;
    }

    public String getBody() {
        return body;
    }

    public String getJWT() {
        return JWT;
    }
}