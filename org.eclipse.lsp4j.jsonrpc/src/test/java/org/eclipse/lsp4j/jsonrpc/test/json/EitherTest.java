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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class EitherTest {

	protected Gson createGson() {
		return new GsonBuilder().registerTypeAdapterFactory(new EitherTypeAdapter.Factory()).create();
	}

	protected void assertSerialize(String expected, Object object) {
		Gson gson = createGson();
		String actual = gson.toJson(object);
		Assert.assertEquals(expected, actual);
	}

	protected void assertParse(Object expected, String string) {
		Gson gson = createGson();
		Object actual = gson.fromJson(string, expected.getClass());
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testSerializeEither() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Either.forRight(7);
		assertSerialize("{\"myProperty\":7}", object);
	}

	@Test
	public void testSerializeNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertSerialize("{\"otherProperty\":\"ok\"}", object);
	}

	@Test
	public void testParseEither() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Either.forRight(7);
		assertParse(object, "{\"myProperty\":7}");
	}

	@Test
	public void testParseNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertParse(object, "{\"myProperty\":null, \"otherProperty\": \"ok\"}");
	}
	
	@Test
	public void testEqualsForNull() {
		Either<Object, Object> either1 = Either.forLeft(null);
		Either<Object, Object> either2 = Either.forLeft(null);

		assertTrue(either1.equals(either2));
	}

	@Test
	public void testLeftEqualsNull() {
		Either<Object, String> either1 = Either.forRight("Testing");
		Either<Object, String> either2 = Either.forRight("Testing");

		assertTrue(either1.equals(either2));
	}

	@Test
	public void testRightEqualsNull() {
		Either<Object, String> either1 = Either.forLeft("Testing");
		Either<Object, String> either2 = Either.forLeft("Testing");

		assertTrue(either1.equals(either2));
	}

	@Test
	public void testEqualsFalseWithNonNull() {
		Either<Object, String> either1 = Either.forLeft("Testing");
		Either<Object, String> either2 = Either.forRight("Testing");

		assertFalse(either1.equals(either2));
	}

	@Test
	public void testMap() {
		Either<char[], String> either = Either.forLeft(new char[] { 'f', 'o', 'o' });
		assertEquals("foo", either.map(
				String::new,
				String::toUpperCase));
		either = Either.forRight("bar");
		assertEquals("BAR", either.map(
				String::new,
				String::toUpperCase));
	}

	@Test
	public void testMapEither3() {
		Either3<String, Integer, Boolean> either3;
		Function<String, String> mapFirst = s -> s.toUpperCase();
		Function<Integer, String> mapSecond = x -> x.toString();
		Function<Boolean, String> mapThird = b -> b.toString();

		either3 = Either3.forFirst("abc");
		assertEquals("ABC", either3.map(mapFirst, mapSecond, mapThird));

		either3 = Either3.forSecond(123);
		assertEquals("123", either3.map(mapFirst, mapSecond, mapThird));

		either3 = Either3.forThird(Boolean.TRUE);
		assertEquals("true", either3.map(mapFirst, mapSecond, mapThird));
	}

	protected static class MyObjectA {
		public Either<String, Integer> myProperty;
		public String otherProperty;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MyObjectA) {
				MyObjectA other = (MyObjectA) obj;
				return Objects.equals(this.myProperty, other.myProperty) && Objects.equals(this.otherProperty, otherProperty);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.myProperty, this.otherProperty);
		}
	}

	@Test
	public void testSerializeEither3() {
		MyObjectB object = new MyObjectB();
		object.myProperty = Either3.forSecond(7);
		assertSerialize("{\"myProperty\":7}", object);
	}

	@Test
	public void testParseEither3() {
		MyObjectB object = new MyObjectB();
		object.myProperty = Either3.forSecond(7);
		assertParse(object, "{\"myProperty\":7}");
	}

	protected static class MyObjectB {
		public Either3<String, Integer, Boolean> myProperty;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MyObjectB) {
				MyObjectB other = (MyObjectB) obj;
				return this.myProperty == null && other.myProperty == null
						|| this.myProperty != null && this.myProperty.equals(other.myProperty);
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (myProperty != null)
				return myProperty.hashCode();
			return 0;
		}
	}

	@Test
	public void testSerializeJsonObject() {
		MyObjectC object = new MyObjectC();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("foo", "bar");
		object.myProperty = Either.forRight(jsonObject);
		assertSerialize("{\"myProperty\":{\"foo\":\"bar\"}}", object);
	}

	@Test
	public void testParseJsonObject() {
		MyObjectC object = new MyObjectC();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("foo", "bar");
		object.myProperty = Either.forRight(jsonObject);
		assertParse(object, "{\"myProperty\":{\"foo\":\"bar\"}}");
	}

	protected static class MyObjectC {
		public Either<String, Object> myProperty;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MyObjectC) {
				MyObjectC other = (MyObjectC) obj;
				return Objects.equals(this.myProperty, other.myProperty);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.myProperty);
		}
	}

	@Test
	public void testSerializeJsonObjectEither3() {
		MyObjectD object = new MyObjectD();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("foo", "bar");
		object.myProperty = Either3.forThird(jsonObject);
		assertSerialize("{\"myProperty\":{\"foo\":\"bar\"}}", object);
	}

	@Test
	public void testParseJsonObjectEither3() {
		MyObjectD object = new MyObjectD();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("foo", "bar");
		object.myProperty = Either3.forThird(jsonObject);
		assertParse(object, "{\"myProperty\":{\"foo\":\"bar\"}}");
	}

	protected static class MyObjectD {
		public Either3<String, Integer, Object> myProperty;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MyObjectD) {
				MyObjectD other = (MyObjectD) obj;
				return Objects.equals(this.myProperty, other.myProperty);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.myProperty);
		}
	}

}
