package com.example.marek.komunikator.controllers.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerInstance {
	private final Object object;
	private final Method methodToInvoke;
	private final Class clazz;
	
	public ControllerInstance(Object object, Method methodToInvoke, Class clazz) {
		this.object = object;
		this.methodToInvoke = methodToInvoke;
		this.clazz = clazz;
	}
	
	public Object getObject() {
		return object;
	}
	
	public Method getMethodToInvoke() {
		return methodToInvoke;
	}
	
	public Class getClazz() {
		return clazz;
	}
	
	public void invokeControllerMethod(Response body) throws InvocationTargetException, IllegalAccessException {
		methodToInvoke.invoke(object, body);
	}
}