package com.example.marek.komunikator.controllers.config;

import com.example.marek.komunikator.controllers.annotation.ApiPath;
import com.example.marek.komunikator.controllers.annotation.Controller;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerConfigurator {
	
	private Map<String, ControllerInstance> MAP_API_PATH_TO_CONTROLLER = new HashMap<>();
	
	public Map<String, ControllerInstance> createControllers() throws IllegalAccessException, IOException, InvocationTargetException, InstantiationException {
		ClassPath classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
		System.out.println(classpath);
		System.out.println(classpath.getTopLevelClasses("."));
		System.out.println(ClassLoader.getSystemClassLoader());
		
		for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive("com.example.marek.komunikator.controllers")) {
			System.out.println("zwyciestwo !!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
			Class clazz = classInfo.load();
			if (clazz.isAnnotationPresent(Controller.class)) {
				parseControllerClass(clazz);
			}
		}
		
		return MAP_API_PATH_TO_CONTROLLER;
	}
	
	private void parseControllerClass(Class<?> controllerClazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Object instance = controllerClazz.getConstructors()[0].newInstance();
		
		for (Method method : controllerClazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(ApiPath.class)) {
				parseControllerMethod(controllerClazz, instance, method);
			}
		}
	}
	
	private void parseControllerMethod(Class<?> controllerClazz, Object instance, Method method) {
		ControllerInstance controllerInstanceReplaced;

		String apiPath = method.getAnnotation(ApiPath.class).path();
		
		ControllerInstance controllerInstance = new ControllerInstance(instance, method, controllerClazz);
		
		controllerInstanceReplaced = MAP_API_PATH_TO_CONTROLLER.put(apiPath, controllerInstance);
		if (controllerInstanceReplaced != null) {
			throw new IllegalArgumentException("In class: " + controllerClazz.getName() + " method: " + method.getName() +
					" has the same api mapping as class: " + controllerInstanceReplaced.getClazz().getName() +
					" in method: " + controllerInstanceReplaced.getMethodToInvoke().getName());
		}
	}
}
