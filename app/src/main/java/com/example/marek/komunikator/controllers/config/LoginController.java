package com.example.marek.komunikator.controllers.config;

/**
 * Created by marek on 20.01.18.
 */

public class LoginController extends Controller {

    public LoginController(Response response) {
        super(response);
    }

    @Override
    public void proceed() {
        if (JWT != null){
            JWT = response.getJWT();
        }
    }
}
