package com.example.marek.komunikator.controllers;

import com.example.marek.komunikator.controllers.annotation.ApiPath;
import com.example.marek.komunikator.controllers.annotation.Controller;
import com.example.marek.komunikator.controllers.config.Response;

/**
 * Created by marek on 18.01.18.
 */

@Controller
public class UserController {
    @ApiPath(path = "/login")
    public void loginUser(Response response) {
        System.out.println(response.getBody());
    }
}
