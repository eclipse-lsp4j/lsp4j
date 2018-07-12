/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.json;

import java.util.Objects;

import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class EitherTest {

	protected Gson createGson() {
		return new GsonBuilder().registerTypeAdapterFactory(new EitherTypeAdapter.Factory()).create();
	}

	protected void assertSerialize(Object object, String expected) {
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
		assertSerialize(object, "{\"myProperty\":7}");
	}

	@Test
	public void testParseEither() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Either.forRight(7);
		assertParse(object, "{\"myProperty\":7}");
	}

	@Test
	public void assertParseNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertParse(object, "{\"myProperty\":null, \"otherProperty\": \"ok\"}");
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
		assertSerialize(object, "{\"myProperty\":7}");
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

}
