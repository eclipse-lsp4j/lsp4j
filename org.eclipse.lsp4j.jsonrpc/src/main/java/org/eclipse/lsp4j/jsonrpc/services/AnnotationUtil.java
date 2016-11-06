package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.base.Strings;

public class AnnotationUtil {
	
	public static void findDelegateSegments(Class<?> clazz, Set<Class<?>> visited, Consumer<Method> acceptor) {
		if (clazz == null || !visited.add(clazz))
			return;
		findDelegateSegments(clazz.getSuperclass(), visited, acceptor);
		for (Class<?> interf : clazz.getInterfaces()) {
			findDelegateSegments(interf, visited, acceptor);
		}
		for (Method method : clazz.getDeclaredMethods()) {
			if (isDelegateMethod(method)) {
				acceptor.accept(method);
			}
		}
	}

	public static boolean isDelegateMethod(Method method) {
		JsonDelegate jsonDelegate = method.getAnnotation(JsonDelegate.class);
		if (jsonDelegate != null) {
			if (!(method.getParameterCount() == 0 && method.getReturnType().isInterface())) {
				throw new IllegalStateException(
						"The method " + method.toString() + " is not a proper @JsonDelegate method.");
			}
			return true;
		}
		return false;
	}
	

	/**
	 * depth first search for annotated methods in hierarchy
	 * 
	 * @param clazz
	 * @param visited
	 * @param acceptor
	 */
	public static void findRpcMethods(Class<?> clazz, Set<Class<?>> visited, Consumer<MethodInfo> acceptor) {
		if (clazz == null || !visited.add(clazz))
			return;
		findRpcMethods(clazz.getSuperclass(), visited, acceptor);
		for (Class<?> interf : clazz.getInterfaces()) {
			findRpcMethods(interf, visited, acceptor);
		}
		JsonSegment jsonSegment = clazz.getAnnotation(JsonSegment.class);
		String prefix = jsonSegment == null ? "" : jsonSegment.value() + "/";
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getParameterCount() <= 1) {
				JsonRequest jsonRequest = method.getAnnotation(JsonRequest.class);
				MethodInfo methodInfo = new MethodInfo();
				methodInfo.method = method;
				if (method.getParameterCount() > 0) {
					methodInfo.parameterType = method.getParameters()[0].getParameterizedType();
				}
				if (jsonRequest != null) {
					String name = Strings.isNullOrEmpty(jsonRequest.value()) ? method.getName() : jsonRequest.value();
					methodInfo.name = prefix + name;
					methodInfo.isNotification = false;
					acceptor.accept(methodInfo);
				} else {
					JsonNotification jsonNotification = method.getAnnotation(JsonNotification.class);
					if (jsonNotification != null) {
						String name = Strings.isNullOrEmpty(jsonNotification.value()) ? method.getName()
								: jsonNotification.value();
						methodInfo.name = prefix + name;
						methodInfo.isNotification = true;
						acceptor.accept(methodInfo);
					}
				}
			}
		}
	}
	
	public static class MethodInfo {
		public String name;
		public Method method;
		public Type parameterType = Void.class;
		public boolean isNotification = false;
	}

	public static class DelegateInfo {
		public Method method;
		public Object delegate;
	}
}
