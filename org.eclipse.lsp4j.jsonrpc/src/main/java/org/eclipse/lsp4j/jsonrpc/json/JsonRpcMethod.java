/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.lang.reflect.Type;

import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

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
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).addAllFields().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isNotification ? 1231 : 1237);
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + ((parameterType == null) ? 0 : parameterType.hashCode());
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonRpcMethod other = (JsonRpcMethod) obj;
		if (isNotification != other.isNotification)
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (parameterType == null) {
			if (other.parameterType != null)
				return false;
		} else if (!parameterType.equals(other.parameterType))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}
	
}
