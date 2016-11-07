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

	private Endpoint delegate;
	private LinkedHashMap<String, MethodInfo> methodInfos;
	private LinkedHashMap<String, DelegateInfo> delegatedSegments;

	public EndpointProxy(Endpoint delegate, Class<?> interf) {
		if (delegate == null)
			throw new NullPointerException("deleagte");
		if (interf == null)
			throw new NullPointerException("interf");
		this.delegate = delegate;
		methodInfos = new LinkedHashMap<>();
		AnnotationUtil.findRpcMethods(interf, new HashSet<Class<?>>(), (methodInfo) -> {
			if (methodInfos.put(methodInfo.method.getName(), methodInfo) != null) {
				throw new IllegalStateException("method overload not allowed : " + methodInfo.method);
			}
		});
		delegatedSegments = new LinkedHashMap<>();
		AnnotationUtil.findDelegateSegments(interf, new HashSet<Class<?>>(), (method) -> {
			Object delegateProxy = ServiceEndpoints.toServiceObject(delegate, method.getReturnType());
			DelegateInfo info = new DelegateInfo();
			info.delegate = delegateProxy;
			info.method = method;
			if (delegatedSegments.put(method.getName(), info) != null) {
				throw new IllegalStateException("method overload not allowed : " + method);
			}
			;
		});
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		args = args == null ? new Object[0] : args;
		if (args.length <= 1) {
			MethodInfo methodInfo = this.methodInfos.get(method.getName());
			if (methodInfo != null) {
				if (methodInfo.isNotification) {
					delegate.notify(methodInfo.name, args.length == 0 ? null : args[0]);
					return null;
				} else {
					return delegate.request(methodInfo.name, args.length == 0 ? null : args[0]);
				}
			}
			DelegateInfo delegateInfo = this.delegatedSegments.get(method.getName());
			if (delegateInfo != null) {
				return delegateInfo.delegate;
			}
		}

		return method.invoke(delegate, args);
	}

}
