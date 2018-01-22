package com.example.marek.komunikator.controllers.config;


import com.example.marek.komunikator.controllers.UserController;

import java.lang.reflect.InvocationTargetException;

public class ControllerHandler {
//	public static void proceed(Response response) throws InvocationTargetException, IllegalAccessException {
//		ControllerInstance controllerInstance = ConfigContext.getControllerInstanceForApiPath(response.getApiPath());
//		System.out.println(response.getApiPath());
//
//		controllerInstance.invokeControllerMethod(response);
//	}
	public static Controller getController(Response response){
		switch (response.getApiPath()){
			case "/login" : return new LoginController(response);
		}

		return null;
	}
}
