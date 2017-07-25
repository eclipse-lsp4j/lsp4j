/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.services.AnnotationUtil.DelegateInfo;
import org.eclipse.lsp4j.jsonrpc.services.AnnotationUtil.MethodInfo;

/**
 * A Proxy that wraps an {@link Endpoint} in a service interface, i.e. an
 * interface containing {@link JsonNotification} and {@link JsonRequest}
 * methods.
 */
public class EndpointProxy implements InvocationHandler {
	
	private final Method object_equals;
	private final Method object_hashCode;
	private final Method object_toString;

	private final Endpoint delegate;
	private final LinkedHashMap<String, MethodInfo> methodInfos;
	private final LinkedHashMap<String, DelegateInfo> delegatedSegments;

	public EndpointProxy(Endpoint delegate, Class<?> interf) {
		if (delegate == null)
			throw new NullPointerException("delegate");
		if (interf == null)
			throw new NullPointerException("interf");
		this.delegate = delegate;
		try {
			object_equals = Object.class.getDeclaredMethod("equals", Object.class);
			object_hashCode = Object.class.getDeclaredMethod("hashCode");
			object_toString = Object.class.getDeclaredMethod("toString");
		} catch (NoSuchMethodException | SecurityException exception) {
			throw new RuntimeException(exception);
		}
		methodInfos = new LinkedHashMap<>();
		AnnotationUtil.findRpcMethods(interf, new HashSet<Class<?>>(), (methodInfo) -> {
			if (methodInfos.put(methodInfo.method.getName(), methodInfo) != null) {
				throw new IllegalStateException("Method overload not allowed : " + methodInfo.method);
			}
		});
		delegatedSegments = new LinkedHashMap<>();
		AnnotationUtil.findDelegateSegments(interf, new HashSet<Class<?>>(), (method) -> {
			Object delegateProxy = ServiceEndpoints.toServiceObject(delegate, method.getReturnType());
			DelegateInfo info = new DelegateInfo();
			info.delegate = delegateProxy;
			info.method = method;
			if (delegatedSegments.put(method.getName(), info) != null) {
				throw new IllegalStateException("Method overload not allowed : " + method);
			}
		});
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		args = args == null ? new Object[0] : args;
		MethodInfo methodInfo = this.methodInfos.get(method.getName());
		if (methodInfo != null) {
			Object params = getParams(args, methodInfo);
			if (methodInfo.isNotification) {
				delegate.notify(methodInfo.name, params);
				return null;
			}
			return delegate.request(methodInfo.name, params);
		}
		DelegateInfo delegateInfo = this.delegatedSegments.get(method.getName());
		if (delegateInfo != null) {
			return delegateInfo.delegate;
		}
		if (object_equals.equals(method) && args.length == 1) {
			try {
				return this.equals(Proxy.getInvocationHandler(args[0]));
			} catch (IllegalArgumentException exception) {
				return this.equals(args[0]);
			}
		}
		if (object_hashCode.equals(method)) {
			return this.hashCode();
		}
		if (object_toString.equals(method)) {
			return this.toString();
		}
		return method.invoke(delegate, args);
	}

	protected Object getParams(Object[] args, MethodInfo methodInfo) {
		if (args.length == 0) {
			return null;
		}
		if (args.length == 1) {
			return args[0];
		}
		return Arrays.asList(args);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " for " + delegate.toString();
	}

}
