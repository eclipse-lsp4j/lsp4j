/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * An either type maps union types in protocol specifications.
 */
public class Either<L, R> {

	public static <L, R> Either<L, R> forLeft(@NonNull L left) {
		return new Either<L, R>(left, null);
	}

	public static <L, R> Either<L, R> forRight(@NonNull R right) {
		return new Either<L, R>(null, right);
	}

	private final L left;
	private final R right;

	protected Either(L left, R right) {
		super();
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	public boolean isLeft() {
		return left != null;
	}

	public boolean isRight() {
		return right != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Either<?, ?>) {
			Either<?, ?> other = (Either<?, ?>) obj;
			return this.left != null && other.left != null && this.left.equals(other.left)
					|| this.right != null && other.right != null && this.right.equals(other.right);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (this.left != null)
			return this.left.hashCode();
		if (this.right != null)
			return this.right.hashCode();
		return 0;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("Either [").append(System.lineSeparator());
		builder.append("  left = ").append(left).append(System.lineSeparator());
		builder.append("  right = ").append(right).append(System.lineSeparator());
		return builder.append("]").toString();
	}

	/**
	 * Return a left disjoint type if the given type is either.
	 * 
	 * @deprecated Use {@link org.eclipse.lsp4j.jsonrpc.json.adapters.TypeUtils#getElementTypes(Type, Class, Class)} instead
	 */
	@Deprecated
	public static Type getLeftDisjointType(Type type) {
		if (isEither(type)) {
			if (type instanceof ParameterizedType) {
				final ParameterizedType parameterizedType = (ParameterizedType) type;
				return parameterizedType.getActualTypeArguments()[0];
			}
			if (type instanceof Class) {
				final Class<?> cls = (Class<?>) type;
				return cls.getTypeParameters()[0];
			}
		}
		return null;
	}

	/**
	 * Return a right disjoint type if the given type is either.
	 * 
	 * @deprecated Use {@link org.eclipse.lsp4j.jsonrpc.json.adapters.TypeUtils#getElementTypes(Type, Class, Class)} instead
	 */
	@Deprecated
	public static Type getRightDisjointType(Type type) {
		if (isEither(type)) {
			if (type instanceof ParameterizedType) {
				final ParameterizedType parameterizedType = (ParameterizedType) type;
				return parameterizedType.getActualTypeArguments()[1];
			}
			if (type instanceof Class) {
				final Class<?> cls = (Class<?>) type;
				return cls.getTypeParameters()[1];
			}
		}
		return null;
	}
	
	/**
	 * Return all disjoint types.
	 */
	public static Collection<Type> getAllDisjoinTypes(Type type) {
		return collectDisjoinTypes(type, new ArrayList<>());
	}

	protected static Collection<Type> collectDisjoinTypes(Type type, Collection<Type> types) {
		if (isEither(type)) {
			if (type instanceof ParameterizedType) {
				return collectDisjoinTypes((ParameterizedType) type, types);
			}
			if (type instanceof Class) {
				return collectDisjoinTypes((Class<?>) type, types);
			}
		}
		types.add(type);
		return types;
	}

	protected static Collection<Type> collectDisjoinTypes(ParameterizedType type, Collection<Type> types) {
		for (Type typeArgument : type.getActualTypeArguments()) {
			collectDisjoinTypes(typeArgument, types);
		}
		return types;
	}

	protected static Collection<Type> collectDisjoinTypes(Class<?> type, Collection<Type> types) {
		for (Type typeParameter : type.getTypeParameters()) {
			collectDisjoinTypes(typeParameter, types);
		}
		return types;
	}

	/**
	 * Test whether the given type is Either.
	 */
	public static boolean isEither(Type type) {
		if (type instanceof ParameterizedType) {
			return isEither((ParameterizedType) type);
		}
		if (type instanceof Class) {
			return isEither((Class<?>) type);
		}
		return false;
	}

	/**
	 * Test whether the given type is Either.
	 */
	public static boolean isEither(ParameterizedType type) {
		return isEither(type.getRawType());
	}

	/**
	 * Test whether the given class is Either.
	 */
	public static boolean isEither(Class<?> cls) {
		return Either.class.isAssignableFrom(cls);
	}

}
