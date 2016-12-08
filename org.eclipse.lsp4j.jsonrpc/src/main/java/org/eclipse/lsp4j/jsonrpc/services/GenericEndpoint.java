/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;

/**
 * An endpoint that reflectively delegates to {@link JsonNotification} and
 * {@link JsonRequest} methods of a given delegate object.
 */
public class GenericEndpoint implements Endpoint {
	
	private static final Logger LOG = Logger.getLogger(GenericEndpoint.class.getName());

	private final LinkedHashMap<String, Function<Object, CompletableFuture<Object>>> methodHandlers = new LinkedHashMap<>();
	private final Object delegate;

	public GenericEndpoint(Object delegate) {
		this.delegate = delegate;
		recursiveFindRpcMethods(delegate, new HashSet<>(), new HashSet<>());
	}

	protected void recursiveFindRpcMethods(Object current, Set<Class<?>> visited, Set<Class<?>> visitedForDelegate) {
		AnnotationUtil.findRpcMethods(current.getClass(), visited, (methodInfo) -> {
			@SuppressWarnings("unchecked")
			Function<Object, CompletableFuture<Object>> handler = (arg) -> {
				try {
					Object[] argument = arg == null ? new Object[0] : new Object[] { arg };
					return (CompletableFuture<Object>) methodInfo.method.invoke(current, argument);
				} catch (InvocationTargetException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			};
			if (methodHandlers.put(methodInfo.name, handler) != null) {
				throw new IllegalStateException("Multiple methods for name " + methodInfo.name);
			}
		});
		AnnotationUtil.findDelegateSegments(current.getClass(), visitedForDelegate, (method) -> {
			try {
				Object delegate = method.invoke(current);
				if (delegate != null)
					recursiveFindRpcMethods(delegate, visited, visitedForDelegate);
			} catch (InvocationTargetException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public CompletableFuture<?> request(String method, Object parameter) {
		Function<Object, CompletableFuture<Object>> handler = methodHandlers.get(method);
		if (handler != null) {
			return handler.apply(parameter);
		}
		if (delegate instanceof Endpoint) {
			return ((Endpoint) delegate).request(method, parameter);
		}
		String message = "Unsupported request method: " + method;
		LOG.log(Level.WARNING, message);
		CompletableFuture<?> exceptionalResult = new CompletableFuture<Object>();
		ResponseError error = new ResponseError(ResponseErrorCode.InvalidRequest, message, null);
		exceptionalResult.completeExceptionally(new ResponseErrorException(error));
		return exceptionalResult;
	}

	@Override
	public void notify(String method, Object parameter) {
		Function<Object, CompletableFuture<Object>> handler = methodHandlers.get(method);
		if (handler != null) {
			handler.apply(parameter);
			return;
		}
		if (delegate instanceof Endpoint) {
			((Endpoint) delegate).notify(method, parameter);
			return;
		}
		LOG.log(Level.WARNING, "Unsupported notification method: " + method);
	}

}
