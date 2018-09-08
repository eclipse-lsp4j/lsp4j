/******************************************************************************
 * Copyright (c) 2017-2018 TypeFox and others.
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

import java.util.Objects;

import org.eclipse.lsp4j.jsonrpc.json.adapters.ThrowableTypeAdapter;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ThrowableTest {

	protected Gson createGson() {
		return new GsonBuilder().registerTypeAdapterFactory(new ThrowableTypeAdapter.Factory()).create();
	}

	protected void assertSerialize(Object object, String expected) {
		Gson gson = createGson();
		String actual = gson.toJson(object);
		Assert.assertEquals(expected, actual);
	}

	protected void assertParse(MyObjectA expected, String string) {
		Gson gson = createGson();
		MyObjectA actual = (MyObjectA)gson.fromJson(string, expected.getClass());
		Assert.assertEquals(expected.otherProperty, actual.otherProperty);
		assertEquals(expected.myThrowable, actual.myThrowable);
	}

	protected void assertEquals(Throwable expected, Throwable actual) {
		if (Objects.equals(expected, actual))
			return;
		Assert.assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.getCause(), actual.getCause());
	}

	@Test
	public void testSerializeThrowable() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = new Throwable("message");
		assertSerialize(object, "{\"myThrowable\":{\"message\":\"message\"}}");
	}

	@Test
	public void testSerializeThrowableWithCause() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = new Throwable("message", new Throwable("cause"));
		assertSerialize(object, "{\"myThrowable\":{\"message\":\"message\",\"cause\":{\"message\":\"cause\"}}}");
	}

	@Test
	public void testSerializeThrowableWithRecursiveCause() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = new Throwable("message");
		object.myThrowable = new Throwable(object.myThrowable.getMessage(), object.myThrowable);
		assertSerialize(object, "{\"myThrowable\":{\"message\":\"message\"}}");
	}

	@Test
	public void testSerializeNull() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = null;
		object.otherProperty = "ok";
		assertSerialize(object, "{\"otherProperty\":\"ok\"}");
		object.toString();
	}

	@Test
	public void testParseThrowable() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = new Throwable("message");
		assertParse(object, "{\"myThrowable\":{\"message\":\"message\"}}");
	}

	@Test
	public void testParseThrowableWithCause() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = new Throwable("message", new Throwable("cause"));
		assertParse(object, "{\"myThrowable\":{\"message\":\"message\",\"cause\":{\"message\":\"cause\"}}}");
	}

	@Test
	public void testParseNull() {
		MyObjectA object = new MyObjectA();
		object.myThrowable = null;
		object.otherProperty = "ok";
		assertParse(object, "{\"myThrowable\":null, \"otherProperty\": \"ok\"}");
		object.toString();
	}

	protected static class MyObjectA {
		public Throwable myThrowable;
		public String otherProperty;
	}
}
