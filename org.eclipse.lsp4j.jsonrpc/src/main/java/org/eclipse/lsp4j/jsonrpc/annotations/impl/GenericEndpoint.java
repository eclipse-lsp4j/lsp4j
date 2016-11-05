package org.eclipse.lsp4j.jsonrpc.annotations.impl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Endpoint;

public class GenericEndpoint implements Endpoint {
	
	private LinkedHashMap<String, Function<Object, CompletableFuture<Object>>> methodHandlers = new LinkedHashMap<>();
	private Object delegate;

	public GenericEndpoint(Object delegate) {
		this.delegate = delegate;
		recursiveFindRpcMethods(delegate);
	}
	
	protected void recursiveFindRpcMethods(Object current) {
		AnnotationUtil.findRpcMethods(current.getClass(), new HashSet<Class<?>>(), (methodInfo) -> {
			try {
				@SuppressWarnings("unchecked")
				Function<Object, CompletableFuture<Object>> handler = (arg) -> {
					try {
						return (CompletableFuture<Object>) methodInfo.method.invoke(current, arg);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				};
				if (methodHandlers.put(methodInfo.name, handler) != null) {
					throw new IllegalStateException("Multiple methods for name " + methodInfo.name);
				}
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		});
		AnnotationUtil.findDelegateSegments(current.getClass(), new HashSet<Class<?>>(), (method) -> {
			try {
				recursiveFindRpcMethods(method.invoke(current));
			} catch (Exception e) {
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
		throw new UnsupportedOperationException("request : "+method);
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
		throw new UnsupportedOperationException("notification : "+method);
	}
	
}
