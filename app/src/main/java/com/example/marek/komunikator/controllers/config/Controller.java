package com.example.marek.komunikator.controllers.config;

/**
 * Created by marek on 20.01.18.
 */

public abstract class Controller {
    protected Response response;
    protected static String JWT;

    public Controller(Response response) {
        this.response = response;
    }

    public abstract void proceed();
}
