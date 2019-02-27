package org.eclipse.lsp4j.jsonrpc.test.json;

import java.util.Objects;

import org.eclipse.lsp4j.jsonrpc.json.adapters.TupleTypeAdapters;
import org.eclipse.lsp4j.jsonrpc.messages.Tuple;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TuplesTest {

	protected static class MyObjectA {
		public Tuple.Two<String, Integer> myProperty;
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

	protected Gson createGson() {
		return new GsonBuilder().registerTypeAdapterFactory(new TupleTypeAdapters.TwoTypeAdapterFactory()).create();
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
	public void testSerializeTwo() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Tuple.two("Foo", 7);
		assertSerialize("{\"myProperty\":[\"Foo\",7]}", object);
	}

	@Test
	public void testSerializeNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertSerialize("{\"otherProperty\":\"ok\"}", object);
	}

	@Test
	public void testParseTwo() {
		MyObjectA object = new MyObjectA();
		object.myProperty = Tuple.two("Foo", 7);
		assertParse(object, "{\"myProperty\":[\"Foo\",7]}");
	}

	@Test
	public void testParseNull() {
		MyObjectA object = new MyObjectA();
		object.myProperty = null;
		object.otherProperty = "ok";
		assertParse(object, "{\"myProperty\":null, \"otherProperty\": \"ok\"}");
	}

}
