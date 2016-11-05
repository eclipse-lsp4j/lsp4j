package org.eclipse.lsp4j.jsonrpc.annotations;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.RpcMethod;
import org.eclipse.lsp4j.jsonrpc.annotations.impl.AnnotationUtil;
import org.eclipse.lsp4j.jsonrpc.annotations.impl.EndpointProxy;
import org.eclipse.lsp4j.jsonrpc.annotations.impl.GenericEndpoint;

public final class Endpoints {
	private Endpoints() {}
	
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
	public static Map<String, RpcMethod> getSupportedMethods(Class<?> type) {
		Map<String, RpcMethod> result = new LinkedHashMap<String, RpcMethod>();
		AnnotationUtil.findRpcMethods(type, new HashSet<>(), (methodInfo) -> {
			RpcMethod meth;
			if (methodInfo.isNotification) {
				meth = RpcMethod.notification(methodInfo.name, methodInfo.parameterType);
			} else {
				Type returnType = methodInfo.method.getGenericReturnType();
				if (returnType instanceof ParameterizedType) {
					ParameterizedType rType = (ParameterizedType) returnType;
					meth = RpcMethod.request(methodInfo.name, methodInfo.parameterType, rType.getActualTypeArguments()[0]);
				} else {
					throw new IllegalStateException("Expecting return type of CompletableFuture but was : "+returnType);
				}
			}
			if (result.put(methodInfo.name, meth) != null) {
				throw new IllegalStateException("Duplicate RPC method "+methodInfo.name+".");
			};
		});
		
		AnnotationUtil.findDelegateSegments(type, new HashSet<>(), (method)-> {
			Map<String, RpcMethod> supportedDelegateMethods = getSupportedMethods(method.getReturnType());
			for (RpcMethod meth : supportedDelegateMethods.values()) {
				if (result.put(meth.getMethodName(), meth) != null) {
					throw new IllegalStateException("Duplicate RPC method "+meth.getMethodName()+".");
				};
			}
		});
		return result;
	}
}
