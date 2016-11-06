package org.eclipse.lsp4j.jsonrpc.json;

import java.lang.reflect.Type;

/**
 * A description of an jsonrpc method 
 */
public class JsonRpcMethod {
	private final String methodName;
	private final Type parameterType;
	private final Type returnType;
	private final boolean isNotification;
	
	private JsonRpcMethod(String methodName, Type parameterType, Type returnType, boolean isNotification) {
		this.methodName = methodName;
		this.parameterType = parameterType;
		this.returnType = returnType;
		this.isNotification = isNotification;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public Type getParameterType() {
		return parameterType;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public boolean isNotification() {
		return isNotification;
	}

	public static JsonRpcMethod notification(String name, Type parameterType) {
		return new JsonRpcMethod(name, parameterType, Void.class, true);
	}
	
	public static JsonRpcMethod request(String name, Type parameterType, Type returnType) {
		return new JsonRpcMethod(name, parameterType, returnType, false);
	}
}
