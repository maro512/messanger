package com.example.marek.komunikator.controllers.config;

import java.util.Map;

public class ConfigContext {
	private static Map<String, ControllerInstance> mapApiPathToController;
	
	static void init(Map<String, ControllerInstance> mapApiPathToController) {
		ConfigContext.mapApiPathToController = mapApiPathToController;
	}
	
	public static ControllerInstance getControllerInstanceForApiPath(String apiPath) {
		return mapApiPathToController.get(apiPath);
	}
}
