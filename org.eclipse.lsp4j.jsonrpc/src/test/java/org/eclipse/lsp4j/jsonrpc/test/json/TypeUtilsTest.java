/******************************************************************************
 * Copyright (c) 2017 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.json;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.lsp4j.jsonrpc.json.adapters.TypeUtils;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.reflect.TypeToken;

public class TypeUtilsTest {
	
	protected void assertElementTypes(TypeToken<?> typeToken, Class<?> targetType, Type... expectedElementTypes) {
		Type[] actualElementTypes = TypeUtils.getElementTypes(typeToken, targetType);
		Assert.assertArrayEquals(expectedElementTypes, actualElementTypes);
	}
	
	@Test
	public void testCollectionElement1() {
		TypeToken<?> token = new TypeToken<List<String>>() {};
		assertElementTypes(token, Collection.class, String.class);
	}
	
	@Test
	public void testCollectionElement2() {
		TypeToken<?> token = new TypeToken<List<? extends Number>>() {};
		assertElementTypes(token, Collection.class, Number.class);
	}
	
	private static interface MyCollection extends Collection<String> {
	}
	
	@Test
	public void testCollectionElement3() {
		TypeToken<?> token = new TypeToken<MyCollection>() {};
		assertElementTypes(token, Collection.class, String.class);
	}
	
	private static class MyLinkedList extends LinkedList<String> {
		private static final long serialVersionUID = 1L;
	}
	
	@Test
	public void testCollectionElement4() {
		TypeToken<?> token = new TypeToken<MyLinkedList>() {};
		assertElementTypes(token, Collection.class, String.class);
	}
	
	@Test
	public void testCollectionElement5() {
		TypeToken<?> token = new TypeToken<List<Either<List<String>, String>>>() {};
		assertElementTypes(token, Collection.class, new TypeToken<Either<List<String>, String>>() {}.getType());
	}
	
	@Test
	public void testEitherElements1() {
		TypeToken<?> token = new TypeToken<Either<String, Number>>() {};
		assertElementTypes(token, Either.class, String.class, Number.class);
	}
	
	@Test
	public void testEitherElements2() {
		TypeToken<?> token = new TypeToken<Either<String, ? extends Number>>() {};
		assertElementTypes(token, Either.class, String.class, Number.class);
	}
	
	private static class MyEitherA extends Either<String, Number> {
		protected MyEitherA(String left, Number right) {
			super(left, right);
		}
	}
	
	@Test
	public void testEitherElements3() {
		TypeToken<?> token = new TypeToken<MyEitherA>() {};
		assertElementTypes(token, Either.class, String.class, Number.class);
	}
	
	private static class MyEitherB<T> extends Either<String, T> {
		protected MyEitherB(String left, T right) {
			super(left, right);
		}
	}
	
	@Test
	public void testEitherElements4() {
		TypeToken<?> token = new TypeToken<MyEitherB<Number>>() {};
		assertElementTypes(token, Either.class, String.class, Number.class);
	}
	
	@Test
	public void testEitherElements5() {
		TypeToken<?> token = new TypeToken<Either3<String, Number, Boolean>>() {};
		assertElementTypes(token, Either.class, String.class, new TypeToken<Either<Number, Boolean>>() {}.getType());
	}
	
	@Test
	public void testEitherElements6() {
		TypeToken<?> token = new TypeToken<Either3<String, Number, Boolean>>() {};
		assertElementTypes(token, Either3.class, String.class, Number.class, Boolean.class);
	}
	
	private static class MyEitherC extends Either3<String, Number, Boolean> {
		protected MyEitherC(String left, Either<Number, Boolean> right) {
			super(left, right);
		}
	}
	
	@Test
	public void testEitherElements7() {
		TypeToken<?> token = new TypeToken<MyEitherC>() {};
		assertElementTypes(token, Either.class, String.class, new TypeToken<Either<Number, Boolean>>() {}.getType());
	}

}
