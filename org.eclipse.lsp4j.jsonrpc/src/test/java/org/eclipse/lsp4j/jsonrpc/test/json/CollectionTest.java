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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CollectionTest {

	protected Gson createGson() {
		return new GsonBuilder().registerTypeAdapterFactory(new CollectionTypeAdapter.Factory()).create();
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
	public void testSerializeList() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Arrays.asList("foo", "bar");
		assertSerialize(object, "{\"myProperty\":[\"foo\",\"bar\"]}");
	}

	@Test
	public void testSerializeNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertParse(object, "{\"otherProperty\":\"ok\"}");
	}

	@Test
	public void testParseList() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Arrays.asList("foo", "bar");
		assertParse(object, "{\"myProperty\":[\"foo\",\"bar\"]}");
	}

	@Test
	public void testParseNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertParse(object, "{\"myProperty\":null, \"otherProperty\": \"ok\"}");
	}

	protected static class MyObjectA {
		public List<String> myProperty;
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
}
