package com.example.marek.komunikator.controllers.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ConfigManager {
	private static Map<String, ControllerInstance> mapApiPathToController;
	private static boolean ARE_CONTROLLERS_CREATED = false;
	
	public static void initializeConfigContext() throws IllegalAccessException, IOException, InvocationTargetException, InstantiationException  {
		createControllers();
		
		ConfigContext.init(mapApiPathToController);
	}
	
	private static void createControllers() throws IllegalAccessException, IOException, InvocationTargetException, InstantiationException {
		if (ARE_CONTROLLERS_CREATED) {
			throw new IllegalAccessException("controllers are already created!");
		}
		
		mapApiPathToController = new ControllerConfigurator().createControllers();
		
		ARE_CONTROLLERS_CREATED = true;
	}
}
