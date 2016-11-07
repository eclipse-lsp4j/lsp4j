/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;

public final class ServiceEndpoints {
	private ServiceEndpoints() {}
	
	/**
	 * Wraps a given {@link Endpoint} in the given service interface.
	 * @param endpoint
	 * @param interface_
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toServiceObject(Endpoint endpoint, Class<T> interface_) {
		Object newProxyInstance = Proxy.newProxyInstance(EndpointProxy.class.getClassLoader(), new Class[]{interface_}, new EndpointProxy(endpoint, interface_));
		return (T) newProxyInstance;
	}
	
	/**
	 * Wraps a given object with service annotations behind an {@link Endpoint} interface.
	 * 
	 * @param serviceObject
	 * @return
	 */
	public static Endpoint toEndpoint(Object serviceObject) {
		return new GenericEndpoint(serviceObject);
	}
	
	/**
	 * Finds all Json RPC methods on a given class
	 * 
	 * @param type
	 * @return
	 */
	public static Map<String, JsonRpcMethod> getSupportedMethods(Class<?> type) {
		Set<Class<?>> visitedTypes = new HashSet<>();
		return getSupportedMethods(type, visitedTypes);
	}
	/**
	 * Finds all Json RPC methods on a given type
	 * 
	 * @param type
	 * @return
	 */
	private static Map<String, JsonRpcMethod> getSupportedMethods(Class<?> type, Set<Class<?>> visitedTypes) {
		Map<String, JsonRpcMethod> result = new LinkedHashMap<String, JsonRpcMethod>();
		AnnotationUtil.findRpcMethods(type, visitedTypes, (methodInfo) -> {
			JsonRpcMethod meth;
			if (methodInfo.isNotification) {
				meth = JsonRpcMethod.notification(methodInfo.name, methodInfo.parameterType);
			} else {
				Type returnType = methodInfo.method.getGenericReturnType();
				if (returnType instanceof ParameterizedType) {
					ParameterizedType rType = (ParameterizedType) returnType;
					meth = JsonRpcMethod.request(methodInfo.name, methodInfo.parameterType, rType.getActualTypeArguments()[0]);
				} else {
					throw new IllegalStateException("Expecting return type of CompletableFuture but was : "+returnType);
				}
			}
			if (result.put(methodInfo.name, meth) != null) {
				throw new IllegalStateException("Duplicate RPC method "+methodInfo.name+".");
			};
		});
		
		AnnotationUtil.findDelegateSegments(type, new HashSet<>(), (method)-> {
			Map<String, JsonRpcMethod> supportedDelegateMethods = getSupportedMethods(method.getReturnType(), visitedTypes);
			for (JsonRpcMethod meth : supportedDelegateMethods.values()) {
				if (result.put(meth.getMethodName(), meth) != null) {
					throw new IllegalStateException("Duplicate RPC method "+meth.getMethodName()+".");
				};
			}
		});
		return result;
	}
}
