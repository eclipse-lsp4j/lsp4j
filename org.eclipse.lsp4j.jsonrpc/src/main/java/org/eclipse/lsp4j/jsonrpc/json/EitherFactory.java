package org.eclipse.lsp4j.jsonrpc.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.internal.$Gson$Types;

public abstract class EitherFactory<L,R> {

	public Either<L,R> create() {
		Type left = getSuperclassTypeParameter(getClass(), 0);
		Type right = getSuperclassTypeParameter(getClass(), 1);
		return new Either<L,R>(WrappedJsonConverter.getConverter(left),
							   WrappedJsonConverter.getConverter(right));
	}
	
	/**
	 * Returns the type from super class's type parameter in
	 * {@link $Gson$Types#canonicalize canonical form}.
	 */
	static Type getSuperclassTypeParameter(Class<?> subclass, int index) {
		Type superclass = subclass.getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		ParameterizedType parameterized = (ParameterizedType) superclass;
		return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[index]);
	}
}
