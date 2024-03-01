/******************************************************************************
 * Copyright (c) 2016, 2024 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;

/**
 * An endpoint that reflectively delegates to {@link JsonNotification} and
 * {@link JsonRequest} methods of one or more given delegate objects.
 */
public class GenericEndpoint implements Endpoint {
	
	private static final Logger LOG = Logger.getLogger(GenericEndpoint.class.getName());
	private static final Object[] NO_ARGUMENTS = {};

	private final LinkedHashMap<String, Function<Object, CompletableFuture<Object>>> methodHandlers = new LinkedHashMap<>();
	private final List<Object> delegates;

	public GenericEndpoint(Object delegate) {
		this.delegates = Collections.singletonList(delegate);
		recursiveFindRpcMethods(delegate, new HashSet<>(), new HashSet<>());
	}
	
	public GenericEndpoint(Collection<Object> delegates) {
		this.delegates = new ArrayList<>(delegates);
		for (Object delegate : this.delegates) {
			recursiveFindRpcMethods(delegate, new HashSet<>(), new HashSet<>());
		}
	}

	protected void recursiveFindRpcMethods(Object current, Set<Class<?>> visited, Set<Class<?>> visitedForDelegate) {
		AnnotationUtil.findRpcMethods(current.getClass(), visited, methodInfo -> {
			@SuppressWarnings("unchecked")
			Function<Object, CompletableFuture<Object>> handler = arg -> {
				Method method = methodInfo.method;
				Object[] arguments = this.getArguments(method, arg);
				try {
					return (CompletableFuture<Object>) method.invoke(current, arguments);
				} catch (InvocationTargetException e) {
					Throwable cause = e.getCause();
					if (cause instanceof RuntimeException) {
						throw (RuntimeException) cause;
					} else if (cause instanceof Error) {
						throw (Error) cause;
					}
					throw new IllegalStateException("An unexpected exception occurred while executing jsonrpc method " + method, cause);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException("Inaccessible jsonrpc method: " + method, e);
				}
			};
			if (methodHandlers.put(methodInfo.name, handler) != null) {
				throw new IllegalStateException("Multiple methods for name " + methodInfo.name);
			}
		});
		AnnotationUtil.findDelegateSegments(current.getClass(), visitedForDelegate, method -> {
			try {
				Object delegate = method.invoke(current);
				if (delegate != null) {
					recursiveFindRpcMethods(delegate, visited, visitedForDelegate);
				} else {
					LOG.fine("A delegate object is null, jsonrpc methods of '" + method + "' are ignored");
				}
			} catch (InvocationTargetException | IllegalAccessException e) {
				throw new IllegalStateException("An exception occurred while executing JsonDelegate method " + method, e);
			}
		});
	}
	
	protected Object[] getArguments(Method method, Object arg) {
		int parameterCount = method.getParameterCount();
		if (parameterCount == 0) {
			if (arg != null) {
				LOG.warning("Unexpected params '" + arg + "' for '" + method + "' is ignored");
			}
			return NO_ARGUMENTS;
		}
		if (arg instanceof List<?>) {
			List<?> arguments = (List<?>) arg;
			int argumentCount = arguments.size(); 
			if (argumentCount == parameterCount) {
				return arguments.toArray();
			}
			if (argumentCount > parameterCount) {
				Stream<?> unexpectedArguments = arguments.stream().skip(parameterCount);
				String unexpectedParams = unexpectedArguments.map(a -> "'" + a + "'").reduce((a, a2) -> a + ", " + a2).get();
				LOG.warning("Unexpected params " + unexpectedParams + " for '" + method + "' is ignored");
				return arguments.subList(0, parameterCount).toArray();
			}
			return arguments.toArray(new Object[parameterCount]);
		}
		Object[] arguments = new Object[parameterCount];
		arguments[0] = arg;
		return arguments;
	}

	@Override
	public CompletableFuture<?> request(String method, Object parameter) {
		// Check the registered method handlers
		Function<Object, CompletableFuture<Object>> handler = methodHandlers.get(method);
		if (handler != null) {
			return handler.apply(parameter);
		}
		
		// Ask the delegate objects whether they can handle the request generically
		List<CompletableFuture<?>> futures = new ArrayList<>(delegates.size());
		for (Object delegate : delegates) {
			if (delegate instanceof Endpoint) {
				futures.add(((Endpoint) delegate).request(method, parameter));
			}
		}
		if (!futures.isEmpty()) {
			return CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()]));
		}
		
		// Create a log message about the unsupported method
		String message = "Unsupported request method: " + method;
		if (isOptionalMethod(method)) {
			LOG.log(Level.INFO, message);
			return CompletableFuture.completedFuture(null);
		}
		LOG.log(Level.WARNING, message);
		CompletableFuture<?> exceptionalResult = new CompletableFuture<Object>();
		ResponseError error = new ResponseError(ResponseErrorCode.MethodNotFound, message, null);
		exceptionalResult.completeExceptionally(new ResponseErrorException(error));
		return exceptionalResult;
	}

	@Override
	public void notify(String method, Object parameter) {
		// Check the registered method handlers
		Function<Object, CompletableFuture<Object>> handler = methodHandlers.get(method);
		if (handler != null) {
			handler.apply(parameter);
			return;
		}
		
		// Ask the delegate objects whether they can handle the notification generically
		int notifiedDelegates = 0;
		for (Object delegate : delegates) {
			if (delegate instanceof Endpoint) {
				((Endpoint) delegate).notify(method, parameter);
				notifiedDelegates++;
			}
		}
		
		if (notifiedDelegates == 0) {
			// Create a log message about the unsupported method
			String message = "Unsupported notification method: " + method;
			if (isOptionalMethod(method)) {
				LOG.log(Level.INFO, message);
			} else {
				LOG.log(Level.WARNING, message);
			}
		}
	}
	
	protected boolean isOptionalMethod(String method) {
		return method != null && method.startsWith("$/");
	}
}
